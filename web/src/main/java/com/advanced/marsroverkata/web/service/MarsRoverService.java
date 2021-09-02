package com.advanced.marsroverkata.web.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advanced.marsroverkata.web.model.GlobalCommandsExecutionData;
import com.advanced.marsroverkata.web.model.rest.ExplorationStatisticsResponse;
import com.advanced.marsroverkata.web.model.rest.Position;
import com.advanced.marsroverkata.web.model.rest.Robot;
import com.advanced.marsroverkata.web.model.rest.SpaceStationCommandsRequest;
import com.advanced.marsroverkata.web.model.rest.SpaceStationCommandsResponse;
import com.marsroverkata.controlstation.ControlStation;
import com.marsroverkata.controlstation.constants.enums.OrientationType;
import com.marsroverkata.controlstation.vehicles.LandRovert;
import com.marsroverkata.controlstation.vehicles.MarsVehicle;
import com.marsroverkata.controlstation.vehicles.components.ActualPosition;
import com.marsroverkata.controlstation.vehicles.components.Plateau;

@Service
public class MarsRoverService {


	@Autowired
	MarsRoverCommandHistoryService marsRoverComandHistoryService;
	
	public SpaceStationCommandsResponse processMarsRoverOrders(SpaceStationCommandsRequest request) {
		// 1.0 Init base data and convert in in order perform calculations
		List<MarsVehicle> vehicles = new LinkedList<MarsVehicle>();
		Plateau areaToExplore2 = new Plateau(request.getPlateau().getX(), request.getPlateau().getY());
		for (Robot robotElem : request.getRobots()) {
			ActualPosition position = new ActualPosition(robotElem.getPosition().getX(), robotElem.getPosition().getY(),
					OrientationType.getById(robotElem.getPosition().getOrientation()));
			LandRovert newVehicle = new LandRovert(position, areaToExplore2);
			newVehicle.setOrdersToExecute(robotElem.getOrders());
			vehicles.add(newVehicle);
		}
		// 2.0 Perform movements
		ControlStation.execute(vehicles);
		// 3.0 Format repsonse
		List<Position> rsl = new LinkedList<Position>();
		for (MarsVehicle vehicleElem : vehicles) {
			rsl.add(new Position(vehicleElem.getXPos(), vehicleElem.getYPos(),
					vehicleElem.getPosition().getOrigentation().getCode(), vehicleElem.getDataToReport()));
		}
		SpaceStationCommandsResponse response = new SpaceStationCommandsResponse(rsl); 
		saveDataIntoBBDD( request, response, vehicles.get(0).calculateTotalAreaExplored());
		return response;
	}

	public void clearCache() {
		marsRoverComandHistoryService.deleteAll();
	}

	public ExplorationStatisticsResponse getMetaDataFromAllRequest() {
		ExplorationStatisticsResponse response = new ExplorationStatisticsResponse();
		List<GlobalCommandsExecutionData> dataToReportList = marsRoverComandHistoryService.getAllGlobalCommandsExecutionData();
		if(dataToReportList != null && dataToReportList.size()>0) {
			long lostRobots = 0;
			long totalRobots = 0;
			long exploredSectors = 0;
			long totalAreaOfPlateau = 0;
			for(GlobalCommandsExecutionData  dataToReport :dataToReportList) {
				lostRobots += dataToReport.getLostRobots();
				totalRobots += dataToReport.getTotalRobots();
				exploredSectors += dataToReport.getExploredSectors();
				totalAreaOfPlateau += dataToReport.getX()*dataToReport.getY();		
			}
			response = new ExplorationStatisticsResponse(lostRobots, totalRobots, exploredSectors,  totalAreaOfPlateau);
		}else {
			response = new ExplorationStatisticsResponse("Not found data from previous executions");
		}
		return response;
	}
	
	public ExplorationStatisticsResponse getMetaDataFromOneRequest(Integer requestId) {
		ExplorationStatisticsResponse response = null;
		GlobalCommandsExecutionData dataToReport = marsRoverComandHistoryService.getGlobalCommandsExecutionDataById(requestId);
		if(dataToReport != null) {
			long lostRobots = dataToReport.getLostRobots();
			long totalRobots = dataToReport.getTotalRobots();
			long exploredSectors = dataToReport.getExploredSectors();
			long totalAreaOfPlateau = dataToReport.getX()*dataToReport.getY();		
			response = new ExplorationStatisticsResponse(lostRobots, totalRobots, exploredSectors,  totalAreaOfPlateau);
		}else {
			response = new ExplorationStatisticsResponse("Not found any execution with id: "+requestId);
		}
		return response;
	}
	

	private void saveDataIntoBBDD(SpaceStationCommandsRequest request, SpaceStationCommandsResponse response, Integer totalAreaExplored) {		
		if(response.getDetailMessage() == null || response.getDetailMessage().length() == 0) {
			//No error to report to user, data can be stored into bbdd
			GlobalCommandsExecutionData dataToSave = new GlobalCommandsExecutionData();
			dataToSave.setX(request.getPlateau().getX());
			dataToSave.setY(request.getPlateau().getY());
			//Calculate metadata
			int lostRobot = 0;
			for(Position pos : response.getFinalCoordinates()) {
				if(pos.getStatus()!=null)
					lostRobot++;
			}
			dataToSave.setLostRobots(lostRobot);
			dataToSave.setTotalRobots(response.getFinalCoordinates().size());
			if(totalAreaExplored != null) {
				dataToSave.setExploredSectors(totalAreaExplored);			
			}
			if(marsRoverComandHistoryService != null) {
				marsRoverComandHistoryService.saveOrUpdate(dataToSave);
				response.setId(dataToSave.getId());
			}
		}
	}
	
}
