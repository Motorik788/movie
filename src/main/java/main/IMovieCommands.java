package main;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by dryzu on 12.04.2018.
 */
public interface IMovieCommands {
	Stream<FilmInfo> search(String name, Date date);

	List<FilmInfo> search(String name, int fromYear, int toYear, float fromRating, float toRating, boolean isAdult);

	FilmInfo detail(String imdb);

	void addReview(String imdb, String text, float rating);

	void changeReview(String idComment, String text, float rating);

	List<Comment> getUserComments();

	void deleteReview(String idComment);
}
