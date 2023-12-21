package com.semi.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semi.dto.BuyProducts;
import com.semi.dto.MemberDTO;
import com.semi.dto.ProductDTO;
import com.semi.service.MemberService;
import com.semi.service.ProductService;

@SessionAttributes("user")
@Controller
public class PayController {

	@Autowired
	private ProductService productService;

	@Autowired
	private MemberService memberService;

	@ModelAttribute("user")
	public MemberDTO setMember() {
		return new MemberDTO();
	}

	@GetMapping("/pay")
	public String payView(@RequestParam("seq") int seq, @RequestParam("productCount") int productCount,
			HttpSession session, @RequestParam("addr") String addr, @RequestParam("phone") String phone,
			@RequestParam("name") String name, @RequestParam("productPrice") int productPrice, Model model) {
		System.out.println("==============================================");
		System.out.println(productPrice);
		MemberDTO dto = (MemberDTO) session.getAttribute("user");
		ProductDTO prodto = productService.getProduct(seq);
		prodto.setProductCount(productCount);
		prodto.setProductPrice(productPrice);
		System.out.println("19423709832740981237408912473098123790413274098");
		System.out.println(prodto.getProductCode());
		model.addAttribute("addr",addr);
		model.addAttribute("name",name);
		model.addAttribute("phone",phone);
		model.addAttribute("prodto", prodto);
		model.addAttribute("memdto", dto);
		return "/pay/pay";
	}

	

	@PostMapping("/cartpay")
	public String cartPay(HttpSession session, @RequestParam("addr") String addr, @RequestParam("phone") String phone,
			@RequestParam("name") String name, Model model) throws JsonMappingException, JsonProcessingException {

		MemberDTO dto = (MemberDTO) session.getAttribute("user");
		session.setAttribute("buyUserInfo", dto);
		
		//이전에 cartOrderForm에서 올려둔 장바구니 정보 가져오기
		String products = (String) session.getAttribute("buyProducts");
		
		if (products == null) {
			// 혹시 세션에 올려둔 정보를 못가져올 경우..
			System.out.println("정보를 가져오지 못함");
			return null;
		}
		
		// String 형식의 json데이터를 내가 지정한 데이터형식으로 매핑해줌
		//인터넷이 이렇게 하라함ㅇㅇ
		ObjectMapper objectMapper = new ObjectMapper();
		List<BuyProducts> productLists = objectMapper.readValue(products, new TypeReference<List<BuyProducts>>(){});
		
		int totalPrice = 0;
		for (BuyProducts buyProducts : productLists) {
			totalPrice += Integer.parseInt(buyProducts.getBpPrice());
		}
		model.addAttribute("addr",addr);
		model.addAttribute("name",name);
		model.addAttribute("phone",phone);
		model.addAttribute("productList", productLists);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("member", dto);

		return "/pay/cartPay";
	}

	@GetMapping("/send")
	public String send() {
		return "/pay/send";
	}

	@GetMapping("/intro")
	public String introView() {
		return "/introduce/intro";
	}
}
