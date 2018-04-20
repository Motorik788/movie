package main;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by dryzu on 12.04.2018.
 */
public interface IMovieStorage {
	void addMovie(FilmInfo filmInfo) throws Exception;

	FilmInfo getMovie(String imdb);

	Stream<FilmInfo> search(String name, Date date);

	List<FilmInfo> search(String name, int fromYear, int toYear, float fromRating, float toRating, boolean isAdult);

	void addReview(User user, String imdb, String text, float rating, Date date);

	Comment getComment(String id);

	List<Comment> getUserComments(int userId);

	void changeReview(String id, String text, float rating);

	void deleteReview(String idComment);
}
