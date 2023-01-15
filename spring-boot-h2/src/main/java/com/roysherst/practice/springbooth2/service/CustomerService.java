package com.roysherst.practice.springbooth2.service;

import java.util.List;

import com.roysherst.practice.springbooth2.domain.Customer;
import com.roysherst.practice.springbooth2.dto.CustomerResponse;

public interface CustomerService {
	
	public List<CustomerResponse> listAll();
	public CustomerResponse selectById(Long id);
	public boolean addCustomer(Customer customer);
	public boolean remove(Long id);
	
	public String selectByNameAndId(String name, Long id);
}
