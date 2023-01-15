package com.roysherst.practice.springbooth2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
	private Long id;
	private String name;
	private String email;
	
	private String token; //不會在資料庫欄位出現的東西都加在這

}
