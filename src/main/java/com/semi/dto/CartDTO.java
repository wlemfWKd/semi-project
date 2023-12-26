package com.semi.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="CART")
@IdClass(CartId.class)
public class CartDTO {
	
	@Id
	private String cartCode;
	
	@Id
	private String userId;
	
	@Id
	private int seq;
	
	private String cartName;
	private int cartCount;
	private int cartPrice;
	private String cartImage;
}
