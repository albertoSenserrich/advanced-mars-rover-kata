package com.advanced.marsroverkata.web.model.rest;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(detailMessage, finalCoordinates, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpaceStationCommandsResponse other = (SpaceStationCommandsResponse) obj;
		return Objects.equals(detailMessage, other.detailMessage)
				&& Objects.equals(finalCoordinates, other.finalCoordinates) && Objects.equals(id, other.id);
	}
	
}