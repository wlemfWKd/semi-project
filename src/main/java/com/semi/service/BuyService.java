package com.semi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.semi.dto.BuyDTO;
import com.semi.dto.ProductDTO;

@Service
public interface BuyService {

	public void complete(BuyDTO dto, ProductDTO prdto);

	public void complete(List<BuyDTO> dto, ProductDTO prdto);
	public List<BuyDTO> getOrders(BuyDTO orders);
	
	//
	
	public List<BuyDTO> getBuyList(String id);
	public void buyDelete(int seq);
	public void buyCancel(Long seq);
	public BuyDTO getBuy(int seq);
	
	//~-~-~-~-~-~-~-~-~-~-~-~-~-
	List<BuyDTO> getAllBuyList();

	Page<BuyDTO> UserBuyList(BuyDTO buy, String id, Pageable pageable);
	
}
