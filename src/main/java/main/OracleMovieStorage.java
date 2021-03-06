package main;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by dryzu on 12.04.2018.
 */
public class OracleMovieStorage implements IMovieStorage {
	private OracleConnectionHelper helper;

	public OracleMovieStorage(String connectionString) {
		helper = new OracleConnectionHelper(connectionString, "system", "1234");
	}


	@Override
	public void addMovie(FilmInfo filmInfo) {
		List<Object> params = new ArrayList<>();
		params.add(filmInfo.IMDB);
		params.add(filmInfo.Type);
		params.add(filmInfo.Name);
		params.add(filmInfo.Genre);
		params.add(new SimpleDateFormat("MM/dd/yyyy").format(filmInfo.RealizeDate));
		params.add(filmInfo.Rating);
		params.add(filmInfo.Description);
		helper.insert("insert into Movie_INFO values (?,?,?,?,TO_DATE(?,'MM/dd/yyyy'),?,?)", params);
	}

	private FilmInfo parseResultSetWrapperToFilmInfo(ResultSetWrapper resultSetWrapper) {
		List<Object> params = new ArrayList<>();
		ResultSet set = resultSetWrapper.getResultSet();
		try {
			FilmInfo film = null;
			if (set.next()) {
				String IMDB = set.getString(1);
				String Type = set.getString(2);
				String name = set.getString(3);
				String genre = set.getString(4);
				Date date = set.getDate(5);
				float rating = set.getFloat(6);
				String descr = set.getString(7);
				String isAdult = set.getString(8);

				film = new FilmInfo();
				film.IMDB = IMDB;
				film.Type = Type;
				film.Name = name;
				film.Genre = genre;
				film.RealizeDate = date;
				film.Rating = rating;
				film.Description = descr;
				film.IsAdult = isAdult.equals("1");
				params.add(IMDB);


				//resultSetWrapper.close();
				resultSetWrapper = helper.select("select * from Comments where ID_IMDB = ?", params);
				set = resultSetWrapper.getResultSet();
				params.clear();
				while (set.next()) {
					Comment comment = resultSetToComment(set);
					if (comment != null)
						film.Comments.add(comment);
				}
				resultSetWrapper.close();
			}
			return film;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return null;
	}

	private List<FilmInfo> tryParseFilms(ResultSetWrapper wrapper) {
		List<FilmInfo> films = new ArrayList<>();

		try {
			FilmInfo filmInfo = null;
			do {
				filmInfo = parseResultSetWrapperToFilmInfo(wrapper);
				if (filmInfo != null)
					films.add(filmInfo);
			} while (filmInfo != null);

			wrapper.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return films;
	}

	private Comment resultSetToComment(ResultSet set) {
		Comment comment = null;
		List<Object> params = new ArrayList<>();
		try {
			params.add(set.getString(3));
			ResultSetWrapper userSetWrapper = helper.select("select * from USER_INFO where ID_USER = ?", params);
			User user = OracleUserStorage.parseSetToUser(userSetWrapper.getResultSet());
			userSetWrapper.close();
			String id = set.getString(1);
			Date dateComment = set.getDate(4);
			String text = set.getString(5);
			float ratingComment = set.getFloat(6);
			comment = new Comment(id, user, text, dateComment, ratingComment);
		} catch (Exception ex) {

		}
		return comment;
	}

	@Override
	public FilmInfo getMovie(String imdb) {
		List<Object> params = new ArrayList<>();
		params.add(imdb);
		ResultSetWrapper resultSetWrapper = helper.select("select * from Movie_INFO where IMDB = ?", params);
		FilmInfo film = parseResultSetWrapperToFilmInfo(resultSetWrapper);
		resultSetWrapper.close();
		return film;
	}

	@Override
	public Stream<FilmInfo> search(String name, Date date) {
		List<Object> params = new ArrayList<>();
		int year = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear() + 1;//небольное заклиннае шоб нормально год получить
		params.add(year);
		params.add("%" + name + "%");
		ResultSetWrapper wrapper = helper.select("select * from Movie_INFO where extract(YEAR from RELEASE) >= ? and MOVIE_NAME like ? order by RATING desc", params);
		List<FilmInfo> films = tryParseFilms(wrapper);
		return films.stream();
	}

	@Override
	public List<FilmInfo> search(String name, int fromYear, int toYear, float fromRating, float toRating, boolean isAdult) {
		List<Object> params = new ArrayList<>();
		params.add(fromYear);
		params.add(toYear);
		params.add(fromRating);
		params.add(toRating);
		params.add(isAdult ? 1 : 0);
		params.add("%" + name + "%");
		ResultSetWrapper wrapper = helper.select("select * from Movie_INFO " +
				"where extract(YEAR from RELEASE) between ? and ? and RATING between ? and ? and IS_ADULT = ? and MOVIE_NAME like ? order by RATING desc", params);

		return tryParseFilms(wrapper);
	}

	@Override
	public void addReview(User user, String imdb, String text, float rating, Date date) {
		List<Object> params = new ArrayList<>();

		ResultSetWrapper set = helper.select("select MAX(ID) from Comments", null);
		if (set != null) {
			try {
				params.add((!set.getResultSet().next() ? 0 : set.getResultSet().getInt(1)) + 1);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
			set.close();
		}
		params.add(imdb);
		params.add(user.getId());
		params.add(new SimpleDateFormat("MM/dd/yyyy").format(date));
		params.add(text);
		params.add(rating);
		helper.insert("insert into Comments values(?,?,?,TO_DATE(?,'MM/dd/yyyy'),?,?)", params);
	}

	@Override
	public Comment getComment(String id) {
		List<Object> params = new ArrayList<>();
		params.add(id);
		Comment comment = null;
		ResultSetWrapper wrapper = helper.select("select * from Comments where ID = ?", params);
		ResultSet set = wrapper.getResultSet();
		if (set != null) {
			try {
				set.next();
				comment = resultSetToComment(set);
				wrapper.close();
			} catch (Exception ex) {

			}
		}
		return comment;
	}

	@Override
	public List<Comment> getUserComments(int userId) {
		List<Object> params = new ArrayList<>();
		params.add(userId);

		List<Comment> comments = new ArrayList<>();

		ResultSetWrapper wrapper = helper.select("select * from Comments where AUTHOR_ID = ?", params);
		ResultSet set = wrapper.getResultSet();
		if (set != null) {
			try {
				while (set.next()) {
					Comment comment = resultSetToComment(set);
					if (comment != null) {
						comments.add(comment);
					}
				}
			} catch (Exception ex) {

			}
		}
		wrapper.close();
		return comments;
	}


	@Override
	public void changeReview(String id, String text, float rating) {
		List<Object> params = new ArrayList<>();
		params.add(text);
		params.add(rating);
		params.add(id);
		helper.insert("update Comments set TEXT = ?, RATING = ? where ID = ?", params);
	}


	@Override
	public void deleteReview(String idComment) {
		List<Object> param = new ArrayList<>();
		param.add(idComment);
		helper.insert("delete from Comments where ID = ?", param);
	}
}
