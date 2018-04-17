package main;

import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by dryzu on 12.04.2018.
 */
public interface IMovieCommands {
	Stream<FilmInfo> search(String name, Date date);

	String detail(String imdb);

	void addReview(String imdb, String text, float rating);

	void deleteReview(String idComment);
}
