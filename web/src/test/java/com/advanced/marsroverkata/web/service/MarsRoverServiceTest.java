package com.advanced.marsroverkata.web.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.advanced.marsroverkata.web.AdvancedMarsRoverApplication;
import com.advanced.marsroverkata.web.model.GlobalCommandsExecutionData;
import com.advanced.marsroverkata.web.model.rest.ExplorationStatisticsResponse;
import com.advanced.marsroverkata.web.model.rest.Plateau;
import com.advanced.marsroverkata.web.model.rest.Position;
import com.advanced.marsroverkata.web.model.rest.Robot;
import com.advanced.marsroverkata.web.model.rest.SpaceStationCommandsRequest;
import com.advanced.marsroverkata.web.model.rest.SpaceStationCommandsResponse;

/**
 * @author Alberto Senserrich Montals
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdvancedMarsRoverApplication.class)
public class MarsRoverServiceTest {

	@Autowired
	MarsRoverCommandHistoryService marsRoverComandHistoryService;

	@Autowired
	MarsRoverService marsRoverServiceService;

	private final static int TEST_CASE_OK_1 = 101;
	private final static int TEST_CASE_OK_2 = 102;
	private final static int TEST_CASE_OK_3 = 103;
	private final static int TEST_CASE_OK_4 = 104;
	private final static int TEST_CASE_OK_5 = 105;
	private final static int TEST_CASE_OK_6 = 106;
	private final static int TEST_CASE_OK_7 = 107;


	@Test
	public void testClearCache() throws Exception {
		// having
		// when
		marsRoverServiceService.clearCache();
		// then
	}

	private final static Integer DEFAULT_ID = 1;
	private final static Integer INVALID_ID = -1;

	@Test
	public void testRetrieveStatisticsFromInvalidElem() throws Exception {
		// having
		// when
		ExplorationStatisticsResponse rsl = marsRoverServiceService.getMetaDataFromOneRequest(INVALID_ID);
		// then
		assertNotNull(rsl);
		assertEquals(rsl.getDetailMessage(), "Not found any execution with id: " + INVALID_ID);
	}

	@Test
	public void testRetrieveStatisticsFromOneElem() throws Exception {
		//having
		GlobalCommandsExecutionData dataElem = generateRslElem();
		marsRoverComandHistoryService.saveOrUpdate(dataElem);
		// when
		ExplorationStatisticsResponse rsl = marsRoverServiceService.getMetaDataFromOneRequest(DEFAULT_ID);
		// then
		assertNotNull(rsl);
		assertNull(rsl.getDetailMessage());		
		assertEquals(rsl.getExploredSectors(), new Long(dataElem.getExploredSectors()));
		assertEquals(rsl.getLostRobots(), new Long(dataElem.getLostRobots()));
		assertEquals(rsl.getTotalAreaOfPlateau(), new Long((dataElem.getX()*dataElem.getY())));
		assertEquals(rsl.getTotalRobots(), new Long(dataElem.getTotalRobots())); 		
	}

	@Test
	public void testRetrieveStatisticsFromMultipleElems() throws Exception {
		//having
		marsRoverServiceService.clearCache();
		List<GlobalCommandsExecutionData> dataElemsList = generateListOfResults(5);
		for(GlobalCommandsExecutionData dataElem : dataElemsList) {
			marsRoverComandHistoryService.saveOrUpdate(dataElem);
		}
		// when
		ExplorationStatisticsResponse rsl = marsRoverServiceService.getMetaDataFromAllRequest();
		// then
		assertNotNull(rsl);
		long exploredSectors = 0;
		long lostRobots = 0;
		long totalAreaOfPlateau= 0;
		long totalRobots = 0;
		for(GlobalCommandsExecutionData dataElem : dataElemsList) {
			exploredSectors += dataElem.getExploredSectors();
			lostRobots += dataElem.getLostRobots();
			totalAreaOfPlateau += (dataElem.getX()*dataElem.getY());
			totalRobots += dataElem.getTotalRobots();
		}
		assertNotNull(rsl);
		assertNull(rsl.getDetailMessage());		
		assertEquals(rsl.getExploredSectors().longValue(), exploredSectors);
		assertEquals(rsl.getLostRobots().longValue(), lostRobots);
		assertEquals(rsl.getTotalAreaOfPlateau().longValue(), totalAreaOfPlateau);
		assertEquals(rsl.getTotalRobots().longValue(), totalRobots);	
	}

	
	private GlobalCommandsExecutionData generateRslElem() {
		return generateListOfResults(1).get(0);
	}

	private List<GlobalCommandsExecutionData> generateListOfResults(int numberOfElems) {
		List<GlobalCommandsExecutionData> rsl = new LinkedList<GlobalCommandsExecutionData>();
		for (int i = 0; i < numberOfElems; i++) {
			GlobalCommandsExecutionData tmpElem = new GlobalCommandsExecutionData();
			tmpElem.setId((long) i);
			tmpElem.setLostRobots(2);
			tmpElem.setTotalRobots(3);
			tmpElem.setX(5);
			tmpElem.setY(5);
			tmpElem.setExploredSectors(15);
			rsl.add(tmpElem);
		}
		return rsl;
	}
	
	@Test
	public void testSimpleHappyPath() throws Exception {
		// having
		SpaceStationCommandsRequest body = new SpaceStationCommandsRequest();
		body.setPlateau(new Plateau(5, 5));
		body.getRobots().add(new Robot(1, 2, "N", "LFLFLFLFF"));
		body.getRobots().add(new Robot(3, 3, "E", "FFRFFRFRRF"));
		// when
		SpaceStationCommandsResponse responseFromSpaceStation = marsRoverServiceService.processMarsRoverOrders(body);
		// then
		validateResultString(-1, 1, new Position(1, 3, "N"), responseFromSpaceStation.getFinalCoordinates().get(0));
		validateResultString(-1, 2, new Position(5, 1, "E"), responseFromSpaceStation.getFinalCoordinates().get(1));
	}

	@Test
	public void testMultipleExamplesFromFileSystem() throws Exception {
		// having
		for (int i = TEST_CASE_OK_1; i <= TEST_CASE_OK_7; i++) {
			SpaceStationCommandsRequest body = generateRequestData(i);
			// when
			SpaceStationCommandsResponse responseFromSpaceStation = marsRoverServiceService
					.processMarsRoverOrders(body);
			// then
			validateOkResponse(responseFromSpaceStation, i);
		}
	}

	private SpaceStationCommandsRequest generateRequestData(int requestDataType) {
		SpaceStationCommandsRequest body = new SpaceStationCommandsRequest();
		switch (requestDataType) {
		case TEST_CASE_OK_1:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(1, 2, "N", "LFLFLFLFF"));
			body.getRobots().add(new Robot(3, 3, "E", "FFRFFRFRRF"));
			break;
		case TEST_CASE_OK_2:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(3, 3, "E", "FFRFFRFRRF"));
			break;
		case TEST_CASE_OK_3:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(1, 2, "N", "LFLFLFLFF"));
			break;
		case TEST_CASE_OK_4:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(1, 2, "N", "LRLRLRLRR"));
			body.getRobots().add(new Robot(3, 3, "E", "RRRRLLLLRRRRLLLL"));
			break;
		case TEST_CASE_OK_5:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(0, 0, "N", "FFFFFFFFFFFFFF"));
			body.getRobots().add(new Robot(0, 0, "E", "FFFFFFFFFFFFFF"));
			break;
		case TEST_CASE_OK_6:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(0, 0, "N", "FFFFFFFFFFFFFF"));
			body.getRobots().add(new Robot(0, 4, "E", "FFFFFFFFFFFFFF"));
			break;
		case TEST_CASE_OK_7:
			body.setPlateau(new Plateau(5, 5));
			body.getRobots().add(new Robot(1, 1, "N", "FLLFL"));
			break;
		default:
			fail("Unexpected test case with no result value defined" + requestDataType);
			break;
		}

		return body;
	}

	private void validateOkResponse(SpaceStationCommandsResponse responseFromSpaceStation, int requestDataType)
			throws UnsupportedEncodingException {
		// 1.0 Check response code and json entity
		assertNotNull(responseFromSpaceStation);
		assertNotNull(responseFromSpaceStation);
		assertNotNull(responseFromSpaceStation.getFinalCoordinates());
		assertTrue("There must be some final coordinates on response",
				responseFromSpaceStation.getFinalCoordinates().size() > 0);
		assertNull("No detail error message can be included", responseFromSpaceStation.getDetailMessage());
		// 2.0 Check content of json entity
		switch (requestDataType) {
		case TEST_CASE_OK_1:
			validateResultString(requestDataType, 1, new Position(1, 3, "N"),
					responseFromSpaceStation.getFinalCoordinates().get(0));
			validateResultString(requestDataType, 2, new Position(5, 1, "E"),
					responseFromSpaceStation.getFinalCoordinates().get(1));
			break;
		case TEST_CASE_OK_2:
			validateResultString(requestDataType, 2, new Position(5, 1, "E"),
					responseFromSpaceStation.getFinalCoordinates().get(0));
			break;
		case TEST_CASE_OK_3:
			validateResultString(requestDataType, 1, new Position(1, 3, "N"),
					responseFromSpaceStation.getFinalCoordinates().get(0));
			break;
		case TEST_CASE_OK_4:
			validateResultString(requestDataType, 1, new Position(1, 2, "E"),
					responseFromSpaceStation.getFinalCoordinates().get(0));
			validateResultString(requestDataType, 2, new Position(3, 3, "E"),
					responseFromSpaceStation.getFinalCoordinates().get(1));
			break;
		case TEST_CASE_OK_5:
			validateResultString(requestDataType, 1, new Position(0, 5, "N", "LOST"),
					responseFromSpaceStation.getFinalCoordinates().get(0));
			validateResultString(requestDataType, 2, new Position(5, 0, "E", "LOST"),
					responseFromSpaceStation.getFinalCoordinates().get(1));
			break;
		case TEST_CASE_OK_6:
			validateResultString(requestDataType, 1, new Position(0, 5, "N", "LOST"),
					responseFromSpaceStation.getFinalCoordinates().get(0));
			validateResultString(requestDataType, 2, new Position(5, 4, "E", "LOST"),
					responseFromSpaceStation.getFinalCoordinates().get(1));
			break;
		case TEST_CASE_OK_7:
			validateResultString(requestDataType, 2, new Position(1, 1, "E"),
					responseFromSpaceStation.getFinalCoordinates().get(0));
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
