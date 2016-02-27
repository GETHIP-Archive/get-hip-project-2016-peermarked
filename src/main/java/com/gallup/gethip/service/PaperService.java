package com.gallup.gethip.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gallup.gethip.DataSourceManager;
import com.gallup.gethip.model.Paper;
import com.j256.ormlite.dao.Dao;

/**
 * 
 * @author Matthew Meacham
 * 
 *         Contains all the implementation for CRUD operations on the database for a paper
 *
 */
public class PaperService {

	final int NUMBER_OF_PAPERS_IN_RECENT = 10;

	public PaperService() {

	}

	public List<Paper> readAllPapers() {
		List<Paper> papers = null;
		try {
			papers = getDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return papers;
	}

	public Paper readPaper(long id) {
		Paper paper = null;
		try {
			paper = getDao().queryForId(String.valueOf(id));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return paper;
	}

	public List<Paper> readRecentPapers() {
		try {
			return readAllPapersPaginated((int) getDao().countOf() - NUMBER_OF_PAPERS_IN_RECENT, NUMBER_OF_PAPERS_IN_RECENT);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Paper>();
	}

	// FIXME this method is going to need vast improvement, we can't just scan through all the papers in the database every time
	// Might need database restructuring, but for small amounts this will work fine
	// Furthermore, in order to save processing power "relevance" is gonna be bad because I have it all in one for loop
	// To increase relevance ordering, we would need each thing in its own for loop
	public List<Paper> readFilteredPapers(String filterCriteria) {
		List<Paper> filteredPapers = new ArrayList<Paper>();
		// Filter at the underscores and the spaces
		// TODO this current method needs to be changed depending on how its passed in the URL
		String[] words = filterCriteria.split("_|\\%20");

		System.out.println(Arrays.asList(words));

		List<Paper> allPapers = readAllPapers();
		for (Paper paper : allPapers) {
			if (paper.getTitle().equals(filterCriteria)) filteredPapers.add(paper);
			if (paper.getAuthor().equals(filterCriteria)) filteredPapers.add(paper);

			for (String word : words) {
				// if (paper.getThemes().contains(word)) filteredPapers.add(paper);
				if (paper.getContent().contains(word)) filteredPapers.add(paper);
			}
		}

		return filteredPapers;
	}

	public List<Paper> readAllPapersForYear(int year) {
		List<Paper> papersForYear = new ArrayList<Paper>();
		Calendar cal = Calendar.getInstance();
		for (Paper paper : getDao()) {
			cal.setTime(paper.getCreated());
			if (cal.get(Calendar.YEAR) == year) {
				papersForYear.add(paper);
			}
		}
		return papersForYear;
	}

	// This will read all pages in the database from the start to the start + size element
	public List<Paper> readAllPapersPaginated(int start, int size) {
		List<Paper> list = null;
		try {
			list = getDao().queryForAll();
			// Avoid IndexOutOfBoundsExceptions
			if (start < 0) start = 0;
			if (start + size > list.size()) size = list.size() - start;

			list = list.subList(start, start + size);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// TODO the create method in Dao is actually a success/failure, so it may be a good idea to return a success/failure response
	public Paper createPaper(Paper paper) {
		paper.setCreated(new Date());
		try {
			paper.setId(getDao().countOf() + 1L);
			// TODO change back to just create
			getDao().createIfNotExists(paper);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return paper;
	}

	public Paper updatePaper(Paper paper) {
		if (paper.getId() <= 0L) return null;
		try {
			getDao().update(paper);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return paper;
	}

	public void deletePaper(long id) {
		try {
			getDao().delete(getDao().queryForId(String.valueOf(id)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private Dao<Paper, String> getDao() {
		Dao<Paper, String> dao = DataSourceManager.getInstance().getDao(Paper.class);
		return dao;
	}

}
