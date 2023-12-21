package com.semi.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.semi.dto.UsBoardDTO;

public interface UsBoardRepository extends JpaRepository<UsBoardDTO, Long>{

}
