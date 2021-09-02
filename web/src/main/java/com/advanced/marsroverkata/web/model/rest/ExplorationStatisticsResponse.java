package com.advanced.marsroverkata.web.model.rest;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;

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

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}

	public Long getLostRobots() {
		return lostRobots;
	}

	public void setLostRobots(Long lostRobots) {
		this.lostRobots = lostRobots;
	}

	public Long getTotalRobots() {
		return totalRobots;
	}

	public void setTotalRobots(Long totalRobots) {
		this.totalRobots = totalRobots;
	}

	public Long getExploredSectors() {
		return exploredSectors;
	}

	public void setExploredSectors(Long exploredSectors) {
		this.exploredSectors = exploredSectors;
	}

	public Long getTotalAreaOfPlateau() {
		return totalAreaOfPlateau;
	}

	public void setTotalAreaOfPlateau(Long totalAreaOfPlateau) {
		this.totalAreaOfPlateau = totalAreaOfPlateau;
	}

	@Override
	public int hashCode() {
		return Objects.hash(exploredSectors, lostRobots, totalAreaOfPlateau, totalRobots);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExplorationStatisticsResponse other = (ExplorationStatisticsResponse) obj;
		return Objects.equals(exploredSectors, other.exploredSectors) && Objects.equals(lostRobots, other.lostRobots)
				&& Objects.equals(totalAreaOfPlateau, other.totalAreaOfPlateau)
				&& Objects.equals(totalRobots, other.totalRobots);
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
