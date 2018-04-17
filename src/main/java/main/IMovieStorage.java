package main;

import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by dryzu on 12.04.2018.
 */
public interface IMovieStorage {
	void addMovie(FilmInfo filmInfo) throws  Exception;
	FilmInfo getMovie(String imdb);
	Stream<FilmInfo> search(String name, Date date);
	void addReview(User user,String imdb, String text, float rating,Date date);
	void deleteReview(String idComment);
}
