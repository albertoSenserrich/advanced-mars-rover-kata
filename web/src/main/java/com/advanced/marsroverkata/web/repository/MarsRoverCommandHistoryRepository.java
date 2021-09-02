package com.advanced.marsroverkata.web.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.advanced.marsroverkata.web.model.GlobalCommandsExecutionData;

	@Repository
	 public interface MarsRoverCommandHistoryRepository extends CrudRepository<GlobalCommandsExecutionData, Long> {}

