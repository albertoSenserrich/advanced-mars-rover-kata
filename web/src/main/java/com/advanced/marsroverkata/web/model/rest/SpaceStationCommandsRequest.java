package com.advanced.marsroverkata.web.model.rest;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class SpaceStationCommandsRequest  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5626896565983299296L;

	private Plateau plateau;

	private List<Robot> robots = new LinkedList<Robot>();

	public Plateau getPlateau() {
		return plateau;
	}

	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

	public SpaceStationCommandsRequest withPlateau(Plateau plateau) {
		this.plateau = plateau;
		return this;
	}

	public List<Robot> getRobots() {
		return robots;
	}

	public void setRobots(List<Robot> robots) {
		this.robots = robots;
	}

	public SpaceStationCommandsRequest withRobots(List<Robot> robots) {
		this.robots = robots;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(SpaceStationCommandsRequest.class.getName()).append('@')
				.append(Integer.toHexString(System.identityHashCode(this))).append('[');
		sb.append("plateau");
		sb.append('=');
		sb.append(((this.plateau == null) ? "<null>" : this.plateau));
		sb.append(',');
		sb.append("robots");
		sb.append('=');
		sb.append(((this.robots == null) ? "<null>" : this.robots));
		sb.append(',');
		if (sb.charAt((sb.length() - 1)) == ',') {
			sb.setCharAt((sb.length() - 1), ']');
		} else {
			sb.append(']');
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = ((result * 31) + ((this.plateau == null) ? 0 : this.plateau.hashCode()));
		result = ((result * 31) + ((this.robots == null) ? 0 : this.robots.hashCode()));
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof SpaceStationCommandsRequest) == false) {
			return false;
		}
		SpaceStationCommandsRequest rhs = ((SpaceStationCommandsRequest) other);
		return (((this.plateau == rhs.plateau) || ((this.plateau != null) && this.plateau.equals(rhs.plateau)))
				&& ((this.robots == rhs.robots) || ((this.robots != null) && this.robots.equals(rhs.robots))));
	}

}
