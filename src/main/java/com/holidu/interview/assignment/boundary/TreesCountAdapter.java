package com.holidu.interview.assignment.boundary;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * This Adapter Class calculates the distance between two points and decides if
 * that point falls inside the circle and it takes three parameters:
 * 
 * @param latitude
 *            latitude(Y value) of the center of the circle from the distance of
 *            the tress would be calculated
 * @param longitude
 *            longitude(X value i.e. From -180 deg to +180 deg) of the centre of
 *            the circle from which the distance of the tress would be
 *            calculated
 * @param radius
 *            Radius of the circle inside which all the tree count has to be
 *            processed
 * @return hashmap which holds the (key,value) pairs of trees common name and
 *         count within given radius of the circle
 * @throws JsonParseException,
 *             JsonMappingException, IOException
 */
public class TreesCountAdapter {

	private Double latitude;
	private Double longitude;
	private Double radius;

	private static final String URI = "https://data.cityofnewyork.us/resource/nwxe-4ae8.json";

	public TreesCountAdapter() {
	}
	
	public TreesCountAdapter(Double latitude, Double longitude, Double radius) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
	}

	/**
	 * This method fetches the trees data from the api and calculates the distance
	 * from the given center of the circle
	 * 
	 * @return hashmap which holds the (key,value) pairs of trees common name and
	 *         count within given radius of the circle
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public HashMap<String, Integer> treeDataCalculation() throws JsonParseException, JsonMappingException, IOException {

		Client client = ClientBuilder.newClient();
		final WebTarget target = client.target(URI);

		final Response response = target.request(MediaType.APPLICATION_JSON).get();
		
		final ObjectMapper mapper = new ObjectMapper();

		final String jsonString = response.readEntity(String.class);
		final Reader reader = new StringReader(jsonString);
		final ArrayNode node = mapper.readValue(reader, ArrayNode.class);

		// Map to hold (key,value) pairs as trees common name and their count within the
		// circle
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();

		for (JsonNode jsonNode : node) {

			/*
			 * Calculate the distance between two point using Mathematical equation ~
			 * 
			 * (X-X1)^2 + (Y-Y1)^2 = Radius^2, whereas X = longitude(X) value of the center
			 * of the circle (provided by the user as a path parameter; Y = latitude(Y)
			 * value of the center of the circle (provided by X1 = longitude(X) value of the
			 * tree from the json response of the API Y1 = latitude(Y) value of the tree
			 * from the json response of the API
			 * 
			 */
			double sum = Math.pow(latitude - jsonNode.get("latitude").asDouble(), 2)
					+ Math.pow(longitude - jsonNode.get("longitude").asDouble(), 2);

			// If sum of (X-X1)^2 + (Y-Y1)^2 is less than radius^2, which implies that tree
			// resides inside the circle
			if (sum < Math.pow(radius, 2)) {
				System.out.println("Point (" + jsonNode.get("latitude") + "," + jsonNode.get("longitude")
						+ ") is inside the circle with center (" + latitude + "," + longitude + ") and radius :"
						+ radius);

				// Existence check for trees common name which needs to be checked
				if (jsonNode.get("spc_common") != null) {

					// first occurrence has been added to the map
					if (hmap.isEmpty()) {
						hmap.put(jsonNode.get("spc_common").asText(), 1);
					} else if (hmap.containsKey(jsonNode.get("spc_common").asText())) {

						// Loop for multiple occurrence of the trees in the given radius
						for (Map.Entry<String, Integer> me : hmap.entrySet()) {
							if (jsonNode.get("spc_common") != null
									&& (jsonNode.get("spc_common").asText().equals(me.getKey()))) {
								// Increment the tree count counter/value if it falls inside the circle
								Integer value = (Integer) me.getValue() + 1;
								me.setValue(value);
							}
						}
					}
					// First occurrence of this particular tree
					else {
						hmap.put(jsonNode.get("spc_common").asText(), 1);
					}
				}
			}
			// Tree falls outside the circle
			else {
				System.out.println("Point (" + jsonNode.get("latitude") + "," + jsonNode.get("longitude")
						+ ") is not inside the circle with center (" + latitude + "," + longitude + ") and radius"
						+ radius);
			}
		}
		// Returning the resultant hashmap with trees common name and count values
		return hmap;
	}
}
