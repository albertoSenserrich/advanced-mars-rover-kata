package com.marsroverkata.controlstation;

import java.util.List;


import org.apache.log4j.Logger;

import com.marsroverkata.controlstation.constants.enums.BaseExecutionType;
import com.marsroverkata.controlstation.factory.ControlStationFactory;
import com.marsroverkata.controlstation.vehicles.MarsVehicle;

import controlStation.executors.ControlStationExecutor;


/**
 * @author Alberto Senserrich Montals
 *
 */
public class ControlStation {
	
	private final static Logger logger = Logger.getLogger(ControlStation.class);

	public static void main(String[] args) {
       //1.0 Instanciate type of base   
       ControlStationExecutor mrbase;      
       if(args!= null && args.length>0) {
    	   logger.debug( "Initiating space station with files on path:"+args[0]);
    	   mrbase = ControlStationFactory.getBase(BaseExecutionType.LOAD_DATA_FROM_FILESYSTEM, args[0]);
       }else {
    	   mrbase = ControlStationFactory.getBase(BaseExecutionType.LOAD_DATA_FROM_FILESYSTEM, null);
       }
       logger.debug("Space Station is on-line");
       //2.0 Perform base action of base (process orders)
       mrbase.performActions();      
       logger.debug("Space Station is shuting down, end execution of request");
	}

	public static void execute() {
	       logger.debug("Space Station is on-line");
	       //1.0 Instanciate type of base   
	       ControlStationExecutor mrbase = ControlStationFactory.getBase(BaseExecutionType.LOAD_DATA_FROM_FILESYSTEM, null);      
	       //2.0 Perform base action of base (process orders)
	       mrbase.performActions();      
	       logger.debug("Space Station is shuting down, end execution of request");		
	}
	
	public static void execute(List<MarsVehicle> vehicles) {
	       logger.debug("Space Station is on-line");
	       //1.0 Instanciate type of base   
	       ControlStationExecutor mrbase = ControlStationFactory.getBase(BaseExecutionType.LOAD_DATA_AS_METHOD_PARAMS, null);
	       //2.0 Perform base action of base (process orders)
	       mrbase.performActions(vehicles);      
	       logger.debug("Space Station is shuting down, end execution of request");		
	}
}
