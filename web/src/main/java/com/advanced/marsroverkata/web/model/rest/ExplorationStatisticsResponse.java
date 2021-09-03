package com.advanced.marsroverkata.web.model.rest;

import java.io.Serializable;

/**
 * @author Alberto Senserrich Montals
 *
 */
public class ExplorationStatisticsResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1403793902149633086L;

	Long lostRobots;
	Long totalRobots;
	Long exploredSectors;
	Long totalAreaOfPlateau;
	private String detailMessage;
	
	
	public String getDetailMessage() {
		return detailMessage;
	}


	public Long getLostRobots() {
		return lostRobots;
	}

	public Long getTotalRobots() {
		return totalRobots;
	}

	public Long getExploredSectors() {
		return exploredSectors;
	}

	public Long getTotalAreaOfPlateau() {
		return totalAreaOfPlateau;
	}

	public ExplorationStatisticsResponse(String detailMessage) {
		this.detailMessage = detailMessage;
	}
			
	public ExplorationStatisticsResponse(Long lostRobots, Long totalRobots, Long exploredSectors,
			Long totalAreaOfPlateau) {
		super();
		this.lostRobots = lostRobots;
		this.totalRobots = totalRobots;
		this.exploredSectors = exploredSectors;
		this.totalAreaOfPlateau = totalAreaOfPlateau;
	}

	public ExplorationStatisticsResponse() {
		super();
	}
}
