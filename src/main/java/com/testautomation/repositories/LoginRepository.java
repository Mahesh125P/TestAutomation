package com.testautomation.repositories;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import com.testautomation.model.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, String>{

	@Query("select applicationName from Application")
	public ArrayList<String> getAllApplicationNames();

	@Query("select screenName from Screen where application.applicationID = 1")
	public ArrayList<String> getAllScreenNames();
	/*
	 * @Query(value =
	 * " SELECT * FROM KVDST02_SHIPMENTPLAN SP WHERE IFNULL(SP.VDST02_APPROVE_F,'N') ='N' ORDER BY SP.VDST02_SHIPMENTPLANNUMBER_C "
	 * , nativeQuery = true) List<ShipmentPlanDetails> findDistinctShipmentPlanNo();
	 * 
	 * @Modifying
	 * 
	 * @Query(value =
	 * " UPDATE KVDST02_SHIPMENTPLAN SP SET SP.VDST02_APPROVE_F = :appreject_flag  WHERE SP.VDST02_SHIPMENTPLANNUMBER_C = :shipmentPlanNo AND IFNULL(SP.VDST02_APPROVE_F,'N') ='N' "
	 * , nativeQuery = true) void updateApproveFlagByPlanNo(@Param("shipmentPlanNo")
	 * String shipmentPlanNo, @Param("appreject_flag") String appreject_flag);
	 */
	
}
