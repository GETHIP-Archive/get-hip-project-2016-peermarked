package com.gallup.gethip;

import java.sql.SQLException;
import java.util.ArrayList;

import com.gallup.gethip.model.Comment;
import com.gallup.gethip.model.Paper;
import com.gallup.gethip.model.Profile;
import com.gallup.gethip.service.PaperService;
import com.gallup.gethip.service.ProfileService;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * 
 * @author Matthew Meacham
 *
 *         This class sets up the database if need be
 *
 */
public class DatabaseSetup {

	public DatabaseSetup(ConnectionSource connectionSource) {
		try {
			TableUtils.createTableIfNotExists(connectionSource, Comment.class);
			TableUtils.createTableIfNotExists(connectionSource, Paper.class);
			TableUtils.createTableIfNotExists(connectionSource, Profile.class);

			createProfiles(connectionSource);
			createPapers(connectionSource);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void createProfiles(ConnectionSource connectionSource) throws SQLException {
		ProfileService profileService = new ProfileService();
		profileService.createProfile(new Profile(1L, "matthew", "Matthew", "Meacham", 100));
		profileService.createProfile(new Profile(2L, "kaitlyn", "Kaitlyn", "Meacham", 0));

		printFields((Profile) DataSourceManager.getInstance().getDao(Profile.class).queryForId(1L));
		printFields((Profile) DataSourceManager.getInstance().getDao(Profile.class).queryForId(2L));
	}

	@SuppressWarnings("unchecked")
	private void createPapers(ConnectionSource connectionSource) throws SQLException {
		PaperService paperService = new PaperService();

		ArrayList<String> themes1 = new ArrayList<String>();
		themes1.add("Java");
		themes1.add("Programming");
		paperService.createPaper(new Paper(1L, "Matthew", "Java Programming in Depth", "Java is cool", themes1));

		ArrayList<String> themes2 = new ArrayList<String>();
		themes2.add("Soccer");
		themes2.add("Sports");
		paperService.createPaper(new Paper(2L, "Kaitlyn", "Soccer and how to play", "Kick the ball", themes2));

		printFields((Paper) DataSourceManager.getInstance().getDao(Paper.class).queryForId(1L));
		printFields((Paper) DataSourceManager.getInstance().getDao(Paper.class).queryForId(2L));
	}

	private void printFields(Profile profile) {
		System.out.println("---------------------------");
		System.out.println(profile.getProfileName());
		System.out.println(profile.getFirstName());
		System.out.println(profile.getLastName());
		System.out.println(profile.getScore());
		System.out.println(profile.getCreated());
	}

	private void printFields(Paper paper) {
		System.out.println("---------------------------");
		System.out.println(paper.getAuthor());
		System.out.println(paper.getTitle());
		System.out.println(paper.getContent());
		System.out.println(paper.getCreated());
		System.out.println(paper.getThemes());
		System.out.println(paper.getLinks());
		System.out.println(paper.getComments());
	}

}
