package com.semi.persistence;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.semi.dto.CommentDTO;
import com.semi.dto.MemberDTO;

public interface CommentRepository extends JpaRepository<CommentDTO, Long>{

	Page<CommentDTO> findAllByQaseq(Long qaseq, Pageable pageable);


	Page<CommentDTO> findAllByQaseqOrderByCommentNumDesc(Long seq, Pageable pageable);


	List<CommentDTO> findByQaseq(Long qaseq);


	List<CommentDTO> findAllByWriter(String name);




	CommentDTO findByCommentNum(Long num);





}
