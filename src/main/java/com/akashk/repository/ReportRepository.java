package com.akashk.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.akashk.binding.Customer;

public interface ReportRepository extends JpaRepository<Customer, Serializable>{

	@Query("SELECT DISTINCT c.planType FROM customer c")
	List<String> getDistinctByPlanType();
	
	@Query("SELECT DISTINCT c.planStatus FROM customer c")
	List<String> getDistinctPlanStatus();
	
	
	/*
	 * List<Customer> findByPlanTypeAndPlanStatusAndStartDateBetween(String
	 * planType,String planStatus,Date startDate,Date endDate);
	 * 
	 * List<Customer> findByPlanTypeAndPlanStatusAndStartDate(String planType,String
	 * planStatus,Date startDate);
	 * 
	 * List<Customer> findByPlanTypeAndPlanStatus(String planType,String
	 * planStatus);
	 * 
	 * List<Customer> findByStartDateBetween(Date startDate,Date endDate);
	 * 
	 * List<Customer> findByPlanTypeAndStartDateBetween(String planType,Date
	 * startDate,Date endDate);
	 * 
	 * List<Customer> findByPlanStatusAndStartDateBetween(String planStatus,Date
	 * startDate,Date endDate);
	 * 
	 * List<Customer> findByPlanTypeOrPlanStatusOrStartDateBetween(String
	 * planType,String planStatus,Date startDate,Date endDate);
	 * 
	 * List<Customer> findByPlanTypeOrPlanStatusOrStartDate(String planType,String
	 * planStatus,Date startDate);
	 */
	
		
	
}
