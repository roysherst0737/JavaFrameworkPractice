package com.roysherst.practice.springbooth2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.roysherst.practice.springbooth2.domain.Customer;
import com.roysherst.practice.springbooth2.dto.CustomerRequest;
import com.roysherst.practice.springbooth2.dto.CustomerResponse;
import com.roysherst.practice.springbooth2.service.CustomerService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
	
	private final CustomerService customerService;

	@GetMapping("/findAll") //Restful規範：查詢是用get; 新增是用post; 修改是用put（或patch); 刪除是用delete
	@ResponseStatus(HttpStatus.OK)
	public List<CustomerResponse> findAll() {
		
		return customerService.listAll();
				
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CustomerResponse selectById(@PathVariable Long id, @RequestHeader("authorization") String token) {
		CustomerRequest customerRequest = CustomerRequest.builder()
														.id(id)
														.token(token)
														.build();
		System.out.println(customerRequest);
		
		return customerService.selectById(customerRequest.getId());
		
	}
	
	@PostMapping("/addCustomer")
	public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
		
		if(customerService.addCustomer(customer)) {
			return new ResponseEntity<String>("新增成功", HttpStatus.ACCEPTED);			
		}else {
			return new ResponseEntity<String>("新增失敗", HttpStatus.NOT_ACCEPTABLE);			
			
		}					
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		
		if(customerService.remove(id)) {
			return new ResponseEntity<String>("刪除成功", HttpStatus.ACCEPTED);			
		}else {
			return new ResponseEntity<String>("刪除失敗", HttpStatus.NOT_ACCEPTABLE);			
			
		}					
	}
	
	@GetMapping("/{name}/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String selectByNameAndId(@PathVariable String name, @PathVariable Long id) {
		
		return customerService.selectByNameAndId(name, id);
		
	}
}
