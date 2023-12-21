package com.semi.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.semi.dto.BuyDTO;
import com.semi.dto.ChBoard;


@Repository
public interface BuyRepository extends JpaRepository<BuyDTO, Integer>{

	public List<BuyDTO> findById(String Id);

	public void deleteById(Long seq);

	public List<BuyDTO> findAllById(String id);
	
	Page<BuyDTO> findById(String id, Pageable pageable);
	
	//~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
	
	
}
