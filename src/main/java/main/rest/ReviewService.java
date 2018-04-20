package main.rest;

import main.Comment;
import main.IMovieCommands;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by dryzu on 19.04.2018.
 */
@Path("library/rest/review")
@Consumes("application/json")
@Produces("application/json; charset=UTF-8")
public class ReviewService {
	@Autowired
	private IMovieCommands library;

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") String id) {
		library.deleteReview(id);
	}

	@PUT
	@Path("/{id}")
	public void changeReview(@PathParam("id") String id, Comment comment) {
		if (comment != null)
			library.changeReview(id, comment.text, comment.rating);
	}

	@POST
	@Path("/view")
	public List<Comment> getCurrentUserAllReview() {
		return library.getUserComments();
	}
}
