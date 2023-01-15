package com.roysherst.practice.springbooth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.roysherst.practice.springbooth2.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	//若要新增或複寫方法，可以寫在這裡
	@Query("SELECT c.email FROM Customer c WHERE c.name = :name AND c.id = :id")
	String findByNameAndId(@Param(value = "name") String name, @Param(value = "id") Long id);
	

}
