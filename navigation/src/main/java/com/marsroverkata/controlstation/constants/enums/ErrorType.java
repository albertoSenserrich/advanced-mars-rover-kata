package com.marsroverkata.controlstation.constants.enums;


	/**
	 * @author Alberto Senserrich Montals
	 *
	 */
	public enum ErrorType {
		 ERROR_INVALID_INPUT_OUTPUT_PARAMETERS("001","Invalid/Output data"), 
		 ERROR_COLISION_ON_ROVER_MOVEMENT("002","Error during Rover movement"), 
		 ERROR_FATAL_ERROR_CREATING_CONTROLER("003","Error creating controler"), 
		 ERROR_OUT_OF_BOUNDARIES_ON_ROVER_MOVEMENT("004","Rover is going to exit the plateau, aborting movements"),
		 ERROR_COLLISION_ALERT_AVORTING_MOVEMENT("005","Red alert!!! Rover is going to crash against something. Aborting action!!!"), 
		 UNEXPECTED_SYSTEM_ERROR("999","Unexpected system error") ;
		 
		 private String code;
		 private String description;
		 
		 private ErrorType(String code, String description) {
			 this.code  =code;
			 this.description = description;
		 }
		 
		 public String getCode() {
		   return code;
		 }
		 public String getDescription(){
			 return this.description;
		 }
		 
	}
	
		 
