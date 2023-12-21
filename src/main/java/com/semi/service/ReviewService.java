package com.semi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.semi.dto.ReviewDTO;

public interface ReviewService {

	public static final String IMAGE = "src/main/resources/static/review/";

	public void fileProcess(MultipartHttpServletRequest mul);

	Page<ReviewDTO> getReviewList(Pageable pageable);

	void insertReview(ReviewDTO review);

	void Rating(Long a, String rating);

	public Page<ReviewDTO> ReviewSearchList(String searchKeyword, Pageable pagealbe);

	public void reviewDelete(Long seq);

	public void updateReview(MultipartHttpServletRequest mul);

	public ReviewDTO review(ReviewDTO dto);
	// ~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
	// 인덱스 연결

	List<ReviewDTO> findTop5ByOrderByRatingDesc();

	public Page<ReviewDTO> ReviewSearchList1(String searchKeyword, Pageable pageable);
	
	Page<ReviewDTO> getReviewsByProductName(String productName, Pageable pageable);


}