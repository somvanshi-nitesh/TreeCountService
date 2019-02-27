package com.holidu.interview.assignment.controller;

import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.holidu.interview.assignment.boundary.TreesCountAdapter;

import java.util.HashMap;

/*
 * TreesCount Service Implementation
 *
 */
@Path("/")
public class TreesCountController {

	private final String NO_TREE_MSG = "No tree is found inside the circle";
	private final String STATUS_500_MSG = "Internal server error occurred, please check the logs for more details.";

	final JsonObject NO_TREE_RESPONSE = Json.createObjectBuilder().add("Status", "OK").add("code", 200)
			.add("Message", NO_TREE_MSG).build();
	final JsonObject STATUS_500_RESPONSE = Json.createObjectBuilder().add("Status", "NOK").add("code", 500)
			.add("Message", STATUS_500_MSG).build();

	final ObjectMapper mapper = new ObjectMapper();

	public TreesCountController() {
	}

	/**
	 * This service takes two parameters as mentioned below: 1. A Cartesian Point
	 * specifying a center point along the x & y plane 2. A search radius in meters
	 * a detail calculation (gUID) call at the respective and retrieves the count of
	 * trees which falls inside the provided radius
	 * 
	 * @param latitude
	 *            latitude(Y value) of the center of the circle from the distance of
	 *            the tress would be calculated
	 * @param longitude
	 *            longitude(X value i.e. From -180 deg to +180 deg) of the center of
	 *            the circle from which the distance of the tress would be
	 *            calculated
	 * @param radius
	 *            Radius of the circle inside which all the tree count has to be
	 *            processed
	 * @return response
	 * @throws JsonParseException,
	 *             JsonMappingException, IOException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("latitude/{latitude}/longitude/{longitude}/radius/{radius}")
	public Response fetchTreeData(@PathParam("latitude") Double latitude, @PathParam("longitude") Double longitude,
			@PathParam("radius") Double radius) throws JsonParseException, JsonMappingException, IOException {

		if (latitude == null || longitude == null || radius == null) {
			return Response.serverError().entity("One or more parameter are null. Please provide the input values")
					.build();
		}

		TreesCountAdapter adapter = new TreesCountAdapter(latitude, longitude, radius);
		
		HashMap<String, Integer> hmap = new HashMap<String,Integer>();
		
		try {
			hmap = adapter.treeDataCalculation();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(STATUS_500_RESPONSE).build();
		}

		if (hmap.isEmpty()) {
			return Response.status(200).entity(NO_TREE_RESPONSE).build();
		}

		return Response.status(200).entity(hmap).build();

	}
}
