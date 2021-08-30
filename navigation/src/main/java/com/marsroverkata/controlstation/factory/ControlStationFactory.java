package com.marsroverkata.controlstation.factory;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.marsroverkata.controlstation.constants.GlobalConstants;
import com.marsroverkata.controlstation.constants.enums.BaseExecutionType;

import controlStation.executors.ControlStationExecutor;
import controlStation.executors.impl.MarsControlStationExecutor;

/**
 * @author Alberto Senserrich Montals
 *
 */
public class ControlStationFactory {

	
    /**
     * Get a base Instance by type
     * @param input Input string with the coordinates of the right north border
     * of area to explore, and all orders to do by the rovers
     * @param base Type of base to be created
     * @return MarsBase
     */
    public static ControlStationExecutor getBase( BaseExecutionType base, String paramPath) {
    	//1.0 init basic values
    	
    	ControlStationExecutor defaultBaseExecutor  = null;
        switch (base) {
            case LOAD_DATA_FROM_FILESYSTEM:
            	String path = paramPath!= null? paramPath :GlobalConstants.DEFAULT_REQUEST_INPUT_FILE_PATH;
            	Path pathN = Paths.get(path);
                pathN.normalize();
            	defaultBaseExecutor = new MarsControlStationExecutor(pathN.toString());
                break;
            case LOAD_DATA_AS_METHOD_PARAMS:
            	defaultBaseExecutor = new MarsControlStationExecutor();
                break;                
            default:
            	
                break;
        }
        return defaultBaseExecutor;
    }
    
}
