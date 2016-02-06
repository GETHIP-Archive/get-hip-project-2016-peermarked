package com.gallup.gethip.service;

import java.sql.SQLException;
import java.util.List;

import com.gallup.gethip.Counter;
import com.gallup.gethip.DataSourceManager;
import com.gallup.gethip.model.Profile;
import com.j256.ormlite.dao.Dao;

/**
 * 
 * @author Matthew Meacham
 * 
 *         Contains all the implementation for CRUD operations on the database for a profile
 *
 */
public class ProfileService {

	public ProfileService() {

	}

	public List<Profile> readAllProfiles() {
		List<Profile> profiles = null;
		try {
			profiles = getDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return profiles;
	}

	public Profile readProfile(String profileName) {
		Profile profile = null;
		try {
			profile = getDao().queryForEq("profile_name", profileName).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return profile;
	}

	public Profile createProfile(Profile profile) {
		profile.setId(Counter.getNextProfileId());
		try {
			getDao().createIfNotExists(profile);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return profile;
	}

	public Profile updateProfile(Profile profile) {
		if (profile.getProfileName().isEmpty()) return null;
		try {
			getDao().update(profile);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return profile;
	}

	public void deleteProfile(String profileName) {
		try {
			getDao().delete(getDao().queryForEq("profile_name", profileName).get(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private Dao<Profile, String> getDao() {
		Dao<Profile, String> dao = DataSourceManager.getInstance().getDao(Profile.class);
		return dao;
	}

}
