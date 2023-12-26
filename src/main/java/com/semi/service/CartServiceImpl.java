package com.semi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semi.dto.CartDTO;
import com.semi.dto.ProductDTO;
import com.semi.persistence.CartRepository;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ProductService ps;

	@Autowired
	private CartRepository cr;


	@Override
	public List<CartDTO> getCartList(String id) {
		return cr.findByUserId(id);
	}

	@Override
	public void insertCart(int seq, int productCount, String userId) {
		ProductDTO product = ps.getProduct(seq);
		product.setProductCount(productCount);
		System.out.println("상품 : " + product.getProductCode());
		System.out.println("user : " + userId);
		
	    CartDTO cart = cr.findByCartCodeAndUserId(product.getProductCode(), userId);
	    System.out.println("cart : " + cart);
		
	    if (cart != null) {
			System.out.println("중복되는데이터");
			cart.setCartCount(cart.getCartCount() + product.getProductCount());
		} else {
			cart = new CartDTO();
			cart.setSeq(product.getSeq());
			cart.setCartCode(product.getProductCode());
			cart.setCartName(product.getProductName());
			cart.setCartPrice(product.getProductPrice());
			cart.setCartImage(product.getProductImage());
			cart.setCartCount(product.getProductCount());
			cart.setUserId(userId);
		}
		cr.save(cart);
	}

	@Override
	@Transactional
	public void deleteCart(String cartCode, String userId) {
		CartDTO cart = new CartDTO();
		cart.setCartCode(cartCode);
		cart.setUserId(userId);
		cr.deleteByCartCodeAndUserId(cartCode, userId);
	}

	@Override
	public void minusCart(String cartCode, String userId) {
		CartDTO cart = cr.findByCartCodeAndUserId(cartCode, userId);
		if (cart.getCartCount() > 1) {
			cart.setCartCount(cart.getCartCount() - 1);
			cr.save(cart);
		}

	}

	@Override
	public void plusCart(String cartCode, String userId) {
		CartDTO cart = cr.findByCartCodeAndUserId(cartCode, userId);
		if (cart.getCartCount() >= 1) {
			cart.setCartCount(cart.getCartCount() + 1);
			cr.save(cart);
		}

	}


}
