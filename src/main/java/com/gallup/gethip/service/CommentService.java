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

	private final String OPENING_SPAN_TAG = "<span class=\"highlighted\">";
	private final String CLOSING_SPAN_TAG = "</span>";
	private final int OPENING_SPAN_TAG_LENGTH = OPENING_SPAN_TAG.length();
	private final int SPAN_TAGS_LENGTH = OPENING_SPAN_TAG_LENGTH + CLOSING_SPAN_TAG.length();

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

		// TODO 90% positive this will actually throw a null pointer exception before my null check can check
		Comment comment = paper.getComments().stream().filter(e -> e.getId() == commentId).collect(Collectors.toList()).get(0);
		if (comment == null) throw new NotFoundException("That resource was not found");

		return comment;
	}

	public Comment createComment(long paperId, Comment comment) {
		Paper paper = null;
		try {
			// TODO the way I am adding the span tags may not be the most efficient
			paper = getPaperDao().queryForId(String.valueOf(paperId));
			int numberOfComments = 0;
			int total = 0;
			String content = paper.getContent().replace(CLOSING_SPAN_TAG, "");
			String[] split = content.split(OPENING_SPAN_TAG);
			for (int i = 0; i < split.length; i++) {
				total += split[i].length();
				if (comment.getIndex() < total) {
					numberOfComments = i;
					break;
				}
			}

			int adjustedIndex = comment.getIndex() + (SPAN_TAGS_LENGTH * numberOfComments);

			StringBuilder builder = new StringBuilder(paper.getContent());
			builder.insert(adjustedIndex, OPENING_SPAN_TAG);
			builder.insert(adjustedIndex + OPENING_SPAN_TAG_LENGTH + comment.getLength(), CLOSING_SPAN_TAG);

			paper.setContent(builder.toString());

			getPaperDao().update(paper);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (paper == null) throw new WebApplicationException(response);

		Collection<Comment> comments = paper.getComments();
		// FIXME find a way to set the id, this method is egregious
		List<Paper> papers = null;
		try {
			papers = getPaperDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int totalComments = 0;
		for (Paper pap : papers) {
			totalComments += pap.getComments().size();
		}

		comment.setId(totalComments + 1);
		comments.add(comment);

		paper.setComments(comments);

		try {
			getPaperDao().update(paper);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return comment;
	}

	// TODO we need to change the <span> tags during updating
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
			// TODO need to make sure commentDao is a thing
			getCommentDao().update(comment);
			getPaperDao().update(paper);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return comment;
	}

	// TODO we need to remove the <span> tags during deletion
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
		Comment comment = comments.stream().filter(e -> e.getId() == commentId).collect(Collectors.toList()).get(0);
		comments.remove(comment);

		paper.setComments(comments);

		try {
			// TODO need to make sure this works
			getCommentDao().delete(comment);
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

	@SuppressWarnings("unchecked")
	private Dao<Comment, Long> getCommentDao() {
		Dao<Comment, Long> dao = DataSourceManager.getInstance().getDao(Comment.class);
		return dao;
	}

}
