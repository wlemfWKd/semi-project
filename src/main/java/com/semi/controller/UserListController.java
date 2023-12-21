package com.semi.controller;

import java.util.List;


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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.semi.dto.MemberDTO;
import com.semi.dto.ProductDTO;
import com.semi.service.MemberService;
import com.semi.service.ProductService;

@SessionAttributes("user")
@Controller
public class UserListController {
	
	@Autowired
	private MemberService memberservice; 
	
	@ModelAttribute("user")
	public MemberDTO setMember() {
		return new MemberDTO();
	}
	
	//------------------------------------------------------------------------	
	
	// 회원 전체 리스트 보여주기
	@GetMapping("/userlist")
	public String userlist(Model model, @PageableDefault(page = 0, size = 5, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<MemberDTO> userList = memberservice.userList(pageable) ;
		
		 int nowPage = userList.getPageable().getPageNumber() + 1; 
         int blockSize = 5;  // 페이징 블록의 크기

         int startBlock = (nowPage - 1) / blockSize * blockSize + 1;
         int endBlock = startBlock + blockSize - 1;

         int startPage = Math.max(startBlock, 1); 
         int endPage = Math.min(endBlock, userList.getTotalPages());

		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("userList", userList);
		
		return "manager/userList";
	}
	
	
	@GetMapping("/deleteUser")
	public String deleteUser(MemberDTO user) {
		memberservice.deleteUser(user);
		return "redirect:userlist";
	}
	
	@GetMapping("/userModify")
	public String updateUser(MemberDTO user, Model model) {
		MemberDTO dto = memberservice.getMember(user);
		String email = dto.getEmail();
		if(email==null) {
			model.addAttribute("email",email);
		}else {
			String email1[] = email.split("@");
			String email2 = email1[0];	
			model.addAttribute("email", email2);
		}
		
		
		String phone = dto.getPhone();
		if(phone.equals("--")) {
			model.addAttribute("num1",null);
			model.addAttribute("num2",null);
			model.addAttribute("num3",null);
			System.out.println("확인용");
		}else {
			String num[] = phone.split("-");
			model.addAttribute("num1",num[0]);
			model.addAttribute("num2",num[1]);
			model.addAttribute("num3",num[2]);
			System.out.println("확인2");
			System.out.println(num[0]);
			System.out.println(num[1]);
			System.out.println(num[2]);
		}
		
		String addr = dto.getAddr();
		if(addr.equals("//")) {
			model.addAttribute("addr1",null);
			model.addAttribute("addr2",null);
			model.addAttribute("addr3",null);
		}else {
			String addr0[] = addr.split("/");
			model.addAttribute("addr1", addr0[0]);
			model.addAttribute("addr2", addr0[1]);
			model.addAttribute("addr3", addr0[2]);
		}
		model.addAttribute("user", dto);
		return "/manager/userModify";
	}
	
	@GetMapping("/updateUser")
	public String updateUser(MemberDTO user) {
		memberservice.updateUser(user);
		return "redirect:userlist";
	}
	
}

