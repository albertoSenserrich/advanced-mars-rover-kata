package com.marsroverkata.controlstation.requestControlers.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.marsroverkata.controlstation.constants.GlobalConstants;
import com.marsroverkata.controlstation.exceptions.ControlStationException;
import com.marsroverkata.controlstation.exceptions.ControlStationInputDataException;
import com.marsroverkata.controlstation.requestControlers.RequestControler;



/**
 * @author Alberto Senserrich Montals
 *
 */
public class FileRequestControlerTest {
	private final static String TEMP_TEST_BASE_PATH =System.getProperty("java.io.tmpdir")+File.separator+"testBasePath"+File.separator; //Path for test preparation

	private static final Path resourceDirectory = Paths.get("src","test","resources");	
	private static final String absolutePath = resourceDirectory.toFile().getAbsolutePath();
	private final static String TEMP_RESOURCE_TEST  =  absolutePath+File.separator+"inputRequestFolder" + File.separator;		
	
	RequestControler reqControler = new FileRequestControler(TEMP_TEST_BASE_PATH);
	
	private final static String VALID_PROCESS_ID_1 ="inputRequest1";
	private final static String VALID_PROCESS_ID_2 ="inputRequest2";
	private final static String VALID_PROCESS_ID_3 ="inputRequest3";
	private final static String INVALID_PROCESS_ID_1 ="inputRequest3Invalid";
	private final static String INVALID_PROCESS_EXTENSIOn_ID_1 ="inputRequest3.requestInvalidExtension";	
	
	private String VALID_PROCESS_ID_1_LINE_2 ="1 2 N";
	private String VALID_PROCESS_ID_1_LINE_1 ="5 5";
	
	@AfterEach
	public void cleanUpAfterTest(){
		File folder = new  File(TEMP_TEST_BASE_PATH); 
		try {
			FileUtils.cleanDirectory(folder);
		} catch (IOException e) {
			fail("Error during clean up["+e.getMessage()+"]");
		} 
	}
	
	@BeforeEach
	public void beforeAnyTest(){
		File folder = new  File(TEMP_TEST_BASE_PATH); 
		if(!folder.exists()){
			folder.mkdir();
		}
	}
		
	@Test
	public void markRequestProcessedValid() throws ControlStationException{
		//having
		copyResourceToTempFolder(VALID_PROCESS_ID_1+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST);
		//when
		reqControler.markRequestProcessed(VALID_PROCESS_ID_1);
		//then
		String path = TEMP_TEST_BASE_PATH + VALID_PROCESS_ID_1+"."+ GlobalConstants.FILE_EXTENSION_FOR_PROCESSED_REQUEST;
		File processedFile = new File(path);
		assertTrue(processedFile.exists(), "Valid files cannot be processd");
	}
	
	@Test
	public void markRequestProcessedInValid() throws ControlStationException{
		//having
		copyResourceToTempFolder(INVALID_PROCESS_EXTENSIOn_ID_1);
		//when
		//when
		assertThrows(ControlStationInputDataException.class, () -> {
			reqControler.markRequestProcessed(INVALID_PROCESS_ID_1);
		});
	}

	@Test
	public void  readINValidRequest() throws ControlStationException{
		//having
		copyResourceToTempFolder(INVALID_PROCESS_EXTENSIOn_ID_1);
		//when
		assertThrows(ControlStationInputDataException.class, () -> {
			reqControler.readRequest(INVALID_PROCESS_EXTENSIOn_ID_1);
		});
		//then
	}
	
	@Test
	public void readValidRequest() throws ControlStationException{
		//having
		copyResourceToTempFolder(VALID_PROCESS_ID_1+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST);
		//when
		List<String> result = reqControler.readRequest(VALID_PROCESS_ID_1);
		//then
		assertEquals(VALID_PROCESS_ID_1_LINE_1 ,result.get(0).trim());
		assertEquals(VALID_PROCESS_ID_1_LINE_2 , result.get(1).trim());		
	}
	
	
	@Test
	public void noFilesToProcess() {
		//having
		//when
		List<String> filesToProcess = reqControler.returnListOfRequestToProcess();
		//then
		assertTrue(filesToProcess.size() == 0);
	}
	
	@Test
	public void onlyProceesedFiles()  {
		//having
		copyResourceToTempFolder(INVALID_PROCESS_EXTENSIOn_ID_1);
		//when
		List<String> filesToProcess = reqControler.returnListOfRequestToProcess();
		//then
		assertTrue(filesToProcess.size() == 0);
	}
	
	@Test
	public void onlyOneFileToProcess() {
		copyResourceToTempFolder(VALID_PROCESS_ID_1+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST);
		List<String> filesToProcess = reqControler.returnListOfRequestToProcess();
		assertTrue(filesToProcess.size() == 1);
	}
	
	@Test
	public void multipleFilesToProcess()  {
		//having
		copyResourceToTempFolder(VALID_PROCESS_ID_1+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST);
		copyResourceToTempFolder(VALID_PROCESS_ID_2+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST);
		copyResourceToTempFolder(VALID_PROCESS_ID_3+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST);
		//when
		List<String> filesToProcess = reqControler.returnListOfRequestToProcess();
		//then
		assertTrue(filesToProcess.size() == 3);
	}	
	

	@Test
	public void processTwoTimesTheSameRequest() throws ControlStationException{
		//having
		copyResourceToTempFolder(VALID_PROCESS_ID_1+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST);
		reqControler.markRequestProcessed(VALID_PROCESS_ID_1);
		//when
		//then
		assertThrows(ControlStationInputDataException.class, () -> {
			reqControler.markRequestProcessed(VALID_PROCESS_ID_1);
		});
	}
	
	private void copyResourceToTempFolder(String id){
		String path =TEMP_RESOURCE_TEST+id;
		 try {
			FileUtils.copyFileToDirectory(new File(path), new File(TEMP_TEST_BASE_PATH));
		} catch (IOException e) {
			fail("Error during initialization up["+e.getMessage()+"]");
		}
	}	
}
