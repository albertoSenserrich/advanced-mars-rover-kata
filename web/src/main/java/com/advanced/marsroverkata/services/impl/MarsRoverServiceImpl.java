package com.advanced.marsroverkata.services.impl;

import java.util.LinkedList;
import java.util.List;

import com.advanced.marsroverkata.dto.Position;
import com.advanced.marsroverkata.dto.Robot;
import com.advanced.marsroverkata.dto.SpaceStationCommandsRequest;
import com.advanced.marsroverkata.dto.SpaceStationCommandsResponse;
import com.advanced.marsroverkata.services.MarsRoverService;
import com.marsroverkata.controlstation.ControlStation;
import com.marsroverkata.controlstation.constants.enums.OrientationType;
import com.marsroverkata.controlstation.vehicles.LandRovert;
import com.marsroverkata.controlstation.vehicles.MarsVehicle;
import com.marsroverkata.controlstation.vehicles.components.ActualPosition;
import com.marsroverkata.controlstation.vehicles.components.Plateau;


public class MarsRoverServiceImpl implements MarsRoverService {

	@Override
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
		return new SpaceStationCommandsResponse(rsl);
	}

}
