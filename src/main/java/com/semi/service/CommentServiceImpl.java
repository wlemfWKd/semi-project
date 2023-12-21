package com.semi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.semi.dto.CommentDTO;
import com.semi.dto.MemberDTO;
import com.semi.persistence.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository CommentRepo;
	
	@Override
	public void save(CommentDTO dto) {
		CommentRepo.save(dto);
	}
	
	@Override
	public Page<CommentDTO> commentList(CommentDTO dto, Long seq, Pageable pageable) {
		Page<CommentDTO> commentList = (Page<CommentDTO>) CommentRepo.findAllByQaseqOrderByCommentNumDesc(seq, pageable);
		return commentList;
	}

	@Override
	public void deleteComment(Long qaseq) {
		List<CommentDTO> comments = CommentRepo.findByQaseq(qaseq);
	    for (CommentDTO comment : comments) {
	        CommentRepo.delete(comment);
	    }
	}
	@Override
	public void delete(Long num) {
		CommentDTO comment = CommentRepo.findByCommentNum(num);
		CommentRepo.delete(comment);
	}
	
	
}
