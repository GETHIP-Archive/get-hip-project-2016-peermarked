package com.gallup.gethip;

import java.sql.SQLException;
import java.util.ArrayList;

import com.gallup.gethip.model.Comment;
import com.gallup.gethip.model.Paper;
import com.gallup.gethip.model.Profile;
import com.gallup.gethip.service.CommentService;
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

	private void createProfiles(ConnectionSource connectionSource) throws SQLException {
		ProfileService profileService = new ProfileService();

		Profile profile1 = new Profile(0L, "matthew", "Matthew", "Meacham", "hunter2", 100);
		profile1.setId(0L);
		Profile profile2 = new Profile(0L, "kaitlyn", "Kaitlyn", "Meacham", "hunter3", 0);
		profile2.setId(1L);
		profileService.createProfile(profile1);
		profileService.createProfile(profile2);

		// printFields((Profile) DataSourceManager.getInstance().getDao(Profile.class).queryForId(0L));
		printFields((Profile) DataSourceManager.getInstance().getDao(Profile.class).queryForEq("profile_name", "matthew").get(0));
		printFields((Profile) DataSourceManager.getInstance().getDao(Profile.class).queryForEq("profile_name", "kaitlyn").get(0));
	}

	private void createPapers(ConnectionSource connectionSource) throws SQLException {
		PaperService paperService = new PaperService();
		CommentService commentService = new CommentService();

		ArrayList<String> themes1 = new ArrayList<String>();
		themes1.add("Java");
		themes1.add("Programming");
		paperService.createPaper(new Paper("Matthew", "Java Programming in Depth", "Java is cool", themes1));
		long paperId = ((Paper) DataSourceManager.getInstance().getDao(Paper.class).queryForEq("content", "Java is cool").get(0)).getId();
		commentService.createComment(paperId, new Comment(0L, "Hello Kaitlyn Meacham", "Matthew Meacham", 0, 5));
		
		ArrayList<String> themes2 = new ArrayList<String>();
		themes2.add("Soccer");
		themes2.add("Sports");
		paperService.createPaper(new Paper("Kaitlyn", "Soccer and how to play", "Kick the ball", themes2));

		printFields((Paper) DataSourceManager.getInstance().getDao(Paper.class).queryForEq("author", "Matthew").get(0));
		printFields((Paper) DataSourceManager.getInstance().getDao(Paper.class).queryForEq("author", "Kaitlyn").get(0));
	}

	private void printFields(Profile profile) {
		System.out.println("---------------------------");
		System.out.println(profile);
		System.out.println(profile.getProfileName());
		System.out.println(profile.getFirstName());
		System.out.println(profile.getLastName());
		System.out.println(profile.getPassword());
		System.out.println(profile.getSalt());
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
