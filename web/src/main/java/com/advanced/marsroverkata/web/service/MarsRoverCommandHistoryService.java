package com.advanced.marsroverkata.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advanced.marsroverkata.web.model.GlobalCommandsExecutionData;
import com.advanced.marsroverkata.web.repository.MarsRoverCommandHistoryRepository;

/**
 * @author Alberto Senserrich Montals
 *
 */
@Service
public class MarsRoverCommandHistoryService {

	@Autowired
	MarsRoverCommandHistoryRepository repository;

	public void saveOrUpdate(GlobalCommandsExecutionData GlobalCommandsExecutionData) {
		repository.save(GlobalCommandsExecutionData);
	}

	public List<GlobalCommandsExecutionData> getAllGlobalCommandsExecutionData() {
		List<GlobalCommandsExecutionData> GlobalCommandsExecutionDatas = new ArrayList<GlobalCommandsExecutionData>();
		repository.findAll()
				.forEach(GlobalCommandsExecutionData -> GlobalCommandsExecutionDatas.add(GlobalCommandsExecutionData));
		return GlobalCommandsExecutionDatas;
	}

	public GlobalCommandsExecutionData getGlobalCommandsExecutionDataById(long id) {
		GlobalCommandsExecutionData resp = null;
		try {
			resp = repository.findById(id).get(); 
		}catch(java.util.NoSuchElementException e) {
			//No data to return
		}
		return resp;
	}
	
	public void deleteAll() {
		repository.deleteAll();
	}
	
}
