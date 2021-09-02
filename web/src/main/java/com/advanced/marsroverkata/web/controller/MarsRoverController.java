package com.advanced.marsroverkata.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.advanced.marsroverkata.web.model.rest.Robot;
import com.advanced.marsroverkata.web.model.rest.SpaceStationCommandsRequest;
import com.advanced.marsroverkata.web.model.rest.SpaceStationCommandsResponse;
import com.advanced.marsroverkata.web.service.MarsRoverService;
//import com.advanced.marsroverkata.web.service.MarsRoverService;
//import com.advanced.marsroverkata.web.service.impl.MarsRoverServiceImpl;
import com.google.gson.Gson;
import com.marsroverkata.controlstation.constants.enums.ActionsType;
import com.marsroverkata.controlstation.constants.enums.OrientationType;

@RestController
public class MarsRoverController {

	Gson gson = new Gson();

	

	@Autowired
	MarsRoverService marsRoverServiceService;
	
    
	@PostMapping("/marsRovertRequest")
	ResponseEntity<?> newRequest(@RequestBody String requestContent) {
		SpaceStationCommandsResponse rsl;
		System.out.print(requestContent);
		SpaceStationCommandsRequest request = gson.fromJson(requestContent, SpaceStationCommandsRequest.class);
		String errorMsg = isValidRequest(request);
		if (errorMsg == null) {
			rsl = marsRoverServiceService.processMarsRoverOrders(request);
			if(rsl != null && rsl.getDetailMessage() == null) {
				return ResponseEntity.ok().body(rsl);
			}else {
				return ResponseEntity.internalServerError().body(rsl);
			}
			
		} else {
			rsl = new SpaceStationCommandsResponse(errorMsg);
			return ResponseEntity.badRequest().body(rsl);
		}
	}
	
	private String isValidRequest(SpaceStationCommandsRequest request) {
		String rsl = null;
		if (request == null)
			return "Invalid input data";

		// 2.0 Check plateau data
		if (request.getPlateau() == null) {
			rsl = "Invalid input data - Plateau is mandatory data";
		} else {
			if (request.getPlateau().getX() == null  || request.getPlateau().getY() == null) {
				rsl = "Invalid input data on Plateau - cannot accept null values for coordinates";
			}else if (request.getPlateau().getX() < 0 || request.getPlateau().getY() < 0) {
				rsl = "Invalid input data on Plateau - cannot accept negative values";
			} else if (request.getPlateau().getX() > 50 || request.getPlateau().getY() > 50) {
				rsl = "Invalid input data on Plateau - cannot accept values higher than 50";
			}
		}
		// 3.0 Check robot data
		if (rsl == null) {
			if (request.getRobots() != null && request.getRobots().size() > 0) {
				for (Robot robotElem : request.getRobots()) {
					if (robotElem.getPosition() != null) {
						if (robotElem.getPosition().getX() ==  null || robotElem.getPosition().getY() == null) {
							rsl = "Invalid input data on Robot - cannot accept null values for coordinates";
						}else if (robotElem.getPosition().getX() < 0 || robotElem.getPosition().getY() < 0) {
							rsl = "Invalid input data on Robot - cannot accept null values for coordinates";
						}else if (robotElem.getPosition().getX() < 0 || robotElem.getPosition().getY() < 0) {
							rsl = "Invalid input data on Robot - position cannot accept negative values";
						} else if (robotElem.getPosition().getX() > request.getPlateau().getX()
								|| robotElem.getPosition().getY() > request.getPlateau().getY()) {
							rsl = "Invalid input data on Robot - position cannot accept coordinates outside the plateau";
						} else if (OrientationType.getById(robotElem.getPosition().getOrientation()) == null) {
							rsl = "Invalid input data on Robot - position has an invalid orientation value";
						} else if (robotElem.getOrders() == null || robotElem.getOrders().trim().length() == 0) {
							rsl = "Invalid input data on Robot - orders are mandatory value";
						} else if (hasInValidOrdersValue(robotElem.getOrders())) {
							rsl = "Invalid input data on Robot - orders has an invalid value";
						}

					} else {
						rsl = "Invalid input data on Robot - Original position is a mandatory data";
					}
					if(rsl != null)
						break;
				}
			} else {
				rsl = "Invalid input data - Robots are mandatory data";
			}
		}
		// 1.0 Check robot data
		return rsl;
	}

	private boolean hasInValidOrdersValue(String orders) {
		for (char orderVal : orders.toCharArray()) {
			if (ActionsType.getById(orderVal) == null)
				return true;
		}
		return false;
	}

}
