package com.gallup.gethip.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gallup.gethip.model.Comment;
import com.gallup.gethip.service.CommentService;

/**
 * 
 * @author Matthew Meacham
 * 
 *         The implementation of these methods is separated so that implementation is bundled and the URIs are separated, this will make it easier to edit
 *
 */
@Path("/")
@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public class CommentResource {

	private CommentService commentService = new CommentService();

	@GET
	public Response readAllComments(@PathParam("paperId") long paperId) {
		GenericEntity<List<Comment>> entity = new GenericEntity<List<Comment>>(commentService.readAllComments(paperId)) {
		};
		Response response = Response.ok().entity(entity).build();
		return response;
	}

	@GET
	@Path("/{commentId}")
	public Response readComment(@PathParam("paperId") long paperId, @PathParam("commentId") long commentId) {
		Comment comment = commentService.readComment(paperId, commentId);

		Response response;
		if (comment == null) response = Response.noContent().build();
		else response = Response.ok().entity(comment).build();

		return response;
	}

	@POST
	public Response createComment(@PathParam("paperId") long paperId, Comment comment) {
		Comment newComment = commentService.createComment(paperId, comment);

		Response response;
		if (newComment == null) response = Response.noContent().build();
		else response = Response.ok().entity(newComment).build();

		return response;
	}

	@PUT
	@Path("/{commentId}")
	public Response updateComment(@PathParam("paperId") long paperId, @PathParam("commentId") long id, Comment comment) {
		comment.setId(id);

		Comment newComment = commentService.updateComment(paperId, comment);

		Response response;
		if (newComment == null) response = Response.noContent().build();
		else response = Response.ok().entity(newComment).build();

		return response;
	}

	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("paperId") long paperId, @PathParam("commentId") long commentId) {
		commentService.deleteComment(paperId, commentId);
	}

}
