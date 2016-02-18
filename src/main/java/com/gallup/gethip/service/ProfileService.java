package com.gallup.gethip.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
		
		String[] hashed = null;
		try {
			hashed = hashSHA256(profile.getPassword());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		profile.setSalt(hashed[0]);
		profile.setPassword(hashed[1]);
		profile.setProfileName(profile.getProfileName());
		
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

	/*
	 * 
	 * Salting and hashing for passwords in profiles
	 * 
	 */

	private SecureRandom secureRandom = new SecureRandom();
	private MessageDigest digest;
	private int saltSize = 32;
	private int iterations = 1;

	public String[] hashSHA256(String stringToHash) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return hash(stringToHash, "SHA-256", "UTF-8");
	}

	public String[] hash(String stringToHash, String hashingAlgorithm, String charSet) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String[] returnArray = new String[iterations + 1];
		byte[] hash = null;
		for (int i = 0; i < iterations; i++) {
			byte[] salt = new byte[saltSize];
			this.secureRandom.nextBytes(salt);
			this.digest = MessageDigest.getInstance(hashingAlgorithm);
			String saltString = bytesToHex(salt);
			hash = i == 0 ? digest.digest((saltString + "$" + stringToHash).getBytes(charSet)) : digest.digest((saltString + "$" + bytesToHex(hash)).getBytes(charSet));
			returnArray[i] = saltString;
		}
		returnArray[this.iterations] = bytesToHex(hash);
		return returnArray;
	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		byte[] arrayOfByte;
		int j = (arrayOfByte = bytes).length;
		for (int i = 0; i < j; i++) {
			byte byt = arrayOfByte[i];
			result.append(Integer.toString((byt & 0xFF) + 256, 16).substring(1));
		}
		return result.toString();
	}

}
