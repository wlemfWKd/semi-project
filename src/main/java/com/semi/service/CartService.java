package com.semi.service;

import java.util.List;

import com.semi.dto.CartDTO;

public interface CartService {

	public List<CartDTO> getCartList(String id);
	public void insertCart(int seq, int productCount, String userId);
	public void deleteCart(String cartCode, String userId);
	public void minusCart(String cartCode, String userId);
	public void plusCart(String cartCode, String userId);
}
