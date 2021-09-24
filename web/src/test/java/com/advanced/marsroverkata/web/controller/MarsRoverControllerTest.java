package com.advanced.marsroverkata.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.advanced.marsroverkata.web.model.rest.Plateau;
import com.advanced.marsroverkata.web.model.rest.Robot;
import com.advanced.marsroverkata.web.model.rest.SpaceStationCommandsRequest;
import com.advanced.marsroverkata.web.model.rest.SpaceStationCommandsResponse;
import com.advanced.marsroverkata.web.service.MarsRoverCommandHistoryService;
import com.advanced.marsroverkata.web.service.MarsRoverService;
import com.google.gson.Gson;

/**
 * @author Alberto Senserrich Montals
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MarsRoverController.class)
public class MarsRoverControllerTest {

	@Autowired
	protected MockMvc mvc;

	@MockBean
	MarsRoverService marsRoverServiceService;

	@MockBean
	MarsRoverCommandHistoryService marsRoverCommandHistoryService;

	Gson gson = new Gson();

	private final static String DEFAULT_URI = "/marsRoverRequest";

	private final static int TEST_CASE_ERROR_NO_PLATEAU_DEFINED = 1;
	private final static int TEST_CASE_ERROR_INVALID_PLATEAU_DEFINED_MIN_VAL_A = 2;
	private final static int TEST_CASE_ERROR_INVALID_PLATEAU_DEFINED_MIN_VAL_B = 3;
	private final static int TEST_CASE_ERROR_INVALID_PLATEAU_DEFINED_MAX_VAL_A = 4;
	private final static int TEST_CASE_ERROR_INVALID_PLATEAU_DEFINED_MAX_VAL_B = 5;
	private final static int TEST_CASE_ERROR_INVALID_PLATEAU_DEFINED_MULTI_ERROR = 6;
	private final static int TEST_CASE_ERROR_INVALID_PLATEAU_NULL_COORDINATE_X = 7;
	private final static int TEST_CASE_ERROR_INVALID_PLATEAU_NULL_COORDINATE_Y = 8;

	private final static int TEST_CASE_ERROR_INVALID_ROBOT_NULL_COORDINATE_Y = 9;
	private final static int TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_A = 10;
	private final static int TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_B = 11;
	private final static int TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_C = 12;
	private final static int TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_D = 13;
	private final static int TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_E = 14;
	private final static int TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_F = 15;
	private final static int TEST_CASE_ERROR_INVALID_ROBOT_INVALID_ORIENTATION = 16;
	private final static int TEST_CASE_ERROR_INVALID_ROBOT_INVALID_ORDERS = 17;
	private final static int TEST_CASE_ERROR_INVALID_ROBOT_NO_DATA = 18;
	private final static int TEST_CASE_ERROR_INVALID_ROBOT_NO_ORDER_DATA = 19;
	private final static int TEST_CASE_ERROR_INVALID_ROBOT_NULL_COORDINATE_X = 20;

	
	
	@Test
	public void testBasicValidationsOnInputValuesMonoCase() throws Exception {
	int testCase = TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_E;
		// having
		String bodyToSend = generateRequestData(testCase);
		// when
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.post(DEFAULT_URI).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(bodyToSend).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		// then
		validateErrorResponse(mvcResult, testCase);
	}
	
	@Test
	public void testBasicValidationsOnInputValuesMultiCase() throws Exception {
		for (int i = TEST_CASE_ERROR_NO_PLATEAU_DEFINED; i <= TEST_CASE_ERROR_INVALID_ROBOT_NULL_COORDINATE_X; i++) {
			// having
			String bodyToSend = generateRequestData(i);
			// when
			MvcResult mvcResult = mvc
					.perform(MockMvcRequestBuilders.post(DEFAULT_URI).contentType(MediaType.APPLICATION_JSON_VALUE)
							.content(bodyToSend).accept(MediaType.APPLICATION_JSON_VALUE))
					.andReturn();
			// then
			validateErrorResponse(mvcResult, i);
		}
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testSimpleHappyPath() throws Exception {
		// having
		SpaceStationCommandsRequest body = new SpaceStationCommandsRequest();
		body.setPlateau(new Plateau(5, 5));
		body.getRobots().add(new Robot(1, 2, "N", "LFLFLFLFF"));
		body.getRobots().add(new Robot(3, 3, "E", "FFRFFRFRRF"));
		String bodyToSend = gson.toJson(body);
		// when
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.post(DEFAULT_URI).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(bodyToSend).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		// then2
		String content = mvcResult.getResponse().getContentAsString();
		SpaceStationCommandsResponse responseFromSpaceStation = gson.fromJson(content,
				SpaceStationCommandsResponse.class);
	}

	private String generateRequestData(int requestDataType) {
		SpaceStationCommandsRequest body = new SpaceStationCommandsRequest();
		switch (requestDataType) {
		case TEST_CASE_ERROR_NO_PLATEAU_DEFINED:
			body.getRobots().add(new Robot(1, 2, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_PLATEAU_DEFINED_MIN_VAL_A:
			body.setPlateau(new Plateau(5, -5));
			body.getRobots().add(new Robot(1, 2, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_PLATEAU_DEFINED_MIN_VAL_B:
			body.setPlateau(new Plateau(-5, 5));
			body.getRobots().add(new Robot(1, 2, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_PLATEAU_DEFINED_MAX_VAL_A:
			body.setPlateau(new Plateau(500, 5));
			body.getRobots().add(new Robot(1, 2, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_PLATEAU_DEFINED_MAX_VAL_B:
			body.setPlateau(new Plateau(5, 500));
			body.getRobots().add(new Robot(1, 2, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_PLATEAU_DEFINED_MULTI_ERROR:
			body.setPlateau(new Plateau(-5, 500));
			body.getRobots().add(new Robot(1, 2, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_PLATEAU_NULL_COORDINATE_X:
			body.setPlateau(new Plateau(null, 5));
			body.getRobots().add(new Robot(1, 2, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_PLATEAU_NULL_COORDINATE_Y:
			body.setPlateau(new Plateau(5, null));
			body.getRobots().add(new Robot(1, 2, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_A:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(1, null, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_B:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(null, 2, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_C:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(1, 12, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_D:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(11, 2, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_E:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(-11, 2, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_F:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(11, -2, "N", "LFLFLFLFF"));
			break;			
		case TEST_CASE_ERROR_INVALID_ROBOT_INVALID_ORIENTATION:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(1, 2, "X", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_INVALID_ORDERS:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(1, 2, "N", "LFLXLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_NO_DATA:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(1, 2, "N", "LFLXLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_NO_ORDER_DATA:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(1, 2, "N", ""));
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_NULL_COORDINATE_Y:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(1, null, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_NULL_COORDINATE_X:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(null, 2, "N", "LFLFLFLFF"));
			break;
		default:
			fail("Unexpected test case with no result value defined" + requestDataType);
			break;
		}
		String bodyToSend = gson.toJson(body);
		return bodyToSend;
	}

	private void validateErrorResponse(MvcResult mvcResult, int requestDataType) throws UnsupportedEncodingException {
		// 1.0 Check response code and json entity
		int status = mvcResult.getResponse().getStatus();
		assertEquals("Invalid error code for test " + requestDataType, 400, status);
		String content = mvcResult.getResponse().getContentAsString();
		SpaceStationCommandsResponse responseFromSpaceStation = gson.fromJson(content,
				SpaceStationCommandsResponse.class);
		assertNotNull("Invalid json entity response for test " + requestDataType, responseFromSpaceStation);
		assertNotNull("Invalid response for test " + requestDataType + " we expected some detail message",
				responseFromSpaceStation.getDetailMessage());
		// 2.0 Check content of json entity
		String expectedErrorMessage = "";
		switch (requestDataType) {
		case TEST_CASE_ERROR_NO_PLATEAU_DEFINED:
			expectedErrorMessage = "Invalid input data - Plateau is mandatory data";
			break;
		case TEST_CASE_ERROR_INVALID_PLATEAU_DEFINED_MIN_VAL_A:
		case TEST_CASE_ERROR_INVALID_PLATEAU_DEFINED_MIN_VAL_B:
			expectedErrorMessage = "Invalid input data on Plateau - cannot accept negative values";
			break;
		case TEST_CASE_ERROR_INVALID_PLATEAU_DEFINED_MAX_VAL_A:
		case TEST_CASE_ERROR_INVALID_PLATEAU_DEFINED_MAX_VAL_B:
			expectedErrorMessage = "Invalid input data on Plateau - cannot accept values higher than 50";
			break;
		case TEST_CASE_ERROR_INVALID_PLATEAU_NULL_COORDINATE_Y:
		case TEST_CASE_ERROR_INVALID_PLATEAU_NULL_COORDINATE_X:
			expectedErrorMessage = "Invalid input data on Plateau - cannot accept null values for coordinates";
			break;
		case TEST_CASE_ERROR_INVALID_PLATEAU_DEFINED_MULTI_ERROR:
			expectedErrorMessage = "Invalid input data on Plateau - cannot accept negative values";
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_A:
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_B:// XX
			expectedErrorMessage = "Invalid input data on Robot - cannot accept null values for coordinates";
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_C:
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_D:
			expectedErrorMessage = "Invalid input data on Robot - position cannot accept coordinates outside the plateau";
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_E:
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_F:
			expectedErrorMessage = "Invalid input data on Robot - position cannot accept negative values";
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_INVALID_ORIENTATION:
			expectedErrorMessage = "Invalid input data on Robot - position has an invalid orientation value";
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_INVALID_ORDERS:
			expectedErrorMessage = "Invalid input data on Robot - orders has an invalid value";
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_NO_DATA:
			expectedErrorMessage = "Invalid input data on Robot - orders has an invalid value"; // XX
//			expectedErrorMessage = "Invalid input data - Robots are mandatory data";
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_NO_ORDER_DATA:
			expectedErrorMessage = "Invalid input data on Robot - orders are mandatory value";
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_NULL_COORDINATE_Y:
		case TEST_CASE_ERROR_INVALID_ROBOT_NULL_COORDINATE_X:
			expectedErrorMessage = "Invalid input data on Robot - cannot accept null values for coordinates";
			break;
		default:
			fail("Unexpected test case with no result value defined" + requestDataType);
			break;
		}
		assertTrue(
				"Unexpected error message on test case [" + requestDataType + "] we have message ["
						+ responseFromSpaceStation.getDetailMessage() + "] we expect [" + expectedErrorMessage + "]",
				expectedErrorMessage.trim().equals(responseFromSpaceStation.getDetailMessage().trim()));
	}
}
