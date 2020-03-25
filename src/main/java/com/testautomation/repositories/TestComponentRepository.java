package com.testautomation.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.testautomation.model.TestComponent;
import com.testautomation.service.LookupDTO;

@Repository
public interface TestComponentRepository extends JpaRepository<TestComponent, Integer> {

	@Query("select new com.testautomation.service.LookupDTO(testComponentID,componentName) from TestComponent t where t.application=:appId")
	public ArrayList<LookupDTO> findTestComponentByAppId(@Param("appId") Integer appId);

}
