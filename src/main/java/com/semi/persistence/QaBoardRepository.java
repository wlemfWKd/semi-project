package com.semi.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.semi.dto.MemberDTO;
import com.semi.dto.QaBoard;

public interface QaBoardRepository extends JpaRepository<QaBoard, Long> {
	Page<QaBoard> findByTitleContaining(String searchKeyword, Pageable pageable);

	Page<QaBoard> findByWriterContaining(String searchKeyword, Pageable pageable);

	Page<QaBoard> findByContentContaining(String searchKeyword, Pageable pageable);

	List<QaBoard> findAllById(String id);

}
