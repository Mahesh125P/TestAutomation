package com.testautomation.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.testautomation.model.ComponentMapping;
import com.testautomation.model.Screen;

@Repository
public interface ComponentMappingRepository extends JpaRepository<ComponentMapping, Integer>{

	@Query("select c from ComponentMapping c where c.testComponent.testComponentID= :componentId ORDER BY c.testOrder")
	List<ComponentMapping> findAllByComponentId(@Param("componentId") Integer componentId);
	
	@Query("select distinct screen.screenName from ComponentMapping c where c.testComponent.testComponentID= :componentId ORDER BY c.testOrder")
	List<String> findScreenNameByComponentId(@Param("componentId") Integer selectedComponentID);
}
