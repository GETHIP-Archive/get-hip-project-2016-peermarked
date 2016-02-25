package com.gallup.gethip.service;

import java.util.Arrays;
import java.util.List;

import org.glassfish.grizzly.http.server.HttpServer;

import com.gallup.gethip.Main;
import com.gallup.gethip.TestSetup;
import com.gallup.gethip.model.Comment;
import com.gallup.gethip.model.Paper;
import com.sun.jersey.api.client.Client;

import junit.framework.TestCase;

public class CommentServiceTest extends TestCase {

	private HttpServer httpServer;

	private CommentService commentService = new CommentService();
	private PaperService paperService = new PaperService();
	private Paper paper;
	private long createdCommentId = 0L;

	public CommentServiceTest(String testName) {
		super(testName);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// start the Grizzly2 web container
		httpServer = Main.startServer();
		new TestSetup();

		paper = paperService.createPaper(new Paper("Matthew", "I can Java good", "Java is a cool language", Arrays.asList("Java", "Programming", "Stuff")));
		// TODO this is really a bad way to run tests, because this assumes creation works, and then does it during setup
		Comment comment = new Comment(0L, "Java is coolio man", "MattJava", 0, 3);
		comment = commentService.createComment(paper.getId(), comment);
		createdCommentId = comment.getId();

		// create the client
		Client.create();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		httpServer.stop();
	}

	public void testReadAllComments() {
		List<Comment> comments = commentService.readAllComments(paper.getId());
		assertTrue(comments.size() > 0);
	}

	public void testReadComment() {
		Comment comment = commentService.readComment(paper.getId(), createdCommentId);

		assertNotNull(comment);
		assertTrue(comment.getAuthor().equals("MattJava"));
		assertTrue(comment.getComment().equals("Java is coolio man"));
		assertTrue(comment.getIndex() == 0);
		assertTrue(comment.getLength() == 3);
		assertNotNull(comment.getCreated());
		assertNotNull(comment.getId());
	}

	public void testCreateComment() {
		Comment comment = new Comment(0L, "My comment", "Matteo", 0, 5);
		comment = commentService.createComment(paper.getId(), comment);
		// createdCommentId = comment.getId();

		assertTrue(paperService.readPaper(paper.getId()).getComments().size() > 0);
		assertTrue(commentService.readComment(paper.getId(), comment.getId()).getAuthor().equals("Matteo"));
		assertTrue(commentService.readComment(paper.getId(), comment.getId()).getComment().equals("My comment"));
		assertTrue(commentService.readComment(paper.getId(), comment.getId()).getIndex() == 0);
		assertTrue(commentService.readComment(paper.getId(), comment.getId()).getLength() == 5);
		assertNotNull(comment.getCreated());
		assertNotNull(comment.getId());
		assertTrue(paperService.readPaper(paper.getId()).getContent().contains("<span"));
	}

	public void testUpdateComment() {
		Comment comment = commentService.readComment(paper.getId(), createdCommentId);
		comment.setAuthor("New Author");
		comment.setComment("New Comment");
		comment.setIndex(5);
		comment.setLength(2);
		Comment updatedComment = commentService.updateComment(paper.getId(), comment);

		assertNotNull(updatedComment);
		assertTrue(updatedComment.getAuthor().equals("New Author"));
		assertTrue(updatedComment.getComment().equals("New Comment"));
		assertTrue(updatedComment.getIndex() == 5);
		assertTrue(updatedComment.getLength() == 2);
		assertNotNull(updatedComment.getId());
		assertNotNull(updatedComment.getCreated());
		assertTrue(comment.getCreated().equals(updatedComment.getCreated()));
		assertTrue(comment.getId() == updatedComment.getId());

	}

	public void testDeleteComment() {
		Comment comment = commentService.readComment(paper.getId(), createdCommentId);
		commentService.deleteComment(paper.getId(), createdCommentId);

		assertNull(commentService.readComment(paper.getId(), comment.getId()));
	}

}
