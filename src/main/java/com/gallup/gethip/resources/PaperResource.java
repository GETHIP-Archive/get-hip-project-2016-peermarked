package com.gallup.gethip.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.gallup.gethip.model.Paper;
import com.gallup.gethip.service.PaperService;

/**
 * 
 * @author Matthew Meacham
 * 
 *         The implementation of these methods is separated so that implementation is bundled and the URIs are separated, this will make it easier to edit
 *
 */
@Path("/papers")
@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public class PaperResource {

	private PaperService paperService = new PaperService();

	@GET
	public Response readAllPapers(@QueryParam("year") int year, @QueryParam("start") int start, @QueryParam("size") int size) {
		GenericEntity<List<Paper>> entity = null;
		if (year > 0) {
			entity = new GenericEntity<List<Paper>>(paperService.readAllPapersForYear(year)) {
			};
		}
		if (start >= 0 && size > 0) {
			entity = new GenericEntity<List<Paper>>(paperService.readAllPapersPaginated(start, size)) {
			};
		}

		Response response = Response.ok().entity(entity).build();
		return response;
	}

	// TODO for all these read methods in resource, we need to add to the links
	@GET
	@Path("/{paperId}")
	public Response readPaper(@PathParam("paperId") long id, @Context UriInfo uriInfo) {
		Paper paper = paperService.readPaper(id);

		Response response;
		if (paper == null) response = Response.noContent().build();
		else {
			// FIXME these need to be fixed
			// paper.addLink(getUriForSelf(uriInfo, paper), "self");
			// paper.addLink(getUriForProfile(uriInfo, paper), "profile");
			// paper.addLink(getUriForComments(uriInfo, paper), "comments");
			response = Response.ok().entity(paper).build();
		}

		return response;
	}

	@GET
	@Path("/recent")
	public Response readRecentPapers() {
		GenericEntity<List<Paper>> entity = new GenericEntity<List<Paper>>(paperService.readRecentPapers()) {

		};

		Response response = Response.ok().entity(entity).build();
		return response;
	}

	@GET
	@Path("/filter/{filterCriteria}")
	public Response readFilteredPapers(@PathParam("filterCriteria") String filterCriteria) {
		GenericEntity<List<Paper>> entity = null;
		if (filterCriteria.trim().isEmpty()) {
			entity = new GenericEntity<List<Paper>>(new ArrayList<Paper>()) {
			};
			return Response.status(400).entity(entity).build();
		}

		entity = new GenericEntity<List<Paper>>(paperService.readFilteredPapers(filterCriteria)) {
		};

		return Response.ok().entity(entity).build();
	}

	@POST
	public Response createPaper(Paper paper, @Context UriInfo uriInfo) {
		Paper newPaper = paperService.createPaper(paper);

		Response response;
		if (newPaper == null) response = Response.noContent().build();
		else {
			String newId = String.valueOf(newPaper.getId());
			URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();

			// FIXME
			// newPaper.addLink(getUriForSelf(uriInfo, newPaper), "self");
			// newPaper.addLink(getUriForProfile(uriInfo, newPaper), "profile");
			// newPaper.addLink(getUriForComments(uriInfo, newPaper), "comments");
			response = Response.created(uri).entity(newPaper).build();
		}
		return response;
	}

	@PUT
	@Path("/{paperId}")
	public Response updatePaper(@PathParam("paperId") long id, Paper paper) {
		paper.setId(id);
		Paper newPaper = paperService.updatePaper(paper);

		Response response;
		if (newPaper == null) response = Response.noContent().build();
		else response = Response.ok().entity(newPaper).build();

		return response;
	}

	@DELETE
	@Path("/{paperId}")
	public void deletePaper(@PathParam("paperID") long id) {
		paperService.deletePaper(id);
	}

	// Leads to the comments information
	@Path("/{paperId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}

	private String getUriForSelf(UriInfo uriInfo, Paper paper) {
		if (paper == null) return "";
		return uriInfo.getBaseUriBuilder().path(PaperResource.class).path(Long.toString(paper.getId())).build().toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Paper paper) {
		return uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(paper.getAuthor()).build().toString();
	}

	// TODO this is a bad method for getting the comments URI, figure out different way, this is too hardcoded
	private String getUriForComments(UriInfo uriInfo, Paper paper) {
		return uriInfo.getBaseUriBuilder().path(PaperResource.class).path(String.valueOf(paper.getId())).path("comments").build().toString();
	}

}
