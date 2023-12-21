package com.semi.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.semi.dto.ProductDTO;

public interface ProductRepository extends CrudRepository<ProductDTO, Integer>{

	ProductDTO findByproductCode(String productCode);

	Page<ProductDTO> findAll(Pageable pageable);

	// ~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
	// 남성, 여성, 공용 구분 페이징

	Page<ProductDTO> findByProductGender(String string, Pageable pageable);

	Page<ProductDTO> findByproductCodeContaining(String searchKeyword, Pageable pageable);

	List<ProductDTO> findAll(Sort by);

	List<ProductDTO> findByproductGender(String string);


}
