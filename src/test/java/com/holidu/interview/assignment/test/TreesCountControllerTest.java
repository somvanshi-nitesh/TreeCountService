package com.holidu.interview.assignment.test;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.FileReader;

import org.glassfish.jersey.client.JerseyInvocation.Builder;
import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.holidu.interview.assignment.controller.TreesCountController;

/**
 * Contains the test cases for the trees count service
 *
 */
public class TreesCountControllerTest {
	@Test
	@DisplayName("TreeCountService test ~ Trees found")
	void treeCountServiceTest() throws IOException {

		TreesCountController treesCountController = new TreesCountController();

		final Response finalResponse = treesCountController.fetchTreeData(44.0, -73.0, 4.0);

		System.out.println(finalResponse.getEntity());

		assertTrue(finalResponse.getEntity().toString().contains("red maple"));

	}

	@Test
	@DisplayName("TreeCountService test ~ Tree not found")
	void noTreefoundTest() throws IOException {

		TreesCountController treesCountController = new TreesCountController();

		final Response finalResponse = treesCountController.fetchTreeData(44.0, 73.0, 4.0);

		System.out.println(finalResponse.getEntity());

		assertTrue(finalResponse.getEntity().toString().contains("No tree is found inside the circle"));

	}
	
	@Test
	@DisplayName("TreeCountService test ~ null path parameter")
	void nullParameterTest() throws IOException {

		TreesCountController treesCountController = new TreesCountController();

		final Response finalResponse = treesCountController.fetchTreeData(null, 73.0, 4.0);

		System.out.println(finalResponse.getEntity());

		assertTrue(finalResponse.getEntity().toString().contains("One or more parameter are null. Please provide the input values"));

	}
}
