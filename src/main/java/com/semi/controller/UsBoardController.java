package com.semi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.semi.dto.MemberDTO;
import com.semi.dto.UsBoardDTO;
import com.semi.service.UsBoardService;

@SessionAttributes("user")
@Controller
public class UsBoardController {

	@Autowired
	private UsBoardService usboardService;
	
	
	@ModelAttribute("user")
	public MemberDTO setUser() {
		return new MemberDTO();
	}
	
	@GetMapping("/usBoardList")
	public String usBoardList(Model model, UsBoardDTO board) {
		List<UsBoardDTO> boardList = usboardService.getUsBoardList(board);
		model.addAttribute("boardList", boardList);
		return "/usBoard/usBoardList";
	}
	
	
	@GetMapping("/insertUsBoard")
	public String insertUsBoardView() {
		return "/usBoard/insertUsBoard";
	}
	
	@PostMapping("/insertUsBoard")
	public String insertBoard(UsBoardDTO board) {
		usboardService.insertUsBoard(board);
		return "redirect:usBoardList";
	}
	
	@GetMapping("/usBoard") 
	public String usBoard(@ModelAttribute("user") MemberDTO user, UsBoardDTO board, Model model, Long seq) {
		
		if(user.getId() == null) {
    		model.addAttribute("message", "로그인이 필요한 서비스입니다.");
        	model.addAttribute("searchUrl", "/login");
    		return "/qaBoard/message";
    	}
		
		Optional<UsBoardDTO> usBoardOptional = usboardService.getBoard(board);
		if (usBoardOptional.isEmpty()) {
			model.addAttribute("message", "이미 삭제된 글입니다.");
	        model.addAttribute("searchUrl", "usBoardList");
	        return "/qaBoard/message";
	    } else {
	    	usboardService.usBoardUpdate(seq);
	        model.addAttribute("board", usBoardOptional.get());
	        return "/usBoard/usBoard";
	    }
	}
	
	@GetMapping("/modifyUsBoard")
	public String modifyUsBoard(UsBoardDTO board, Model model) {
		model.addAttribute("board", usboardService.getUsBoard(board));
		return "/usBoard/modifyUsBoard";
	}
	
	@GetMapping("/updateUsBoard")
	public String updateBoard(UsBoardDTO board) {
		usboardService.updateUsBoard(board);
		return "forward:usBoardList";
	}
	
	@GetMapping("/deleteUsBoard")
	public String deleteBoard(UsBoardDTO board) {
		usboardService.deleteUsBoard(board);
		return "redirect:usBoardList";
	}
}
