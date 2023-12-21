package com.semi.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.semi.dto.CheckDTO;
import com.semi.dto.MemberDTO;
import com.semi.dto.ProductDTO;
import com.semi.service.MemberService;
import com.semi.service.MemberServiceImpl;
import com.semi.service.ProductService;
@SessionAttributes("user")
@Controller
public class FaceCheckController {
	
	@Autowired
	private ProductService productService;

	@Autowired
	MemberService memberService;
	
	@ModelAttribute("user")
	public MemberDTO setMember() {
		return new MemberDTO();
	}

//  ---------------------------------------------------------------------------------
	
	// test 클릭하면 facecheck페이지로 연결
	@GetMapping("/facecheck")
	public String check(@ModelAttribute("user") MemberDTO dto, Model model) {
		if(dto.getId() == null) {
			return "/user/login";
		}
		
		return "/facecheck/facecheck";
	}

	// facecheck에서 피부진단 눌리면 결과 페이지 연결
	@PostMapping("/checkResult")
	public String result(@ModelAttribute("user") MemberDTO dto, CheckDTO chdto, ProductDTO podto, Model model) {
		podto = productService.check(chdto, podto);
		MemberDTO dto1 = memberService.checkMember(dto.getId());
		model.addAttribute("memberdto", dto1);
		model.addAttribute("prdto", podto);
		model.addAttribute("dto",chdto);
		return "/facecheck/checkResult";
	}
}
