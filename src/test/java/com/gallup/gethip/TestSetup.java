package com.gallup.gethip;

import java.sql.SQLException;

import com.gallup.gethip.model.Comment;
import com.gallup.gethip.model.Paper;
import com.gallup.gethip.model.Profile;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class TestSetup {

	public TestSetup() {
		ConnectionSource connectionSource = createDatabaseConnection();
		buildDaos();
		new DatabaseSetup(connectionSource);
	}

	private static ConnectionSource createDatabaseConnection() {
		String databaseUrl = "jdbc:mysql://jgetrost.com:3306/peermarked";
		// String databaseUrl = "jdbc:mysql://127.0.0.1:3306/peerreview";
		ConnectionSource connectionSource = null;
		try {
			connectionSource = new JdbcConnectionSource(databaseUrl);
			((JdbcConnectionSource) connectionSource).setUsername("peermarked");
			((JdbcConnectionSource) connectionSource).setPassword("gethip");
			// ((JdbcConnectionSource) connectionSource).setUsername("root");
			// ((JdbcConnectionSource) connectionSource).setPassword("MyPassword");
			DataSourceManager.setConnectionSource(connectionSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connectionSource;
	}

	private static void buildDaos() {
		try {
			DataSourceManager.addDao(Comment.class);
			DataSourceManager.addDao(Paper.class);
			DataSourceManager.addDao(Profile.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
