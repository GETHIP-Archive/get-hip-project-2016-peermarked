package com.gallup.gethip.service;

import java.util.List;

import org.glassfish.grizzly.http.server.HttpServer;

import com.gallup.gethip.Main;
import com.gallup.gethip.TestSetup;
import com.gallup.gethip.model.Profile;
import com.sun.jersey.api.client.Client;

import junit.framework.TestCase;

public class ProfileServiceTest extends TestCase {

	private HttpServer httpServer;

	private ProfileService profileService = new ProfileService();

	private final String PROFILE_NAME = "matthew1024921";

	public ProfileServiceTest(String testName) {
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

	public void testCreateProfile() {
		Profile profile1 = profileService.createProfile(new Profile(0L, PROFILE_NAME, "Matthew", "Meacham", "hunter4", 10));
		assertEquals(profile1.getProfileName(), PROFILE_NAME);
		assertEquals(profile1.getFirstName(), "Matthew");
		assertEquals(profile1.getLastName(), "Meacham");
		assertNotSame(profile1.getPassword(), "hunter4");
		assertNotSame(0L, profile1.getId());
		assertEquals(profile1.getScore(), 10);
		assertNotNull(profile1.getSalt());
		assertNotNull(profile1.getCreated());

		// Duplicate entry test
		Profile profile2 = profileService.createProfile(new Profile(0L, PROFILE_NAME, "NewMatthew", "ShouldntWork", "hunter5", 100));
		assertNull(profile2);
	}

	public void testReadAllProfiles() {
		List<Profile> profiles = profileService.readAllProfiles();
		assertTrue(profiles.size() > 0);
	}

	public void testReadProfile() {
		Profile profile1 = profileService.readProfile("matthew");
		assertNotNull(profile1);
		assertEquals(profile1.getProfileName(), "matthew");
		assertNotSame(profile1.getPassword(), "hunter2");

		// Random profile name that shouldn't exist
		Profile profile2 = profileService.readProfile("fdsajf;lkdasj;lkfjask;lfjaslkjfkasjfkljadsfjhaskjhfkjashfkjahskjfhakjhfakj");
		assertNull(profile2);
	}

	public void testUpdateProfile() {
		Profile profile = profileService.readProfile(PROFILE_NAME);
		profile.setFirstName("NewFirstName");
		profile.setLastName("NewLastName");
		Profile profile1 = profileService.updateProfile(profile);
		assertEquals(profile1.getFirstName(), "NewFirstName");
		assertEquals(profile1.getLastName(), "NewLastName");
		assertEquals(profile1.getId(), profile.getId());
		assertEquals(profile1.getPassword(), profile.getPassword());
		assertEquals(profile1.getProfileName(), profile.getProfileName());
		assertEquals(profile1.getSalt(), profile.getSalt());
		assertEquals(profile1.getScore(), profile.getScore());
		assertEquals(profile1.getCreated(), profile.getCreated());
	}

	public void testDeleteProfile() {
		profileService.deleteProfile(PROFILE_NAME);
		assertTrue(profileService.readProfile(PROFILE_NAME) == null);
	}

}
