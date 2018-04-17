package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by dryzu on 12.04.2018.
 */
public class BasicMovieStorage implements IMovieStorage {
	private List<FilmInfo> Films = new ArrayList<>();


	@Override
	public void addMovie(FilmInfo filmInfo) throws Exception {
		Films.add(filmInfo);
	}

	@Override
	public FilmInfo getMovie(String imdb) {
		for (FilmInfo film : Films) {
			if (film.IMDB.equals(imdb)) {
				return film;
			}
		}
		return null;
	}

	@Override
	public Stream<FilmInfo> search(String name, Date date) {
		return Films.stream().filter((x) -> x.Name.toLowerCase().contains(name.toLowerCase()) && x.RealizeDate.after(date)).sorted(new Comparator<FilmInfo>() {
			@Override
			public int compare(FilmInfo o1, FilmInfo o2) {
				return -(int) (o1.Rating - o2.Rating);
			}
		});
	}

	@Override
	public void addReview(User user, String imdb, String text, float rating, Date date) {
		for (FilmInfo film : Films) {
			if (film.IMDB.equals(imdb)) {
				film.Comments.add(new Comment((user.hashCode() - text.hashCode()) + "", user, text, date, rating));
			}
		}
	}

	@Override
	public void deleteReview(String idComment) {
		int index = -1;
		FilmInfo searchFilm = null;
		for (FilmInfo film : Films) {
			for (Comment comment : film.Comments) {
				if (comment.Id.equals(idComment)) {
					searchFilm = film;
					index = film.Comments.indexOf(comment);
					break;
				}
			}
			if (searchFilm != null)
				break;
		}
		if (searchFilm != null)
			searchFilm.Comments.remove(index);
	}
}
