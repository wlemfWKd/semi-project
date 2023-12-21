package com.semi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.semi.dto.MemberDTO;
import com.semi.dto.ProductDTO;
import com.semi.dto.ReviewDTO;
import com.semi.service.ProductService;
import com.semi.service.ReviewService;

@SessionAttributes("user")
@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ReviewService reviewService;

	@ModelAttribute("user")
	public MemberDTO setMember() {
		return new MemberDTO();
	}
	// ------------------------------------------------------------------------

	@GetMapping("/productWrite")
	public String productWrite() {
		return "/product/productWrite";
	}

	// ------------------------------------------------------------------------

	@GetMapping("/productList")
	public String getProductList(ProductDTO product, Model model,
			@PageableDefault(page = 0, size = 10, sort = "seq", direction = Sort.Direction.DESC) Pageable pageable,
			String searchKeyword) {
		Page<ProductDTO> productList = null;

		if (searchKeyword == null) {
			productList = productService.getProductList(pageable);
		} else {
			productList = productService.productList(searchKeyword, pageable);
		}

		int nowPage = productList.getPageable().getPageNumber() + 1; 
        int blockSize = 5;  // 페이징 블록의 크기

        int startBlock = (nowPage - 1) / blockSize * blockSize + 1;
        int endBlock = startBlock + blockSize - 1;

        int startPage = Math.max(startBlock, 1); 
        int endPage = Math.min(endBlock, productList.getTotalPages()); 

		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("productList", productList);
		return "/product/productList";
	}

	@GetMapping("/productManagerList")
	public String getProductManagerList(ProductDTO product, Model model,
			@PageableDefault(page = 0, size = 10, sort = "seq", direction = Sort.Direction.DESC) Pageable pageable,
			String searchKeyword) {
		Page<ProductDTO> productList = null;

		if (searchKeyword == null) {
			productList = productService.productManagerList(pageable);
		} else {
			productList = productService.productList(searchKeyword, pageable);
		}

		int nowPage = productList.getPageable().getPageNumber() + 1; 
        int blockSize = 5;  // 페이징 블록의 크기

        int startBlock = (nowPage - 1) / blockSize * blockSize + 1;
        int endBlock = startBlock + blockSize - 1;

        int startPage = Math.max(startBlock, 1); 
        int endPage = Math.min(endBlock, productList.getTotalPages()); 
        
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("productList", productList);
		return "/product/productManagerList";
	}

	// ------------------------------------------------------------------------

	@PostMapping("/insertProduct")
	public String insertProduct(MultipartHttpServletRequest mul) {
		productService.fileProcess(mul);
		return "redirect:/productManagerList";
	}

	// ------------------------------------------------------------------------
	@GetMapping("/productModify")
	public String modifyProduct(ProductDTO product, Model model) {
		ProductDTO dto = productService.getProduct(product);
		model.addAttribute("product", dto);
		return "product/productModify";
	}

	// ------------------------------------------------------------------------

	@PostMapping("/productModify")
	public String updateProduct(MultipartHttpServletRequest mul) {

		productService.updateProduct(mul);

		return "redirect:/productManagerList";
	}

	// ------------------------------------------------------------------------

	@GetMapping("/deleteProduct")
	public String deleteProduct(ProductDTO product) {
		productService.deleteProduct(product);
		return "redirect:/productManagerList";
	}

	
	
	
	@GetMapping("/productArticle")
	public String productArticle(@RequestParam("seq") int seq, Model model, @PageableDefault(page = 0, size = 3, sort = "seq") Pageable pageable) {
	    ProductDTO product = productService.getProduct(seq);
	    model.addAttribute("product", product);

	    Page<ReviewDTO> relatedReviews = reviewService.getReviewsByProductName(product.getProductName(), pageable);
	    model.addAttribute("relatedReviews", relatedReviews);

	    int nowPage = relatedReviews.getPageable().getPageNumber() + 1; 
        int blockSize = 5;  // 페이징 블록의 크기

        int startBlock = (nowPage - 1) / blockSize * blockSize + 1;
        int endBlock = startBlock + blockSize - 1;

        int startPage = Math.max(startBlock, 1); 
        int endPage = Math.min(endBlock, relatedReviews.getTotalPages()); 

	    model.addAttribute("nowPage", nowPage);
	    model.addAttribute("startPage", startPage);
	    model.addAttribute("endPage", endPage);
	    model.addAttribute("productArticle", relatedReviews);

	    return "product/productArticle";
	}

	
	
	

	// ------------------------------------------------------------------------

	@PostMapping("/minusProduct")
	@ResponseBody
	public void minusProduct(@RequestParam("prodctCode") String productCode) {
		productService.minusProduct(productCode);
	}

	@PostMapping("/plusProduct")
	@ResponseBody
	public void plusProduct(@RequestParam("prodctCode") String productCode) {
		productService.plusProduct(productCode);
	}

	// ~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
	// 남성 상품 페이지
	@GetMapping("/productMale")
	public String getProductMaleList(ProductDTO product, Model model,
			@PageableDefault(page = 0, size = 10, sort = "seq", direction = Sort.Direction.DESC) Pageable pageable,
			String searchKeyword) {

		Page<ProductDTO> productMale = null;

		if (searchKeyword == null) {
			productMale = productService.getProductMaleList(pageable);
		} else {
			productMale = productService.productListM(searchKeyword, pageable);
		}

		int nowPage = productMale.getPageable().getPageNumber() + 1; 
        int blockSize = 5;  // 페이징 블록의 크기

        int startBlock = (nowPage - 1) / blockSize * blockSize + 1;
        int endBlock = startBlock + blockSize - 1;

        int startPage = Math.max(startBlock, 1); 
        int endPage = Math.min(endBlock, productMale.getTotalPages()); 

		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("productMale", productMale);
		return "/product/productMale";
	}

	
	
	// 여성 상품 페이지
	@GetMapping("/productFemale")
	public String getProductFemaleList(ProductDTO product, Model model,
			@PageableDefault(page = 0, size = 10, sort = "seq", direction = Sort.Direction.DESC) Pageable pageable,
			String searchKeyword) {

		Page<ProductDTO> productFemale = null;

		if (searchKeyword == null) {
			productFemale = productService.getProductFemaleList(pageable);
		} else {
			productFemale = productService.productListW(searchKeyword, pageable);
		}
		int nowPage = productFemale.getPageable().getPageNumber() + 1; 
        int blockSize = 5;  // 페이징 블록의 크기

        int startBlock = (nowPage - 1) / blockSize * blockSize + 1;
        int endBlock = startBlock + blockSize - 1;

        int startPage = Math.max(startBlock, 1); 
        int endPage = Math.min(endBlock, productFemale.getTotalPages()); 

		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("productFemale", productFemale);
		return "/product/productFemale";
	}

	// 공용 상품 페이지
	@GetMapping("/productCommon")
	public String getProductproductCommon(ProductDTO product, Model model,
			@PageableDefault(page = 0, size = 10, sort = "seq", direction = Sort.Direction.DESC) Pageable pageable,
			String searchKeyword) {

		Page<ProductDTO> productCommon = null;

		if (searchKeyword == null) {
			productCommon = productService.getProductproductCommon(pageable);
		} else {
			productCommon = productService.productListC(searchKeyword, pageable);
		}

		int nowPage = productCommon.getPageable().getPageNumber() + 1; 
        int blockSize = 5;  // 페이징 블록의 크기

        int startBlock = (nowPage - 1) / blockSize * blockSize + 1;
        int endBlock = startBlock + blockSize - 1;

        int startPage = Math.max(startBlock, 1); 
        int endPage = Math.min(endBlock, productCommon.getTotalPages()); 

		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("productCommon", productCommon);
		return "/product/productCommon";
	}

}
