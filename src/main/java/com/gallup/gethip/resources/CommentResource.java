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
import javax.ws.rs.core.MediaType;

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
	public List<Comment> readAllComments(@PathParam("paperId") long paperId) {
		return commentService.readAllComments(paperId);
	}

	@GET
	@Path("/{commentId}")
	public Comment readComment(@PathParam("paperId") long paperId, @PathParam("commentId") long commentId) {
		return commentService.readComment(paperId, commentId);
	}

	@POST
	public Comment createComment(@PathParam("paperId") long paperId, Comment comment) {
		return commentService.createComment(paperId, comment);
	}

	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam("paperId") long paperId, @PathParam("commentId") long id, Comment comment) {
		comment.setId(id);
		return commentService.updateComment(paperId, comment);
	}

	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("paperId") long paperId, @PathParam("commentId") long commentId) {
		commentService.deleteComment(paperId, commentId);
	}

}
