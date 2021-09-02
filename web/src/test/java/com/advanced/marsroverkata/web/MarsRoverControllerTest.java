package com.advanced.marsroverkata.web;

import static org.junit.jupiter.api.Assertions.fail;

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

import com.advanced.marsroverkata.web.controller.MarsRoverController;
import com.advanced.marsroverkata.web.model.rest.Plateau;
import com.advanced.marsroverkata.web.model.rest.Robot;
import com.advanced.marsroverkata.web.model.rest.SpaceStationCommandsRequest;
import com.advanced.marsroverkata.web.model.rest.SpaceStationCommandsResponse;
import com.advanced.marsroverkata.web.service.MarsRoverCommandHistoryService;
import com.advanced.marsroverkata.web.service.MarsRoverService;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@WebMvcTest(MarsRoverController.class)
public class MarsRoverControllerTest {

	@Autowired
	protected MockMvc mvc;	
/*
 *    @MockBean
    private Manager manager;
    
	@Autowired
	MarsRoverService marsRoverServiceService;
	*/
   @MockBean
   MarsRoverService marsRoverServiceService;
   
   @MockBean
   MarsRoverCommandHistoryService marsRoverCommandHistoryService;
   
   
	Gson gson = new Gson();

	private final static String DEFAULT_URI = "/marsRovertRequest";
	
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

	
	
	@Test
	public void testBasicValidationsOnInputValuesMultiCase() throws Exception {
		// having
		for (int i = TEST_CASE_ERROR_NO_PLATEAU_DEFINED; i <= TEST_CASE_ERROR_INVALID_ROBOT_NULL_COORDINATE_Y; i++) {
			String bodyToSend = generateRequestData(i);
			// when
			MvcResult mvcResult = mvc
					.perform(MockMvcRequestBuilders.post(DEFAULT_URI).contentType(MediaType.APPLICATION_JSON_VALUE)
							.content(bodyToSend).accept(MediaType.APPLICATION_JSON_VALUE))
					.andReturn();
		}
	}

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
		// then
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
		default:
			fail("Unexpected test case with no result value defined" + requestDataType);
			break;
		}
		String bodyToSend = gson.toJson(body);
		return bodyToSend;
	}
}
