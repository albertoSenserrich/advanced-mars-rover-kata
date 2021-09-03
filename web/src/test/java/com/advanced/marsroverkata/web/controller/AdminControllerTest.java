package com.advanced.marsroverkata.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.advanced.marsroverkata.web.model.rest.ExplorationStatisticsResponse;
import com.advanced.marsroverkata.web.service.MarsRoverService;
import com.google.gson.Gson;

/**
 * @author Alberto Senserrich Montals
 *
 */
/**
 * @author Alberto Senserrich Montals
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

	@Autowired
	protected MockMvc mvc;

	@MockBean	
	MarsRoverService marsRoverServiceService;

	Gson gson = new Gson();

	private final static String DEFAULT_URI = "/adminService/";
	private final static String HEALTH_CHECK_URI = DEFAULT_URI + "healthCheck";
	private final static String CLEAR_CACHE_URI = DEFAULT_URI + "clearCache";
	private final static String SATATISTICS_URI = DEFAULT_URI + "statistics";

	private final static String NO_DATA_FOUND_ERR_MESSAGE = "No data aviable";
	private final static String HEALTH_CHECK_RESULT_OK = "Space station on-line";
	private final static int HTTP_STATUS_OK = 200;
	private final static int HTTP_STATUS_ERROR = 400;

	@Test
	public void testHealthCheck() throws Exception {
		// having
		// when
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(HEALTH_CHECK_URI)).andReturn();
		// then
		String content = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(HTTP_STATUS_OK, status);
		assertEquals(content, HEALTH_CHECK_RESULT_OK);
	}

	@Test
	public void testClearCache() throws Exception {
		// having
		// when
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(CLEAR_CACHE_URI)).andReturn();
		// then
		int status = mvcResult.getResponse().getStatus();
		assertEquals(HTTP_STATUS_OK, status);
	}

	@Test
	public void testRetrieveStatisticsForAllEelems() throws Exception {
		// having
		// when
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(SATATISTICS_URI)).andReturn();
		// then
		assertEquals(HTTP_STATUS_ERROR, mvcResult.getResponse().getStatus());
		checkResultMessage(mvcResult.getResponse().getContentAsString(), NO_DATA_FOUND_ERR_MESSAGE);
	}

	@Test
	public void testRetrieveStatisticsForOneEelem() throws Exception {
		// having
		// when
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(SATATISTICS_URI + "/" + 1)).andReturn();
		// then
		assertEquals(HTTP_STATUS_ERROR, mvcResult.getResponse().getStatus());
		checkResultMessage(mvcResult.getResponse().getContentAsString(), NO_DATA_FOUND_ERR_MESSAGE);
	}

	private void checkResultMessage(String content, String errMsg) {
		ExplorationStatisticsResponse responseFromSpaceStation = gson.fromJson(content,
				ExplorationStatisticsResponse.class);
		assertNotNull(responseFromSpaceStation);
		assertEquals(responseFromSpaceStation.getDetailMessage(), errMsg);

	}
}
