package com.advanced.marsroverkata.web.model.rest;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


/**
 * @author Alberto Senserrich Montals
 *
 */
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


	public List<Robot> getRobots() {
		return robots;
	}

}
