package com.semi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.semi.dto.MemberDTO;
import com.semi.dto.ProductDTO;
import com.semi.dto.ReviewDTO;
import com.semi.service.ProductService;
import com.semi.service.ReviewService;
@SessionAttributes("user")
@Controller
public class IndexController {
	
	// ~-~-~-~-~-~-~-~-~-
	// 리뷰서비스연결
	@Autowired
	private ReviewService reviewService;

	@Autowired
	private ProductService productService;
	
	@ModelAttribute("user")
	public MemberDTO setUser() {
		return new MemberDTO();
	}
	
	// ~-~-~-~-~-~-~-~-~-
    @GetMapping("/")
    public String indexView(Model model) {
        List<ReviewDTO> topRatedReviews = reviewService.findTop5ByOrderByRatingDesc();
        for (int i = 0; i < topRatedReviews.size(); i++) {
			if(topRatedReviews.get(i).getReviewImage().equals("non")) {
				topRatedReviews.remove(i);
			}
		}
        for (int i = 0; i < topRatedReviews.size(); i++) {
        	System.out.println(topRatedReviews.get(i));
        }
        model.addAttribute("reviews", topRatedReviews);
        List<ProductDTO> cntPro = productService.getProductList2();
        model.addAttribute("cntPro", cntPro);
        return "index";
    }
    @GetMapping("/introduce")
    public String intro() {
       return "/introduce/introduce";
    }
    @GetMapping("/privacy")
	public String introView2() {
		return "/introduce/privacy";
	}
	
	@GetMapping("/agreement")
	public String introView3() {
		return "/introduce/agreement";
	}
	
}
