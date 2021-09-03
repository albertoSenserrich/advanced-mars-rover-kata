package com.advanced.marsroverkata.web.model.rest;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Alberto Senserrich Montals
 *
 */
public class SpaceStationCommandsResponse  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1403793902149633086L;
	private List<Position> finalCoordinates = new LinkedList<Position>();
	String detailMessage;
	Long id;
	
	
	public List<Position> getFinalCoordinates() {
		return finalCoordinates;
	}
	
	public String getDetailMessage() {
		return detailMessage;
	}

	public SpaceStationCommandsResponse(String detailMessage) {
		super();
		this.detailMessage = detailMessage;
	}
	public SpaceStationCommandsResponse(List<Position> robots) {
		super();
		this.finalCoordinates = robots;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
