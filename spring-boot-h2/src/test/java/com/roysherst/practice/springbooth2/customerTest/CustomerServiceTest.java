package com.roysherst.practice.springbooth2.customerTest;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.roysherst.practice.springbooth2.dto.CustomerResponse;
import com.roysherst.practice.springbooth2.service.CustomerService;

@SpringBootTest
public class CustomerServiceTest {
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void test1() {
		List<CustomerResponse> customerSample = new ArrayList<>();
		List<CustomerResponse> customerTest = customerService.listAll();
		CustomerResponse customer1 = CustomerResponse.builder()
									.id(1L)
									.name("Andy")
									.email("andy@gmail.com")
									.build();
		CustomerResponse customer2 = CustomerResponse.builder()
									.id(2L)
									.name("Roy")
									.email("roy@gmail.com")
									.build();
		CustomerResponse customer3 = CustomerResponse.builder()
									.id(3L)
									.name("Po")
									.email("po@gmail.com")
									.build();
		CustomerResponse customer4 = CustomerResponse.builder()
									.id(4L)
									.name("Jimmy")
									.email("jimmy@gmail.com")
									.build();
		CustomerResponse customer5 = CustomerResponse.builder()
									.id(5L)
									.name("Simon")
									.email("simon@gmail.com")
									.build();
		CustomerResponse customer6 = CustomerResponse.builder()
									.id(6L)
									.name("Buddy")
									.email("buddy@gmail.com")
									.build();
		
		customerSample.add(customer1);
		customerSample.add(customer2);
		customerSample.add(customer3);
		customerSample.add(customer4);
		customerSample.add(customer5);
		customerSample.add(customer6);
		
		Assertions.assertThat(customerTest).isEqualTo(customerSample);
		
	}
	
	@Test
	public void test2() {
		CustomerResponse customerSample = CustomerResponse.builder()
									.id(1L)
									.name("Andy")
									.email("andy@gmail.com")
									.build();
		
		CustomerResponse customerResponseTest = customerService.selectById(1L);
		
		Assertions.assertThat(customerResponseTest).isEqualTo(customerSample);
		
	}
}
