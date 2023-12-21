package com.semi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.semi.dto.CommentDTO;

public interface CommentService {

	public void save(CommentDTO dto);
	public Page<CommentDTO> commentList(CommentDTO dto, Long seq, Pageable pageable);
	public void deleteComment(Long qaseq);
	public void delete(Long num);
}