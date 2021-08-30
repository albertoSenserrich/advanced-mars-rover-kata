package com.marsroverkata.controlstation.requestControlers;

import java.util.List;

import com.marsroverkata.controlstation.exceptions.ControlStationException;
import com.marsroverkata.controlstation.exceptions.ControlStationInputDataException;



/**
 * @author Alberto Senserrich Montals
 *
 */
public interface RequestControler {

	public void markRequestProcessed(String requestId)  throws ControlStationException;
	public void  returnAnswer(String requestId, List<String> responseData) throws ControlStationInputDataException;
	public List<String> readRequest(String requestId) throws ControlStationException;	
	public List<String> returnListOfRequestToProcess() ;
	
}
