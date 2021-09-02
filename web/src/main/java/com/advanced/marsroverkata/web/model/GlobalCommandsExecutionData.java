package com.advanced.marsroverkata.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="MARS_GLOBAL_COMMAND")
@Table
public class GlobalCommandsExecutionData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	Long id;
	@Column(name = "PLATEAU_SIZE_X")
	Integer x;
	@Column(name = "PLATEAU_SIZE_Y")
	Integer y;

	@Column(name = "LOST_ROBOTS")
	Integer lostRobots;
	
	@Column(name = "TOTAL_ROBOTS")
	Integer totalRobots;
	
	@Column(name = "EXPLORED_SECTORS")
	Integer exploredSectors;
	
	
	public Long getId() {
		return id;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getLostRobots() {
		return lostRobots;
	}

	public void setLostRobots(Integer lostRobots) {
		this.lostRobots = lostRobots;
	}

	public Integer getTotalRobots() {
		return totalRobots;
	}

	public void setTotalRobots(Integer totalRobots) {
		this.totalRobots = totalRobots;
	}

	public Integer getExploredSectors() {
		return exploredSectors;
	}

	public void setExploredSectors(Integer exploredSectors) {
		this.exploredSectors = exploredSectors;
	}

}
