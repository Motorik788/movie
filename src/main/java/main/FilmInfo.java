package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dryzu on 05.04.2018.
 */
public class FilmInfo {
	public String IMDB;
	public String Type;
	public String Name;
	public String Genre;
	public Date RealizeDate;
	public float Rating;
	public String Description;
	public  boolean IsAdult;

	public List<Comment> Comments = new ArrayList<>();

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("IMDB: ");
		sb.append(IMDB + '\n');
		sb.append("Type: ");
		sb.append(Type + '\n');
		sb.append("Name: ");
		sb.append(Name + '\n');
		sb.append("Genre: ");
		sb.append(Genre + '\n');
		sb.append("RealizeDate: ");
		sb.append(RealizeDate.toString() + '\n');
		sb.append("Rating: ");
		sb.append(Rating + "\n");
		sb.append("Description: ");
		sb.append(Description + '\n');

		sb.append("Comments: \n");
		for (Comment comment : Comments) {
			sb.append(comment);
		}

		return sb.toString();
	}

	public String toShortInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("IMDB: ");
		sb.append(IMDB);
		sb.append(", Name: ");
		sb.append(Name);
		sb.append(", RealizeDate: ");
		sb.append(RealizeDate.toString());
		sb.append(", Rating: ");
		sb.append(Rating + "\n");
		return sb.toString();
	}
}
