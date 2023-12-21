package com.semi.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.semi.dto.ChBoard;
import com.semi.dto.MemberDTO;

public interface ChBoardRepository extends JpaRepository<ChBoard, Long>{
	Page<ChBoard> findByTitleContaining(String searchKeyword, Pageable pageable);

	List<ChBoard> findById(String id);
	
	Page<ChBoard> findById(String id, Pageable pageable);

	List<ChBoard> findAllById(String id);
}
