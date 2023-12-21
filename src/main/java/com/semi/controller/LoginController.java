package com.semi.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.semi.dto.MemberDTO;
import com.semi.service.BuyService;
import com.semi.service.CartService;
import com.semi.service.ChBoardService;
import com.semi.service.MemberService;

@SessionAttributes("user")
@Controller
public class LoginController {

	@Autowired
	private MemberService memberService;
	
	
	
	@ModelAttribute("user")
	public MemberDTO setMember() {
		return new MemberDTO();
	}
//  ---------------------------------------------------------------------------------
	
	// 로그인페이지 연결
	@GetMapping("/login")
	public String loginView() {
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(MemberDTO dto, Model model) {
		MemberDTO user = memberService.getMember(dto);
		if(user != null && user.getPassword().equals(dto.getPassword())) {
			model.addAttribute("user", user);
			 
			return "redirect:/"; // / 자체가 index를 가리킴  
		}
		
		//redirect는 컨트롤러를 한번 더 거치는거 새로고침할떼 많이 씀	
		//forword는 바로 페이지보여주는
		
		return "/user/login";
	}
	
//  ---------------------------------------------------------------------------------

	// 회원가입 페이지 연결
	@GetMapping("/joinform")
	public String joinform() {
		return "/user/joinform";
	}
	
	// 회원가입 완료 후  index 페이지 연결
	@PostMapping("/joinResult")
	public String joinResult(MemberDTO dto, Model model) {
		memberService.insertMember(dto);
		return "redirect:/";
	}
	
//  ---------------------------------------------------------------------------------
	
	// 로그아웃 되고 나면 세션 삭제 후 index 페이지 연결
	@GetMapping("/logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/";
	}
	
//	---------------------------------------------------------------------------------

	// 아이디 찾기 페이지 연결
	@GetMapping("/idsearch")
	public String idsearch() {
		return "/user/idsearchform";
	}

	@PostMapping("/find-username")
	@ResponseBody
	public String findUsername(@RequestParam("email") String email, @RequestParam("name") String name) {

		MemberDTO dto = memberService.searchId(email, name);
		if(dto == null) {
			return "없음";
		}else {
			return dto.getId();
		}
		
	}

//	---------------------------------------------------------------------------------

	// 비밀번호 찾기 페이지 연결
	@GetMapping("/pwsearch")
	public String pwsearch() {
		return "/user/pwsearchform";
	}

	@PostMapping("/find-password")
	@ResponseBody
	public String findPasswordAjax(@RequestBody MemberDTO dto) {
		String id = memberService.findPassword(dto);

		if (id != null) {
			return id;
		} else {
			// 예외적인 경우에는 고려하여 적절한 문자열을 반환
			return "일치하는 계정이 없습니다.";
		}
	}
	
	
	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam("idr") String id, @RequestParam("password")String password) {
		memberService.updatePassword(id, password);
	    return "/user/login";
	}
	
//	---------------------------------------------------------------------------------
	
	// 아이디 중복 체크
	@GetMapping("/checkId")
	public String checkid(@RequestParam String id, Model model) {
	    MemberDTO dto = memberService.checkMember(id);
	    if(dto == null) {
	    	model.addAttribute("message","사용 가능한 아이디 입니다.");
	    } else {
	    	model.addAttribute("message","이미 사용중인 아이디 입니다.");
	    }
		return "/user/checkIdpop";
	}
	
	@GetMapping(value="/Idcheck", produces = "application/json; charset=utf-8")
	@ResponseBody
	public int check(@RequestParam String id, Model model) {
		System.out.println(id);
		MemberDTO dto = memberService.checkMember(id);
		if(dto == null) {
			return 0;
		}else {
			return 1;
		}
		
	}
	
	//~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
	
	// 마이페이지(수정폼)
	@GetMapping("/mypage")
	public String mypage(HttpSession session, Model model) {
		MemberDTO dto = memberService.getMember((MemberDTO) session.getAttribute("user"));
		if(dto.getId() == null) {
			dto.setId("check");
		}
		if(dto.getId().equals("check")) {
			return "/user/login";
		}
		
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
		return "/user/mypage";
	}

	@PostMapping("/updateInfo")
	public String userUpdate(MemberDTO dto) {
		memberService.updateUser(dto);
		
		return "redirect:/";
	}
	//~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
	
	// 카카오 회원가입 및 로그인
	
    @PostMapping("/kakaoLogin")
	public ResponseEntity<String> kakaoLogin(@RequestBody Map<String, Object> kakaoData, Model model) {
    	System.out.println("======================================");
        String kakao1 = (String) kakaoData.get("kakao");
        System.out.println(kakao1);
        MemberDTO kakao = memberService.checkKakao(kakao1);
        if(kakao == null) {
        	
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        	
        } else if(kakao != null) {
        	model.addAttribute("user",kakao);
        }
        return ResponseEntity.ok("success");
    }
    
	// 회원가입 페이지 연결
	@GetMapping("/kakaojoinform")
	public String kakaojoinform(@RequestParam(name = "kakao", required = false) String kakao, Model model) {
		model.addAttribute("kakao", kakao);
		return "/user/kakaojoinform";
	}
	
	// 회원가입 완료 후  index 페이지 연결
	@PostMapping("/kakaojoinResult")
	public String kakaojoinResult(MemberDTO dto, Model model) {
		memberService.insertMember(dto);
		return "redirect:/";
	}
	
//	---------------------------------------------------------------------
	//회원탈퇴
	@GetMapping(value="/out", produces = "application/json; charset=utf-8")
	@ResponseBody
	public int out(@RequestParam("id") String id,@RequestParam("name") String name,SessionStatus status) {
		memberService.out(id, name);
		status.setComplete();
		return 1;
	}


}



































