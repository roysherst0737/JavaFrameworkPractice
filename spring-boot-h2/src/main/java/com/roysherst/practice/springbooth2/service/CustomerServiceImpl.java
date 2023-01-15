package com.roysherst.practice.springbooth2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.roysherst.practice.springbooth2.domain.Customer;
import com.roysherst.practice.springbooth2.dto.CustomerResponse;
import com.roysherst.practice.springbooth2.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //建構式注入，比autowired還好(setter方法注入、autowired注入、建構式注入)
public class CustomerServiceImpl implements CustomerService{
	
	private final CustomerRepository customerRepository;
	
	//可以用@RequiredArgsConstructor取代
//	public CustomerServiceImpl(CustomerRepository customerRepository) {
//		this.customerRepository = customerRepository;
//	}

	@Override
	public List<CustomerResponse> listAll() {
		return maptoDto2(customerRepository.findAll());
	}

	@Override
	public CustomerResponse selectById(Long id) {
		
		//練習使用DTO
		return maptoDto(customerRepository.findById(id).orElse(new Customer()));
	}

	@Override
	public boolean addCustomer(Customer customer) {
		
		try {
			customerRepository.save(customer);
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	private CustomerResponse maptoDto(Customer customer) {
		
		CustomerResponse customerResponse = CustomerResponse.builder()
															.id(customer.getId())
															.name(customer.getName())
															.email(customer.getEmail())
															.build();
		return customerResponse;
	}
	
	private List<CustomerResponse> maptoDto2(List<Customer> customers) {
		
		List<CustomerResponse> list = new ArrayList<>();
		
		for(int i = 0; i < customers.size(); i++) {
			CustomerResponse customerResponse = CustomerResponse.builder()
																.id(customers.get(i).getId())
																.name(customers.get(i).getName())
																.email(customers.get(i).getEmail())
																.build();
			list.add(customerResponse);
		}
		
		return list;
	}

	@Override
	public boolean remove(Long id) {
		try {
			customerRepository.deleteById(id);
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public String selectByNameAndId(String name, Long id) {
		if(customerRepository.findByNameAndId(name, id) != null) {
			return customerRepository.findByNameAndId(name, id);
		} else {
			return"請重新輸入";
		}
	}
	

}
