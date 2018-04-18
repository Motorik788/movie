package main;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by dryzu on 05.04.2018.
 */
//@Component
public class FilmLibrary implements IMovieCommands {
	private UserManager userManager;
	private IMovieStorage movieStorage;

	private FilmLibrary(IUserStorage userStorage, IMovieStorage movieStorage) {
		this.userManager = new UserManager(userStorage);
		this.movieStorage = movieStorage;
		loadFromCSV(System.getProperty("user.dir"));
	}

	public static FilmLibrary createBasicStorageLibrary() {
		return new FilmLibrary(new BasicUserStorage(), new BasicMovieStorage());
	}

	public static FilmLibrary createOracleStorageLibrary(String connectionString) {
		return new FilmLibrary(new OracleUserStorage(connectionString), new OracleMovieStorage(connectionString));
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void loadFromCSV(String path) {
		String csvFile = path + "/Films.csv";
		String line = "";
		String cvsSplitBy = ";";

		if (Files.exists(Paths.get(csvFile))) {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "UTF8"))) {
				while ((line = br.readLine()) != null) {
					String[] film = line.split(cvsSplitBy);
					FilmInfo filmInfo = new FilmInfo();
					filmInfo.IMDB = film[0].trim();
					filmInfo.Name = film[1].trim();
					filmInfo.Genre = film[2].trim();
					filmInfo.Type = film[3].trim();
					filmInfo.RealizeDate = new SimpleDateFormat("MM/dd/yyyy").parse(film[4].trim());
					filmInfo.Rating = Float.parseFloat(film[5].trim());
					filmInfo.Description = film[6].trim();
					try {
						movieStorage.addMovie(filmInfo);
						System.out.println(filmInfo.toShortInfo());
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}


	public Stream<FilmInfo> search(String name, Date date) {
		return movieStorage.search(name, date);
	}

	public String detail(String imdb) {
		FilmInfo filmInfo = movieStorage.getMovie(imdb);
		if (filmInfo != null)
			return filmInfo.toString();
		return null;
	}

	public void addReview(String imdb, String text, float rating) {
		movieStorage.addReview(userManager.getCurrentUser(), imdb, text, rating, new Date());
	}

	public void deleteReview(String idComment) {
		User current = userManager.getCurrentUser();
		if (current != null && current instanceof AdminUser) {
			movieStorage.deleteReview(idComment);
		}
	}
}
