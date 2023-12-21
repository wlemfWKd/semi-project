package com.semi.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import com.semi.dto.BuyDTO;
import com.semi.dto.CartDTO;
import com.semi.dto.MemberDTO;

@EnableJpaRepositories
public interface CartRepository extends CrudRepository<CartDTO, String>{

	public CartDTO findByCartCode(String cartCode);
	public List<CartDTO> findByUserId(String userId);
	public CartDTO findByCartCodeAndUserId(String productCode, String userId);
	public void deleteByCartCodeAndUserId(String cartCode, String userId);
	public List<CartDTO> findAllByuserId(String id);
}
