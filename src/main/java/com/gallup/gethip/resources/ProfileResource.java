package com.gallup.gethip.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
	public Response readAllProfiles() {
		GenericEntity<List<Profile>> entity = new GenericEntity<List<Profile>>(profileService.readAllProfiles()) {
		};
		return Response.ok().entity(entity).build();
	}

	@GET
	@Path("/{profileName}")
	public Response readProfile(@PathParam("profileName") String profileName) {
		Profile profile = profileService.readProfile(profileName);

		Response response;
		if (profile == null) response = Response.noContent().build();
		else response = Response.ok().entity(profile).build();

		return response;
	}

	@POST
	public Response createProfile(Profile profile, @Context UriInfo uriInfo) {
		Profile newProfile = profileService.createProfile(profile);

		Response response;
		if (newProfile == null) response = Response.noContent().build();
		else {
			URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newProfile.getId())).build();
			response = Response.created(uri).entity(profileService.createProfile(profile)).build();
		}

		return response;
	}

	@PUT
	@Path("/{profileName}")
	public Response updateProfile(@PathParam("profileName") String profileName, Profile profile) {
		profile.setProfileName(profileName);
		Profile newProfile = profileService.updateProfile(profile);

		Response response;
		if (newProfile == null) response = Response.noContent().build();
		else response = Response.ok().entity(newProfile).build();

		return response;
	}

	@DELETE
	@Path("/{profileName}")
	public void deleteProfile(@PathParam("profileName") String profileName) {
		profileService.deleteProfile(profileName);
	}

}
