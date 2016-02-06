package com.gallup.gethip.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.gallup.gethip.Counter;
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

	// TODO for all read methods, I need to have a DataNotFoundException (custom-made) thrown
	public Paper readPaper(long id) {
		Paper paper = null;
		try {
			paper = getDao().queryForId(String.valueOf(id));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return paper;
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
			if (start + size > list.size()) return new ArrayList<Paper>();
			list = list.subList(start, start + size);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// TODO the create method in Dao is actually a success/failure, so it may be a good idea to return a success/failure response
	public Paper createPaper(Paper paper) {
		paper.setId(Counter.getNextPaperId());
		try {
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
