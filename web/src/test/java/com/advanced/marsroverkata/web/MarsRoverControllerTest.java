package com.advanced.marsroverkata.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.advanced.marsroverkata.dto.Plateau;
import com.advanced.marsroverkata.dto.Position;
import com.advanced.marsroverkata.dto.Robot;
import com.advanced.marsroverkata.dto.SpaceStationCommandsRequest;
import com.advanced.marsroverkata.dto.SpaceStationCommandsResponse;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@WebMvcTest(MarsRoverController.class)
public class MarsRoverControllerTest {

	@Autowired
	protected MockMvc mvc;

	@Before
	protected void setUp() {
	}

	Gson gson = new Gson();

	private final static String DEFAULT_URI = "/marsRovertRequest";

	private final static int TEST_CASE_OK_1 = 100;

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
	private final static int TEST_CASE_ERROR_INVALID_ROBOT_INVALID_ORIENTATION = 14;
	private final static int TEST_CASE_ERROR_INVALID_ROBOT_INVALID_ORDERS = 15;
	private final static int TEST_CASE_ERROR_INVALID_ROBOT_NO_DATA = 16;
	private final static int TEST_CASE_ERROR_INVALID_ROBOT_NO_ORDER_DATA = 17;
	private final static int TEST_CASE_ERROR_INVALID_ROBOT_NULL_COORDINATE_X = 18;

//	@Test
	public void testBasicValidationsOnInputValuesMultiCase() throws Exception {
		// having
		for (int i = TEST_CASE_ERROR_NO_PLATEAU_DEFINED; i <= TEST_CASE_ERROR_INVALID_ROBOT_NULL_COORDINATE_Y; i++) {
			System.out.print("Executing test case " + i);
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

	@Test
	public void testHappyPath() throws Exception {
		// having
		String bodyToSend = generateRequestData(TEST_CASE_OK_1);
		// when
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.post(DEFAULT_URI).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(bodyToSend).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		// then
		validateOkResponse(mvcResult, TEST_CASE_OK_1);
	}

	@Test
	public void testMultipleExamplesFromFileSystem() throws Exception {
		// having
		String bodyToSend = generateRequestData(TEST_CASE_OK_1);
		// when
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.post(DEFAULT_URI).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(bodyToSend).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		// then
		validateOkResponse(mvcResult, TEST_CASE_OK_1);
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
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_C:
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_D:
			expectedErrorMessage = "Invalid input data on Robot - position cannot accept coordinates outside the plateau";
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_INVALID_ORIENTATION:
			expectedErrorMessage = "Invalid input data on Robot - position has an invalid orientation value";
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_INVALID_ORDERS:
			expectedErrorMessage = "Invalid input data on Robot - orders has an invalid value";
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_NO_DATA:
			expectedErrorMessage = "Invalid input data - Robots are mandatory data";
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_NO_ORDER_DATA:
			expectedErrorMessage = "Invalid input data on Robot - orders has an invalid value";
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
			body.getRobots().add(new Robot(-1, 2, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_B:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(1, -2, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_C:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(1, 12, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_ERROR_INVALID_ROBOT_OUTSIDE_PLATEAU_D:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(11, 2, "N", "LFLFLFLFF"));
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
		case TEST_CASE_OK_1:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(1, 2, "N", "LFLFLFLFF"));
			body.getRobots().add(new Robot(3, 3, "E", "FFRFFRFRRF"));
			break;
		default:
			fail("Unexpected test case with no result value defined" + requestDataType);
			break;
		}
		String bodyToSend = gson.toJson(body);
		return bodyToSend;
	}

	private void validateOkResponse(MvcResult mvcResult, int requestDataType) throws UnsupportedEncodingException {
		// 1.0 Check response code and json entity
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		SpaceStationCommandsResponse responseFromSpaceStation = gson.fromJson(content,
				SpaceStationCommandsResponse.class);
		assertNotNull(responseFromSpaceStation);
		assertNotNull(responseFromSpaceStation.getFinalCoordinates());
		assertTrue("There must be some final coordinates on response",responseFromSpaceStation.getFinalCoordinates().size()>0);
		assertNull("No detail error message can be included", responseFromSpaceStation.getDetailMessage());
		// 2.0 Check content of json entity
		switch (requestDataType) {
		case TEST_CASE_OK_1:
			validateResultString(requestDataType, 1, new Position(1, 3, "N"), responseFromSpaceStation.getFinalCoordinates().get(0));
			validateResultString(requestDataType, 2, new Position(5, 1, "E"), responseFromSpaceStation.getFinalCoordinates().get(1));

			break;
		default:
			fail("Unexpected test case with no result value defined" + requestDataType);
			break;
		}
	}

	private void validateResultString(int testCase, int robot, Position posA, Position posB) {
		String a = posA.toString();
		String b = posB.toString();
		assertTrue("Invalid response code for testCase " + testCase + " and robot [" + robot + "] we expect result ["
				+ a + "] but we obtain response [" + b + "]", posA.equals(posB));
	}
}
