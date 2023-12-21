package com.semi.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.semi.dto.CartDTO;
import com.semi.dto.MemberDTO;
import com.semi.dto.ProductDTO;
import com.semi.service.CartService;
import com.semi.service.MemberService;

@Controller
public class CartController {
	
	@Autowired
	private MemberService ms;
	
	@Autowired
	private CartService cs;
	
	@GetMapping("/cartList")
	public String myCartPage(HttpSession session, Model model) {
		//세션에서 유저 가져오기
		MemberDTO member = (MemberDTO)session.getAttribute("user");
		if(member.getId() == null) {
			return "/user/login";
		}else if (member != null) {
	            // 장바구니의 아이템을 가져온다.
	            List<CartDTO> cartItems = cs.getCartList(member.getId());
	            //총 금액	
	            int totalPrice = 0;
	            for (CartDTO cartItem : cartItems) {
	                totalPrice += (cartItem.getCartPrice() * cartItem.getCartCount());
	            }

	            model.addAttribute("cartItems", cartItems);
	            model.addAttribute("totalPrice", totalPrice);

	            return "/cart/cartList";
	    }

	    return "redirect:/";
		
	}

	@PostMapping("/insertCart")
	@ResponseBody
	public int insertCart(@ModelAttribute("user") MemberDTO user, @RequestBody ProductDTO dto , HttpSession session) {
		MemberDTO dto1 = (MemberDTO) session.getAttribute("user");
		if (dto1.getId() == null) {
			return 2;
		}
		System.out.println("insertCart 들어왔다.");
		System.out.println(dto.getSeq());
		System.out.println(dto.getProductCount());
		int seq = dto.getSeq();
		int count = dto.getProductCount();
		System.out.println(seq);
		System.out.println(count);
		MemberDTO member = (MemberDTO)session.getAttribute("user");
		System.out.println("유저 아이디 : " + member.getId());
		cs.insertCart(seq,count,member.getId());
		return 1;
	}
	
	@GetMapping("/deleteCart")
	public String deleteCart(@RequestParam("cartCode") String cartCode, HttpSession session) {
		MemberDTO member = (MemberDTO)session.getAttribute("user");
		cs.deleteCart(cartCode, member.getId());
		return "redirect:/cartList";
	}
	
	@PostMapping("/minusCart")
	@ResponseBody
	public void minusCart(@RequestParam("cartCode") String cartCode, HttpSession session) {
		MemberDTO member = (MemberDTO)session.getAttribute("user");
		cs.minusCart(cartCode, member.getId());
	} 
	
	@PostMapping("/plusCart")
	@ResponseBody
	public void plusCart(@RequestParam("cartCode") String cartCode, HttpSession session) {
		MemberDTO member = (MemberDTO)session.getAttribute("user");
			cs.plusCart(cartCode, member.getId());
	}
	
	
	
}
