package com.semi.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="BUY")
public class BuyDTO {

	@Id
	@GeneratedValue
	private int seq;
		
	private String id;			// 아이디
	private String name;		// 이름
	private String phone;		// 폰번호
	private String addr;		// 주소
	private String productCode;		// 상품코드
	private String productName;		// 상품이름
	private int productPrice;	// 상품가격
	private int productCount;
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date createdate;
	
	
}
