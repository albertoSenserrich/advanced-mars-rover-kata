package com.advanced.marsroverkata.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.advanced.marsroverkata.web.model.rest.ExplorationStatisticsResponse;
import com.advanced.marsroverkata.web.service.MarsRoverService;


/**
 * @author Alberto Senserrich Montals
 *
 */
@RestController
public class AdminController {

	@Autowired
	MarsRoverService marsRoverService;

	@GetMapping("/adminService/healthCheck")
	ResponseEntity<?> healthCheck() {
		return ResponseEntity.ok("Space station on-line");
	}

	@DeleteMapping("/adminService/clearCache")
	void clearCache() {
		marsRoverService.clearCache();
	}
	
	@GetMapping("/adminService/statistics")
	ResponseEntity<?> getMetaDataFromAllRequest() {
		ExplorationStatisticsResponse response = marsRoverService.getMetaDataFromAllRequest();
		if(response == null) {
			 response = new ExplorationStatisticsResponse("No data aviable");	
		}
		if(response.getDetailMessage() == null) {
			return ResponseEntity.ok().body(response);
		}else {
			return ResponseEntity.badRequest().body(response); 
		}
	}
	
	@GetMapping("/adminService/statistics/{id}")
	ResponseEntity<?> getMetaDataFromOneRequest(@PathVariable Integer id) {
		ExplorationStatisticsResponse response = marsRoverService.getMetaDataFromOneRequest(id);
		if(response == null) {
			 response = new ExplorationStatisticsResponse("No data aviable");	
		}
		if(response.getDetailMessage() == null) {
			return ResponseEntity.ok().body(response);
		}else {
			return ResponseEntity.badRequest().body(response); 
		}		
	}
	
}
