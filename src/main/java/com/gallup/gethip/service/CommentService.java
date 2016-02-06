package com.gallup.gethip.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.gallup.gethip.DataSourceManager;
import com.gallup.gethip.model.Comment;
import com.gallup.gethip.model.Paper;
import com.j256.ormlite.dao.Dao;
import com.sun.jersey.api.NotFoundException;

/**
 * 
 * @author Matthew Meacham
 * 
 *         Contains all the implementation for CRUD operations on the database for a comment
 *
 */
public class CommentService {

	// the resource not found exception
	private Response response = Response.status(Status.NOT_FOUND).build();

	public List<Comment> readAllComments(long paperId) {
		Paper paper = null;
		try {
			paper = getPaperDao().queryForId(String.valueOf(paperId));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (paper == null) throw new WebApplicationException(response);

		return new ArrayList<Comment>(paper.getComments());
	}

	public Comment readComment(long paperId, long commentId) {
		// TODO really should use custom error responses
		Paper paper = null;
		try {
			paper = getPaperDao().queryForId(String.valueOf(paperId));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (paper == null) throw new WebApplicationException(response);

		Comment comment = paper.getComments().stream().filter(e -> e.getId() == commentId).collect(Collectors.toList()).get(0);
		if (comment == null) throw new NotFoundException("That resource was not found");

		return comment;
	}

	public Comment createComment(long paperId, Comment comment) {
		Paper paper = null;
		try {
			paper = getPaperDao().queryForId(String.valueOf(paperId));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (paper == null) throw new WebApplicationException(response);

		Collection<Comment> comments = paper.getComments();
		comment.setId(comments.size() + 1);
		comments.add(comment);
		paper.setComments(comments);
		try {
			getPaperDao().update(paper);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return comment;
	}

	public Comment updateComment(long paperId, Comment comment) {
		Paper paper = null;
		try {
			paper = getPaperDao().queryForId(String.valueOf(paperId));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (paper == null) throw new WebApplicationException(response);

		Collection<Comment> comments = paper.getComments();
		// TODO for all update methods we should perform this ID check
		if (comment.getId() <= 0) return null;
		Comment previousComment = comments.stream().filter(e -> e.getId() == comment.getId()).collect(Collectors.toList()).get(0);
		comments.remove(previousComment);
		comments.add(comment);

		try {
			getPaperDao().update(paper);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return comment;
	}

	public void deleteComment(long paperId, long commentId) {
		Paper paper = null;
		try {
			paper = getPaperDao().queryForId(String.valueOf(paperId));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (paper == null) throw new WebApplicationException(response);

		Collection<Comment> comments = paper.getComments();
		if (commentId <= 0) return;
		comments.remove(comments.stream().filter(e -> e.getId() == commentId).collect(Collectors.toList()).get(0));

		try {
			getPaperDao().update(paper);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private Dao<Paper, String> getPaperDao() {
		Dao<Paper, String> dao = DataSourceManager.getInstance().getDao(Paper.class);
		return dao;
	}

}
