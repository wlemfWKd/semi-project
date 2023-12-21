package com.semi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.semi.dto.BuyDTO;
import com.semi.dto.ChBoard;
import com.semi.dto.ProductDTO;
import com.semi.persistence.BuyRepository;
import com.semi.persistence.ProductRepository;

@Service
public class BuyServiceImpl implements BuyService {
	@Autowired
	public ProductService ps;

	@Autowired
	public BuyRepository buyRepo;
	
	@Autowired
	public ProductRepository productRepo;
	
	public void complete(BuyDTO dto, ProductDTO prodto) {
		buyRepo.save(dto);
		prodto = productRepo.findByproductCode(dto.getProductCode());
		prodto.setCnt(prodto.getCnt()+1);
		productRepo.save(prodto);
	}
	
	
	@Override
	public List<BuyDTO> getOrders(BuyDTO orders){
		return (List<BuyDTO>) buyRepo.findAll();
	}

	@Override
	public void complete(List<BuyDTO> dto, ProductDTO prdto) {
		for (BuyDTO buyDTO : dto) {
			ProductDTO dto1 = new ProductDTO();
			
			dto1 = productRepo.findByproductCode(buyDTO.getProductCode());
			dto1.setCnt(dto1.getCnt()+1);
			productRepo.save(dto1);
			buyRepo.save(buyDTO);
			
		}
		
	}

	@Override
	public Page<BuyDTO> UserBuyList(BuyDTO buy, String id, Pageable pageable) {
		return (Page<BuyDTO>) buyRepo.findById(id, pageable);
	}

	@Override
	public List<BuyDTO> getBuyList(String id) {
		return buyRepo.findById(id);
	}


	@Override
	public void buyDelete(int seq) {
		buyRepo.deleteById(seq);
		
	}


	@Override
	public void buyCancel(Long seq) {
		buyRepo.deleteById(seq);
		
	}


	@Override
	public BuyDTO getBuy(int seq) {
		return buyRepo.findById(seq).get();
	}
	
	
	//~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
	@Override
	public List<BuyDTO> getAllBuyList(){
		return buyRepo.findAll();
		
	
	

	}
	
	
}

















