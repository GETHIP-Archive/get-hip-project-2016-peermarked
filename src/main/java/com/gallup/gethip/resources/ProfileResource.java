package com.gallup.gethip.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.gallup.gethip.model.Profile;
import com.gallup.gethip.service.ProfileService;

/**
 * 
 * @author Matthew Meacham
 * 
 *         The implementation of these methods is separated so that implementation is bundled and the URIs are separated, this will make it easier to edit
 *
 */
@Path("/profiles")
@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public class ProfileResource {

	private ProfileService profileService = new ProfileService();

	@GET
	public List<Profile> readAllProfiles() {
		return profileService.readAllProfiles();
	}

	@GET
	@Path("/{profileName}")
	public Profile readProfile(@PathParam("profileName") String profileName) {
		return profileService.readProfile(profileName);
	}

	@POST
	public Profile createProfile(Profile profile) {
		return profileService.createProfile(profile);
	}

	@PUT
	@Path("/{profileName}")
	public Profile updateProfile(@PathParam("profileName") String profileName, Profile profile) {
		profile.setProfileName(profileName);
		return profileService.updateProfile(profile);
	}

	@DELETE
	@Path("/{profileName}")
	public void deleteProfile(@PathParam("profileName") String profileName) {
		profileService.deleteProfile(profileName);
	}

}
