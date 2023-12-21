package com.semi.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.semi.dto.CheckDTO;
import com.semi.dto.ProductDTO;
import com.semi.persistence.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Override
	public List<ProductDTO> getProductList(ProductDTO product) {
		return (List<ProductDTO>) productRepo.findAll();
	}
	
	@Override
	public List<ProductDTO> getProductList2() {
		
		return productRepo.findAll(Sort.by(Sort.Order.desc("cnt"))).stream().limit(4).collect(Collectors.toList());
	}

	@Override
	public void fileProcess(MultipartHttpServletRequest mul) {
		ProductDTO dto = new ProductDTO();
		dto.setProductCode(mul.getParameter("productCode"));
		dto.setProductName(mul.getParameter("productName"));
		String productPriceString = mul.getParameter("productPrice");
		if (productPriceString != null && !productPriceString.isEmpty()) {
			int productPrice = Integer.parseInt(productPriceString);
			dto.setProductPrice(productPrice);
		}
		dto.setProductContent(mul.getParameter("productContent"));
		dto.setProductGender(mul.getParameter("productGender"));
		// html에 있는 name="productImage"
		MultipartFile file = mul.getFile("productImage");
		if (file.getSize() != 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			Calendar calendar = Calendar.getInstance();
			String sysFileName = sdf.format(calendar.getTime());
			sysFileName += file.getOriginalFilename();
			dto.setProductImage(sysFileName);

			String absolutePath = new File("").getAbsolutePath() + "//";

			File saveFile = new File(absolutePath + IMAGE + sysFileName);
			try {
				file.transferTo(saveFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			dto.setProductImage("non");
		}

		productRepo.save(dto);
	}

	@Override
	public void insertProduct(ProductDTO product) {
		productRepo.save(product);
	}

	@Override
	public ProductDTO getProduct(ProductDTO product) {
		return productRepo.findById(product.getSeq()).get();
	}

	@Override
	public void updateProduct(MultipartHttpServletRequest mul) {
		int seq = Integer.parseInt(mul.getParameter("seq"));
		System.out.println("seq = " + seq);
		ProductDTO findProduct = productRepo.findById(seq).get();
		findProduct.setProductName(mul.getParameter("productName"));
		String productPriceString = mul.getParameter("productPrice");
		try {
			if (productPriceString != null && !productPriceString.isEmpty()) {
				int productPrice = Integer.parseInt(productPriceString);
				findProduct.setProductPrice(productPrice);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		findProduct.setProductContent(mul.getParameter("productContent"));
		findProduct.setProductGender(mul.getParameter("productGender"));

		int findIndex = findProduct.getProductImage().lastIndexOf(".");
		System.out.println(findProduct.getProductImage());

		String saveFileName = findProduct.getProductImage().substring(findIndex);

		MultipartFile file = mul.getFile("productImage");

		if (!saveFileName.equals(file.getOriginalFilename()) && !file.getOriginalFilename().equals("")) {
			// 저장된 파일 이름이랑 수정된 파일 이름이 같지 않으면 다시 파일 업로드
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			Calendar calendar = Calendar.getInstance();
			String sysFileName = sdf.format(calendar.getTime());
			sysFileName += file.getOriginalFilename();

			findProduct.setProductImage(sysFileName);

			String absolutePath = new File("").getAbsolutePath() + "//";

			File saveFile = new File(absolutePath + IMAGE + sysFileName);
			try {
				file.transferTo(saveFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		productRepo.save(findProduct);

	}

	@Override
	public void deleteProduct(ProductDTO product) {
		productRepo.deleteById(product.getSeq());

	}

	@Override
	public ProductDTO check(CheckDTO dto, ProductDTO product) {
		 Random random = new Random();
		 int num = random.nextInt(50);
		List<ProductDTO> product1 = productRepo.findByproductGender("남성용");
		List<ProductDTO> product2 = productRepo.findByproductGender("여성용");
		List<ProductDTO> product3 = (List<ProductDTO>) productRepo.findAll();
		if(dto.getGender().contains("여성")) {
			if(dto.getType().equals("지성")||dto.getType().equals("복합")) {
				if(dto.getTrouble().contains("여드릅")||dto.getTrouble().contains("흉터")) {
					if(dto.getDot().contains("약간")||dto.getDot().contains("많음")) {
						if(dto.getColor().contains("때때로")||dto.getColor().contains("항상")) {
							if(dto.getOlder().contains("늙어")) {
								return product2.get(num);
							}
						}
					}
				}else if(dto.getTrouble().contains("건조")) {
					if(dto.getDot().contains("약간")||dto.getDot().contains("많음")) {
						if(dto.getColor().contains("때때로")||dto.getColor().contains("항상")) {
							if(dto.getOlder().contains("늙어")) {
								return product2.get(num);
							}						
						}
					}
				}
				
				
			}else if(dto.getType().equals("건성")) {
				if(dto.getTrouble().contains("여드릅")||dto.getTrouble().contains("흉터")) {
					if(dto.getDot().contains("약간")||dto.getDot().contains("많음")) {
						if(dto.getColor().contains("때때로")||dto.getColor().contains("항상")) {
							if(dto.getOlder().contains("늙어")) {
								return product2.get(num);
							}
						}
					}
				}else if(dto.getTrouble().contains("건조")) {
					if(dto.getDot().contains("약간")||dto.getDot().contains("많음")) {
						if(dto.getColor().contains("때때로")||dto.getColor().contains("항상")) {
							if(dto.getOlder().contains("늙어")) {
								return product2.get(num);
							}
						}
					}
				}
			}
		} else if(dto.getGender().contains("남성")){
			if(dto.getType().equals("지성")||dto.getType().equals("복합")) {
				if(dto.getTrouble().contains("여드릅")||dto.getTrouble().contains("흉터")) {
					if(dto.getDot().contains("약간")||dto.getDot().contains("많음")) {
						if(dto.getColor().contains("때때로")||dto.getColor().contains("항상")) {
							if(dto.getOlder().contains("늙어")) {
								return product1.get(num);
							}
						}
					}
				}else if(dto.getTrouble().contains("건조")) {
					if(dto.getDot().contains("약간")||dto.getDot().contains("많음")) {
						if(dto.getColor().contains("때때로")||dto.getColor().contains("항상")) {
							if(dto.getOlder().contains("늙어")) {
								return product1.get(num);
							}						
						}
					}
				}
				
				
			}else if(dto.getType().equals("건성")) {
				if(dto.getTrouble().contains("여드릅")||dto.getTrouble().contains("흉터")) {
					if(dto.getDot().contains("약간")||dto.getDot().contains("많음")) {
						if(dto.getColor().contains("때때로")||dto.getColor().contains("항상")) {
							if(dto.getOlder().contains("늙어")) {
								return product1.get(num);
							}
						}
					}
				}else if(dto.getTrouble().contains("건조")) {
					if(dto.getDot().contains("약간")||dto.getDot().contains("많음")) {
						if(dto.getColor().contains("때때로")||dto.getColor().contains("항상")) {
							if(dto.getOlder().contains("늙어")) {
								return product1.get(num);
							}
						}
					}
				}
			}
		} 
		return product3.get(num);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public Page<ProductDTO> getProductList(Pageable pageable) {
		return productRepo.findAll(pageable);
	}

	@Override
	public ProductDTO getProduct(int seq) {
		return productRepo.findById(seq).get();
	}

	@Override
	public void minusProduct(String productCode) {
		ProductDTO product = productRepo.findByproductCode(productCode);
		if (product.getProductCount() > 1) {
			product.setProductCount(product.getProductCount() - 1);
			productRepo.save(product);
		}

	}

	@Override
	public void plusProduct(String productCode) {
		ProductDTO product = productRepo.findByproductCode(productCode);
		if (product.getProductCount() >= 1) {
			product.setProductCount(product.getProductCount() + 1);
			productRepo.save(product);
		}

	}

	//~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
	@Override
	public Page<ProductDTO> productList(String searchKeyword, Pageable pageable) {
		return productRepo.findByproductCodeContaining(searchKeyword, pageable);
	}	
	
	// 남성, 여성, 공용 구분 페이징
	
	@Override
	public Page<ProductDTO> getProductMaleList(Pageable pageable) {
	    return productRepo.findByProductGender("남성용", pageable);
	}
	@Override
	public Page<ProductDTO> getProductFemaleList(Pageable pageable) {
	    return productRepo.findByProductGender("여성용", pageable);
	}
	@Override
	public Page<ProductDTO> getProductproductCommon(Pageable pageable) {
	    return productRepo.findByProductGender("공용", pageable);
	}
	@Override
	public Page<ProductDTO> productManagerList(Pageable pageable) {
		return productRepo.findAll(pageable);
	}
	
	
	
	
	
	@Override
	public Page<ProductDTO> productListM(String searchKeyword, Pageable pageable) {
		return productRepo.findByproductCodeContaining(searchKeyword, pageable);
	}

	@Override
	public Page<ProductDTO> productListW(String searchKeyword, Pageable pageable) {
		return productRepo.findByproductCodeContaining(searchKeyword, pageable);
	}
	
	@Override
	public Page<ProductDTO> productListC(String searchKeyword, Pageable pageable) {
		return productRepo.findByproductCodeContaining(searchKeyword, pageable);
	}
	

}
