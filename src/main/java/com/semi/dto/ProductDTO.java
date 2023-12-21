package com.semi.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="PRODUCT")
public class ProductDTO {
	
	@Id
	@GeneratedValue
	private int seq;
	
	private String productCode;		// 상품코드
	private String productName;		// 상품이름
	private int productPrice;	// 상품가격
	
	private String productImage;	// 상품 이미지
	private String productContent;	// 상품 설명
	private String productGender;	// 상품 성별
	@Column(updatable = false, columnDefinition = "integer default 0")
	private int productCount;
	@Column(columnDefinition = "integer default 0")
	private int cnt;
	
    public ProductDTO() {
        this.productCount = 0;
    }

}
