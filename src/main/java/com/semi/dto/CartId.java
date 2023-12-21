package com.semi.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CartId implements Serializable{
	
	private String cartCode;
	private String userId;

}
