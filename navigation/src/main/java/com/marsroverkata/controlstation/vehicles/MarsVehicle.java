package com.marsroverkata.controlstation.vehicles;

import com.marsroverkata.controlstation.exceptions.ControlStationException;
import com.marsroverkata.controlstation.vehicles.components.ActualPosition;
import com.marsroverkata.controlstation.vehicles.components.Plateau;


/**
 * @author Alberto Senserrich Montals
 *
 */
public abstract class MarsVehicle {
	
	protected ActualPosition position;
	protected String ordersToExecute;
//	protected boolean thereIsSomeNotification = false;
	protected String dataToReport = null;
	protected String detailMessageToReport = null;
	protected static Plateau areaToExplore;
	
	public boolean isThereIsSomeNotification() {
		return dataToReport != null;
	}

	public MarsVehicle(ActualPosition positon, Plateau areaToExplore2) {
		this.position = positon;
		MarsVehicle.areaToExplore = areaToExplore2;
	}
	
	public void setOrdersToExecute(String orderToExecuteI) {
		this.ordersToExecute = orderToExecuteI;
	}
	
	public ActualPosition getPosition() {
		return position;
	}

	public String getOrdersToExecute() {
		return ordersToExecute;
	}

	public abstract void performMovements() throws ControlStationException;
	
	protected abstract void turnRight();
	
	protected abstract void turnLeft();
	
	protected abstract void moveForward() throws ControlStationException;
	
	@Override
	public String toString(){
		String metaInformationOfLocation = "";
		if(this.dataToReport!= null) {
			metaInformationOfLocation = " "+this.dataToReport;
		}
		return position.getxPos()+" "+position.getyPos()+" " +position.getOrigentation().getCode()+metaInformationOfLocation;
	}

	
	public boolean isValid() {
		return position.getOrigentation()!= null && isCoordinateInsidePlateau(position.getxPos(),position.getyPos() );
	}
	
	public int getYPos() {
		return position.getyPos();
	}

	public int getXPos() {
		return position.getxPos();
	}

	public void markOcupiedSpace(int x, int y) {
		areaToExplore.markOcupiedSpace(x, y);
	}
	
	public void markLostRover(int x, int y) {
		areaToExplore.markLostRover(x, y);
	}
	
	public void markFreeSpace(int x, int y) {
		areaToExplore.markFreeSpace(x, y);
	}

	public boolean isRoverLostOnCoordenates(int x, int y) {
		return areaToExplore.isRoverLostOnCoordenates(x, y);
	}
	
	public boolean isCoordinateInsidePlateau(int xDestination, int yDestination) {
		return areaToExplore.isCoordinateInsidePlateau(xDestination, yDestination);
	}
	
	public String getDetailMessageToReport() {
		return detailMessageToReport;
	}

	public String getDataToReport() {
		return dataToReport;
	}

	public int calculateTotalAreaExplored() {
		int totalAreaExplored = 0;
		if(areaToExplore!=null) {
			totalAreaExplored = areaToExplore.calculateTotalAreaExplored();
		}
		return totalAreaExplored;
	}

}
