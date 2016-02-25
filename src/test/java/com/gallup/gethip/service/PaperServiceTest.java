package com.gallup.gethip.service;

import java.util.Arrays;
import java.util.List;

import org.glassfish.grizzly.http.server.HttpServer;

import com.gallup.gethip.Main;
import com.gallup.gethip.TestSetup;
import com.gallup.gethip.model.Paper;
import com.sun.jersey.api.client.Client;

import junit.framework.TestCase;

public class PaperServiceTest extends TestCase {

	private HttpServer httpServer;

	private PaperService paperService = new PaperService();

	private long createdPaperId;

	public PaperServiceTest(String testName) {
		super(testName);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// start the Grizzly2 web container
		httpServer = Main.startServer();
		new TestSetup();

		// create the client
		Client.create();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		httpServer.stop();
	}

	public void testReadAllPapers() {
		List<Paper> papers = paperService.readAllPapers();
		assertTrue(papers.size() > 0);
	}

	public void testReadPaper() {
		Paper paper = paperService.readPaper(createdPaperId);
		assertNotNull(paper);
	}

	public void testReadRecentPapers() {
		List<Paper> papers = paperService.readRecentPapers();
		assertNotNull(papers);
		assertTrue(papers.size() <= paperService.NUMBER_OF_PAPERS_IN_RECENT);
	}

	public void testReadFilteredPapers() {
		List<Paper> papers = paperService.readFilteredPapers("Java");
		assertNotNull(papers);
		assertTrue(papers.size() > 0);
	}

	public void testReadAllPapersForYear() {
		List<Paper> papers = paperService.readAllPapersForYear(2016);
		assertNotNull(papers);
		assertTrue(papers.size() > 0);
	}

	public void testReadAllPapersPaginated() {
		List<Paper> papers = paperService.readAllPapersPaginated(0, 10);
		assertNotNull(papers);
		assertTrue(papers.size() > 0);
	}

	public void testCreatePaper() {
		Paper paper1 = paperService.createPaper(new Paper("matthew", "Java Programming", "Java Is Cool", Arrays.asList("Java", "Programming", "Computers")));
		createdPaperId = paper1.getId();
		assertEquals(paper1.getAuthor(), "matthew");
		assertEquals(paper1.getTitle(), "Java Programming");
		assertEquals(paper1.getContent(), "Java Is Cool");
		assertTrue(paper1.getThemes().size() > 0);
		assertNotNull(paper1.getCreated());
		assertNotNull(paper1.getId());
		assertTrue(paper1.getComments().size() == 0);
		assertTrue(paper1.getLinks().size() == 0);
	}

	public void testUpdatePaper() {
		Paper paper = paperService.readPaper(createdPaperId);
		paper.setAuthor("kaitlyn");
		paper.setTitle("C++ is better than Java Programming");
		paper.setContent("C++ is cooler than Java");

		Paper updatedPaper = paperService.updatePaper(paper);
		assertNotNull(updatedPaper);
		assertEquals(updatedPaper.getAuthor(), "kaitlyn");
		assertEquals(updatedPaper.getTitle(), "C++ is better than Java Programming");
		assertEquals(updatedPaper.getContent(), "C++ is cooler than Java");
		assertEquals(paper.getCreated(), updatedPaper.getCreated());
		assertEquals(paper.getId(), updatedPaper.getId());
		assertEquals(paper.getComments(), updatedPaper.getComments());
		assertEquals(paper.getLinks(), updatedPaper.getLinks());
		assertEquals(paper.getThemes(), updatedPaper.getThemes());
	}

	public void testDeletePaper() {
		paperService.deletePaper(createdPaperId);
		assertNull(paperService.readPaper(createdPaperId));
	}

}
