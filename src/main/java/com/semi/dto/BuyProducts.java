package com.semi.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class BuyProducts implements Serializable{
	
	String bpCode;
	String bpCount;
	String bpPrice;
	String bpName;
}
