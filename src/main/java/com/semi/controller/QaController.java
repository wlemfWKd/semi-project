package com.semi.controller;


import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.semi.dto.CommentDTO;
import com.semi.dto.MemberDTO;
import com.semi.dto.QaBoard;
import com.semi.service.CommentService;
import com.semi.service.QaBoardService;

@SessionAttributes("user")
@Controller
public class QaController {

   @ModelAttribute("user")
   public MemberDTO setUser() {
      return new MemberDTO();
   }
   
   @Autowired
   private QaBoardService qaBoardService;
   
   @Autowired
   private CommentService commentService;
   
   @GetMapping("/qaBoardWrite")
    public String qaboardWriteForm() {
        return "/qaBoard/qaBoardWrite"; 
    }

    @PostMapping("/qaWritepro")
    public String qaboardWritePro(QaBoard board, Model model) throws Exception{
       qaBoardService.write(board);
       
       model.addAttribute("message", "글 작성이 완료되었습니다.");
       model.addAttribute("searchUrl", "/qaBoardList");
       
        return "/qaBoard/message";
    }
    
    @GetMapping("/qaBoardList")
    public String qaBoardList(Model model, String select, @PageableDefault(page = 0, size = 10, sort = "seq", direction = Sort.Direction.DESC) Pageable pageable, String searchKeyword) {
       
       Page<QaBoard> list = null;
       
       if (searchKeyword == null) {
       
       list = qaBoardService.qaBoardList(pageable);
       
       } else if(select.equals("title")){
          
       list = qaBoardService.boardSearchList(searchKeyword, pageable);
       
       } else if(select.equals("content")){
           
       list = qaBoardService.boardSearchList2(searchKeyword, pageable);
       
       } else {
           
       list = qaBoardService.boardSearchList3(searchKeyword, pageable);
        
       }
       
       int nowPage = list.getPageable().getPageNumber() + 1; 
       int blockSize = 5;  // 페이징 블록의 크기

       int startBlock = (nowPage - 1) / blockSize * blockSize + 1;
       int endBlock = startBlock + blockSize - 1;

       int startPage = Math.max(startBlock, 1); 
       int endPage = Math.min(endBlock, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
       
       return "/qaBoard/qaBoardList";
    }
    
    @GetMapping("/qaBoardView")
    public String boardView(@ModelAttribute("user") MemberDTO user, Model model, @RequestParam Long seq, QaBoard board,
                      CommentDTO dto,@PageableDefault(page = 0, size=5, sort="commentNum", direction = Sort.Direction.DESC) Pageable pageable) {
       
       if(user.getId() == null) {
          model.addAttribute("message", "로그인이 필요한 서비스입니다.");
           model.addAttribute("searchUrl", "/login");
          return "/qaBoard/message";
       }
       
       
       Optional<QaBoard> qaBoardOptional = qaBoardService.getBoard(board);
		if (qaBoardOptional.isEmpty()) {
			model.addAttribute("message", "이미 삭제된 글입니다.");
	        model.addAttribute("searchUrl", "qaBoardList");
	        return "/qaBoard/message";
	    } else {
       model.addAttribute("board", qaBoardService.qaBoardView(seq));
          Page<CommentDTO> commentpage = commentService.commentList(dto, seq, pageable);
          int nowPage = commentpage.getPageable().getPageNumber() + 1; 
          int blockSize = 5;  // 페이징 블록의 크기

          int startBlock = (nowPage - 1) / blockSize * blockSize + 1;
          int endBlock = startBlock + blockSize - 1;

          int startPage = Math.max(startBlock, 1); 
          int endPage = Math.min(endBlock, commentpage.getTotalPages());

        model.addAttribute("comment",commentpage);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
         qaBoardService.qaBoardUpdate(seq);
       return "/qaBoard/qaBoardView";
       
	    }
    }
    
    @GetMapping("/deleteQaBoard")
    public String qaboardDelete(Long seq, Model model) {
       qaBoardService.qaboardDelete(seq);
       commentService.deleteComment(seq);
       model.addAttribute("message", "글 삭제가 완료되었습니다.");
       model.addAttribute("searchUrl", "/qaBoardList");
       
       return "/qaBoard/message";
    }
    
    @GetMapping("/modifyQaBoard")
    public String qaboardModify(Model model, Long seq) {
       model.addAttribute("board", qaBoardService.qaBoardView(seq));
       return "/qaBoard/qaBoardModify";
    }
    
    @PostMapping("/updateQaBoard")
    public String boardUpdate(Long seq, QaBoard board, Model model) throws Exception {
       QaBoard boardTemp = qaBoardService.qaBoardView(seq);
       
       boardTemp.setTitle(board.getTitle());
       boardTemp.setContent(board.getContent());

       qaBoardService.write(boardTemp);
       
       model.addAttribute("message", "글 수정이 완료되었습니다.");
       model.addAttribute("searchUrl", "/qaBoardList");
       
       return "/qaBoard/message";
    }
    @PostMapping("/comment")
    public String comment(CommentDTO dto, RedirectAttributes ra) {
       commentService.save(dto);
       ra.addAttribute("seq", dto.getQaseq());
       return "redirect:/qaBoardView";
    }
    
    @GetMapping("/commentDelete")
    public String delete2(@RequestParam("commentNum")Long num,@RequestParam("qaseq") Long seq, RedirectAttributes ra) {
    	commentService.delete(num);
        ra.addAttribute("seq", seq);
    	return "redirect:/qaBoardView";
    }
    
    
}