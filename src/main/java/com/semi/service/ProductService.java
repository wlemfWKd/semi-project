package com.semi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.semi.dto.ChBoard;
import com.semi.dto.CheckDTO;
import com.semi.dto.ProductDTO;
import com.semi.dto.QaBoard;
import com.semi.dto.ReviewDTO;

public interface ProductService {
	
	public List<ProductDTO> getProductList(ProductDTO product);
	public List<ProductDTO> getProductList2();

	public static final String IMAGE="src/main/resources/static/files/";
	public void fileProcess(MultipartHttpServletRequest mul);
	
	public void insertProduct(ProductDTO product);
	
	public ProductDTO getProduct(ProductDTO product);
	public ProductDTO getProduct(int seq);
	public void updateProduct(MultipartHttpServletRequest mul);
	
	public void deleteProduct(ProductDTO product);
	
	public ProductDTO check(CheckDTO dto, ProductDTO product);
	
	public Page<ProductDTO> getProductList(Pageable pageable);
	
	public void minusProduct(String productCode);
	
	public void plusProduct(String productCode);
	
	//~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
	
	
	
	// 남성, 여성, 공용 구분 페이징
	
	Page<ProductDTO> getProductMaleList(Pageable pageable);
	Page<ProductDTO> getProductFemaleList(Pageable pageable);
	Page<ProductDTO> getProductproductCommon(Pageable pageable);
	
	Page<ProductDTO> productManagerList(Pageable pageable);
	
	public Page<ProductDTO> productListM(String searchKeyword, Pageable pagealbe);
	public Page<ProductDTO> productListW(String searchKeyword, Pageable pagealbe);
	public Page<ProductDTO> productListC(String searchKeyword, Pageable pagealbe);
	public Page<ProductDTO> productList(String searchKeyword, Pageable pagealbe);
	
}
