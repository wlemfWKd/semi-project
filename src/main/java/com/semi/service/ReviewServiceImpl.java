
package com.semi.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.semi.dto.MemberDTO;
import com.semi.dto.ReviewDTO;
import com.semi.persistence.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository ReviewRepo;

	@Override
	public void fileProcess(MultipartHttpServletRequest mul) {
		ReviewDTO review = new ReviewDTO();

		review.setReviewid(mul.getParameter("reviewid"));
		review.setReviewContent(mul.getParameter("reviewContent"));
		review.setRating(mul.getParameter("rating"));
		review.setProduct(mul.getParameter("product"));
		review.setReviewWriter(mul.getParameter("reviewWriter"));

		MultipartFile file = mul.getFile("reviewImage");
		if (file.getSize() != 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			Calendar calendar = Calendar.getInstance();
			String sysFileName = sdf.format(calendar.getTime());
			sysFileName += file.getOriginalFilename();
			review.setReviewImage(sysFileName);

			String absolutePath = new File("").getAbsolutePath() + "//";

			File saveFile = new File(absolutePath + IMAGE + sysFileName);
			try {
				file.transferTo(saveFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			review.setReviewImage("non");
		}
		ReviewRepo.save(review);
	}

	@Override
	public Page<ReviewDTO> getReviewList(Pageable pageable) {
		return ReviewRepo.findAll(pageable);
	}

	@Override
	public void insertReview(ReviewDTO review) {
		ReviewRepo.save(review);
	}

	@Override
	public void Rating(Long a, String newRating) {
		Optional<ReviewDTO> optionalReview = ReviewRepo.findById(a);
		optionalReview.isPresent();
		ReviewDTO review = optionalReview.get();
		review.setRating(newRating);
		ReviewRepo.save(review);
	}

	@Override
	public Page<ReviewDTO> ReviewSearchList(String searchKeyword, Pageable pageable) {
		return ReviewRepo.findByProductContaining(searchKeyword, pageable);
	}

	public void reviewDelete(Long seq) {
		ReviewRepo.deleteById(seq);
	}

	@Override
	public void updateReview(MultipartHttpServletRequest mul) {

//		Long seq = Integer.parseInt(mul.getParameter("seq"));
		Long seq = Long.parseLong(mul.getParameter("seq"));

		System.out.println("seq = " + seq);
		ReviewDTO findReview = ReviewRepo.findById(seq).get();
		findReview.setReviewContent(mul.getParameter("reviewContent"));
		findReview.setProduct(mul.getParameter("product"));
		int findIndex = findReview.getReviewImage().lastIndexOf("-");
		String saveFileName ="";
		if(findReview.getReviewImage().equals("non")) {
			
		}else {
			
			saveFileName = findReview.getReviewImage().substring(findIndex);
		}
		
		MultipartFile file = mul.getFile("reviewImage");
		System.out.println("");
		if (!saveFileName.equals(file.getOriginalFilename())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			Calendar calendal = Calendar.getInstance();
			String sysFileName = sdf.format(calendal.getTime());
			sysFileName += file.getOriginalFilename();

			findReview.setReviewImage(sysFileName);

			String absolutePath = new File("").getAbsolutePath() + "//";

			File saveFile = new File(absolutePath + IMAGE + sysFileName);
			try {
				file.transferTo(saveFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ReviewRepo.save(findReview);
	}

	@Override
	public ReviewDTO review(ReviewDTO dto) {

		return ReviewRepo.findBySeq(dto.getSeq());
	}

	// ~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
	 @Override
	   public List<ReviewDTO> findTop5ByOrderByRatingDesc() {
	   String non = "non";
	      return ReviewRepo.findTop5ByReviewImageIsNotOrderByReviewDateDescRatingDesc(non);
	   }


	@Override
	public Page<ReviewDTO> ReviewSearchList1(String searchKeyword, Pageable pageable) {
		return ReviewRepo.findByReviewContentContaining(searchKeyword, pageable);
	}

	public Page<ReviewDTO> getReviewsByProductName(String productName, Pageable pageable) {
		return ReviewRepo.findByProductOrderByReviewDateDesc(productName, pageable);
	}

}
