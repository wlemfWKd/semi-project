
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.semi.dto.BuyDTO;
import com.semi.dto.MemberDTO;
import com.semi.dto.ReviewDTO;
import com.semi.service.BuyService;
import com.semi.service.ReviewService;

@SessionAttributes("user")
@Controller
public class ReviewController {

   @Autowired
   private ReviewService reviewService;

   @Autowired
   private BuyService buyService;
   
   @ModelAttribute("user")
   public MemberDTO setMember() {
      return new MemberDTO();
   }

   // ~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
   // 리뷰 목록
   
   @GetMapping("/getReviewList")
   public String getReviewList(Model model,String select, @PageableDefault(page=0,size=5, sort="seq", direction = Sort.Direction.DESC) Pageable pageable, String searchKeyword) {
      
      Page<ReviewDTO> list = null;
      
      if (searchKeyword == null) {
         list = reviewService.getReviewList(pageable);
      } else if(select.equals("product")){
         list = reviewService.ReviewSearchList(searchKeyword ,pageable);
      } else {
         list = reviewService.ReviewSearchList1(searchKeyword ,pageable);
      }
      
      int nowPage = list.getPageable().getPageNumber() + 1; 
      int blockSize = 5;  // 페이징 블록의 크기

      int startBlock = (nowPage - 1) / blockSize * blockSize + 1;
      int endBlock = startBlock + blockSize - 1;

      int startPage = Math.max(startBlock, 1); 
      int endPage = Math.min(endBlock, list.getTotalPages()); 
        
        model.addAttribute("ReviewList", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
       
       return "/review/getReviewList";        
   }

   // ~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
   // 리뷰 작성
//   @ModelAttribute("user") MemberDTO user, Model model
   @GetMapping("/insertReview")
   public String insertReviewView(@ModelAttribute("user") MemberDTO user, Model model) {
       if(user.getId() == null) {
           model.addAttribute("message", "로그인이 필요한 서비스입니다.");
            model.addAttribute("searchUrl", "/login");
           return "/qaBoard/message";
        }
       
       List<BuyDTO> list = null;
       list = buyService.getBuyList(user.getId());
       model.addAttribute("list", list);
      return "/review/insertReview";
   }
   // ~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
   // 작성된 리뷰 목록
//   @PostMapping("/insertReview")
//   public String insertReview(ReviewDTO review) {
//      
//      reviewService.insertReview(review);
//      return "redirect:getReviewList";
//   }
   // ~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
   // 리뷰목록
   @PostMapping("/insertimage")
   public String insertimage(MultipartHttpServletRequest mul) {
	   reviewService.fileProcess(mul);
	   return "redirect:/getReviewList";
   }
   
   
   // ~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
   //평점
   
   @PostMapping("/submitRating")
   public String Rating(@ModelAttribute("user") MemberDTO member, @RequestParam Long a, @RequestParam String rating) {
       reviewService.Rating(a, rating);
       return "redirect:getReviewList";
   }
   // ~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
   // 리뷰 삭제
   @GetMapping("/deleteReview")
   public String deleteReview(Long seq, Model model) {
	   reviewService.reviewDelete(seq);
	   
	   model.addAttribute("message", "리뷰 삭제가 완료되었습니다.");
	   model.addAttribute("searchUrl", "/getReviewList");
	   
	   return "/review/message";
   }
   // ~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
   // 리뷰 수정  
   @GetMapping("/reviewModify")
   public String modifyReview(ReviewDTO review, Model model) {
	   ReviewDTO dto1 = reviewService.review(review);
	   model.addAttribute("review", dto1);
	   return "review/reviewModify";
   }
   
   @PostMapping("/reviewModify")
   public String updateReview(MultipartHttpServletRequest mul) {
	   reviewService.updateReview(mul);
	   System.out.println(mul);
	   
	   return "redirect:/getReviewList";
   }

}














