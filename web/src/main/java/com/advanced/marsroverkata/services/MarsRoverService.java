package com.advanced.marsroverkata.services;

import com.advanced.marsroverkata.dto.SpaceStationCommandsRequest;
import com.advanced.marsroverkata.dto.SpaceStationCommandsResponse;

public interface MarsRoverService {

	public SpaceStationCommandsResponse processMarsRoverOrders(SpaceStationCommandsRequest request);
	
}
