package com.marsroverkata.controlstation.vehicles;

import com.marsroverkata.controlstation.constants.enums.ActionsType;
import com.marsroverkata.controlstation.exceptions.ControlStationException;
import com.marsroverkata.controlstation.vehicles.components.ActualPosition;
import com.marsroverkata.controlstation.vehicles.components.Plateau;


/**
 * @author Alberto Senserrich Montals
 *
 */
public class LandRovert extends MarsVehicle{

	
	public LandRovert(ActualPosition positon,Plateau areaToExplore) {
		super(positon,areaToExplore);
	
	}

	@Override
	public void performMovements() throws ControlStationException {
		//1.0 init data
		String actionsToExecute = super.getOrdersToExecute();
		if(actionsToExecute!=null && actionsToExecute.length()>0){
			char[] orders = actionsToExecute.toCharArray();
			//2.0 perform actions
			for(char actualOrder: orders){
				if(super.dataToReport == null) {
//					System.out.println("Coordinates ["+this.getXPos()+"]["+this.getYPos()+"] orientation ["+this.getPosition().getOrigentation()+"] order ["+actualOrder+"]");
					ActionsType action = ActionsType.getById(actualOrder);
					if (action == null)
						continue;
					switch(action){
					case TURN_LEFT :
						turnLeft();
						break; 
					case TURN_RIGHT :
						turnRight();			 
						break;
					case MOVE_FORWARD :
						moveForward();				     
						break; 
					default :
						// Invalid action code,  we wont do nothing, 
						//TODO: add some error message
						break; 
					}	
				}else {
					//something has happened during execution and landRovert neds to report to space station some incident
					return;
				}
			}			
		}
	}

	@Override
	protected void turnRight() {
		super.getPosition().turnRight();
	}

	@Override
	protected void turnLeft() {
		super.getPosition().turnLeft();
	}

	@Override
	protected void moveForward() throws ControlStationException {
		//check if movement is valid
		if(checkNextMovement()) {
			//2.0 Update position and plateau data
			markFreeSpace(getXPos(),getYPos());
			super.getPosition().moveForward();
			markOcupiedSpace(getXPos(),getYPos());
		}
	}

	private boolean checkNextMovement() throws ControlStationException{
		//1.0 Init base data
		boolean isValid = true;
		int xDestination = super.getPosition().getOrigentation().getXposIncrement()+super.getPosition().getxPos();
		int yDestination = super.getPosition().getOrigentation().getYposIncrement()+super.getPosition().getyPos();
		//2.0 Check if the vehicle is going to exit the plateau (area to explore)
		if(!super.isCoordinateInsidePlateau(xDestination, yDestination)) {
			isValid = false;
			if(!super.isRovertLostOnCoordenates(xDestination, yDestination)){
				markLostRovert(xDestination, yDestination);
				super.dataToReport = "LOST";
				super.detailMessageToReport = "Abort movement, rovert is going to exit the plateau  keep actual positon ["+super.getPosition().getxPos()+"]["+super.getPosition().getyPos()+"]"; 
			}
		}		
		//3.0 Check collisions before movemen
		/* Feature not requiredon actual version
		if(super.isOcupedSpace(xDestination, yDestination)){
			super.dataToReport = "COLLISION";
			super.detailMessageToReport = "Abort movement. Collision alert to element on coordinates ["+xDestination+"]["+yDestination+"]";
			super.dataToReport = "Abort movement. Collision alert to element on coordinates ["+xDestination+"]["+yDestination+"]";		
			throw new ControlStationException (super.dataToReport, ErrorType.ERROR_COLLISION_ALERT_AVORTING_MOVEMENT);
		}*/
		return isValid;
	}
}
