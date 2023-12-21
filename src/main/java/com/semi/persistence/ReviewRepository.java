package com.semi.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

import com.semi.dto.MemberDTO;
import com.semi.dto.ReviewDTO;

public interface ReviewRepository extends JpaRepository<ReviewDTO, Long> {

//   List<ReviewDTO> findById(int Id);
	Page<ReviewDTO> findByProductContaining(String searchKeyword, Pageable pageable);

	ReviewDTO findBySeq(Long seq);

	List<ReviewDTO> findTop5ByReviewImageIsNotOrderByRatingDesc(String non);

	Page<ReviewDTO> findByReviewContentContaining(String searchKeyword, Pageable pageable);

	List<ReviewDTO> findAllByreviewid(String id);

	Page<ReviewDTO> findByProductOrderByReviewDateDesc(String productName, Pageable pageable);

}