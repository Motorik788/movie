package main.rest;

import main.Comment;
import main.FilmInfo;
import main.IMovieCommands;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.List;


/**
 * Created by dryzu on 19.04.2018.
 */
@Path("library/rest/movie")
@Consumes("application/json")
@Produces("application/json; charset=UTF-8")
public class MovieService {
	@Autowired
	private IMovieCommands library;

	@GET
	@Path("/{id}")
	public FilmInfo getMovie(@PathParam("id") String id) {
		return library.detail(id);
	}

	@POST
	@Path("/{id}/review")
	public void addReview(@PathParam("id") String id, Comment comment) {
		if (comment != null)
			library.addReview(id, comment.text, comment.rating);
	}

	@POST
	@Path("/view")
	public List<FilmInfo> view(MovieFilter filter) {
		if (filter != null)
			return library.search(filter.name, filter.fromYear, filter.toYear, filter.fromRating, filter.toRating, filter.isAdult);
		return null;
	}

}
