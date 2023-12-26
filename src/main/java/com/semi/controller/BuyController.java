package com.semi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semi.dto.BuyDTO;
import com.semi.dto.BuyProducts;
import com.semi.dto.ChBoard;
import com.semi.dto.MemberDTO;
import com.semi.dto.ProductDTO;
import com.semi.service.BuyService;
import com.semi.service.CartService;
import com.semi.service.ChBoardService;
import com.semi.service.MemberService;
import com.semi.service.ProductService;

@SessionAttributes("user")
@Controller
public class BuyController {

	@Autowired
	private ProductService productService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private BuyService buyService;

	@Autowired
	private CartService cartService;

	@Autowired
	private ChBoardService chService;

	@ModelAttribute("user")
	public MemberDTO setUser() {
		return new MemberDTO();
	}

	@GetMapping("/buyPage")
	public String buyPage(@ModelAttribute("user") MemberDTO user, Model model,
			@RequestParam("productCount2") int productCount2, @RequestParam("seq1") int seq1) {
		String id = user.getId();
		System.out.println("=================================================");
		System.out.println(id);
		System.out.println(seq1);
		System.out.println(productCount2);
		if (user.getId() == null) {
			model.addAttribute("message", "로그인이 필요한 서비스입니다.");
			model.addAttribute("searchUrl", "/login");
			return "/qaBoard/message";
		}
		user = memberService.checkMember(id);
		ProductDTO pro = productService.getProduct(seq1);
		pro.setProductCount(productCount2);
		model.addAttribute("product", pro);
		model.addAttribute("member", user);
		return "/buy/buyPage";
	}

	@GetMapping("/orderform")
	public String orderform(HttpSession session, Model model, @RequestParam("seq1") int seq,
			@RequestParam("productCount2") int productCount2, @RequestParam("totalPrcTxt2") int productPrice) {
		MemberDTO dto = (MemberDTO) session.getAttribute("user");
		if (dto.getId() == null) {
			return "/user/login";
		}
		
		String phone = dto.getPhone();
		if (phone.equals("--")) {
			model.addAttribute("num1", null);
			model.addAttribute("num2", null);
			model.addAttribute("num3", null);
			System.out.println("확인용");
		} else {
			String num[] = phone.split("-");
			model.addAttribute("num1", num[0]);
			model.addAttribute("num2", num[1]);
			model.addAttribute("num3", num[2]);
			System.out.println("확인2");
			System.out.println(num[0]);
			System.out.println(num[1]);
			System.out.println(num[2]);
		}
		String addr = dto.getAddr();
		if (addr.equals("//")) {
			model.addAttribute("addr1", null);
			model.addAttribute("addr2", null);
			model.addAttribute("addr3", null);
		} else {
			String addr0[] = addr.split("/");
			model.addAttribute("addr1", addr0[0]);
			model.addAttribute("addr2", addr0[1]);
			model.addAttribute("addr3", addr0[2]);
		}
		System.out.println("===========================================================");
		System.out.println(productPrice);
		model.addAttribute("seq", seq);
		model.addAttribute("productCount", productCount2);
		model.addAttribute("productPrice", productPrice);

		return "buy/orderForm";
	}

	@GetMapping("/cartOrderForm")
	public String cartOrderForm(@ModelAttribute("user") MemberDTO user, @RequestParam("data") String products, HttpSession session, Model model)
			throws JsonMappingException, JsonProcessingException {
		MemberDTO dto = (MemberDTO) session.getAttribute("user");
		if (dto.getId() == null) {
			return "/user/login";
		}
		System.out.println(".....!@!" + products);

		// 이다음 구매페이지에서 사용하기 위해 장바구니 정보를 세션에 올려둠
		session.setAttribute("buyProducts", products);


		String phone = dto.getPhone();
		if (phone.equals("--")) {
			model.addAttribute("num1", null);
			model.addAttribute("num2", null);
			model.addAttribute("num3", null);
			System.out.println("확인용");
		} else {
			String num[] = phone.split("-");
			model.addAttribute("num1", num[0]);
			model.addAttribute("num2", num[1]);
			model.addAttribute("num3", num[2]);
			System.out.println("확인2");
			System.out.println(num[0]);
			System.out.println(num[1]);
			System.out.println(num[2]);
		}
		String addr = dto.getAddr();
		if (addr.equals("//")) {
			model.addAttribute("addr1", null);
			model.addAttribute("addr2", null);
			model.addAttribute("addr3", null);
		} else {
			String addr0[] = addr.split("/");
			model.addAttribute("addr1", addr0[0]);
			model.addAttribute("addr2", addr0[1]);
			model.addAttribute("addr3", addr0[2]);
		}

		return "buy/cartOrderForm";
	}

	@GetMapping("/complete")
	public String complete(BuyDTO dto, Model model, ProductDTO prdto, @ModelAttribute("user") MemberDTO user) {
		dto.setId(user.getId());
		System.out.println(dto.getProductCode());
		System.out.println(dto.getId());
		model.addAttribute("complete", dto);
		buyService.complete(dto, prdto);
		return "buy/Complete";
	}

	@GetMapping("/completeCart")
	public String completeCart(HttpSession session, Model model) throws JsonMappingException, JsonProcessingException {
		// cartpay에서 올려둔 구매정보 가져옴
		MemberDTO dto = (MemberDTO) session.getAttribute("buyUserInfo");
		// 다시 장바구니 정보 가져옴
		String products = (String) session.getAttribute("buyProducts");
		if (products == null) {
			System.out.println("정보를 가져오지 못함");
			return null;
		}
		// String 형식의 json데이터를 내가 지정한 데이터형식으로 매핑해줌
		ObjectMapper objectMapper = new ObjectMapper();
		List<BuyProducts> productLists = objectMapper.readValue(products, new TypeReference<List<BuyProducts>>() {
		});

		// Buy테이블에 맞게 해주기위해 생성
		List<BuyDTO> buyLists = new ArrayList<BuyDTO>();

		// 구매내역에 추가
		for (BuyProducts product : productLists) {
			BuyDTO buyDto = new BuyDTO();
			buyDto.setId(dto.getId());
			buyDto.setName(dto.getName());
			buyDto.setPhone(dto.getPhone());
			buyDto.setAddr(dto.getAddr());
			buyDto.setProductCode(product.getBpCode());
			buyDto.setProductName(product.getBpName());
			buyDto.setProductCount(Integer.parseInt(product.getBpCount()));
			buyDto.setProductPrice(Integer.parseInt(product.getBpPrice()));

			buyLists.add(buyDto);
		}
		int totalPrice = 0;
		for (int i = 0; i < buyLists.size(); i++) {
			totalPrice += buyLists.get(i).getProductPrice();
		}
		ProductDTO prdto = new ProductDTO();
		buyService.complete(buyLists, prdto);

		// 장바구니에서 삭제
		for (BuyDTO buyDTO : buyLists) {
			cartService.deleteCart(buyDTO.getProductCode(), buyDTO.getId());
		}

		model.addAttribute("list", buyLists);
		model.addAttribute("count", buyLists.size());
		model.addAttribute("totalPrice", totalPrice);
		return "buy/Complete";
	}

	//
	@GetMapping("/deleteBuyBoard")
	public String buyDelete(int productSeq, Model model, ChBoard board, Long seq) {

		board = chService.chBoardView(seq);

		if (board.getReady().equals("승인 대기")) {
			buyService.buyDelete(productSeq);
			board.setReady("승인 완료");
			chService.save(board);

			model.addAttribute("message", "승인이 완료되었습니다.");
			model.addAttribute("searchUrl", "/chBoardList");

			return "/qaBoard/message";
		}

		model.addAttribute("message", "이미 승인된 물품입니다.");
		model.addAttribute("searchUrl", "/chBoardList");

		return "/qaBoard/message";
	}

	@GetMapping("/getBuyList")
	public String myBuyList(@ModelAttribute("user") MemberDTO user, Model model, BuyDTO buy,
			@PageableDefault(page = 0, size = 3, sort = "seq", direction = Sort.Direction.DESC) Pageable pageable) {

		if (user.getId() == null) {
			model.addAttribute("message", "로그인이 필요한 서비스입니다.");
			model.addAttribute("searchUrl", "/login");

			return "/qaBoard/message";
		}

		String id = user.getId();

		Page<BuyDTO> buyList = buyService.UserBuyList(buy, id, pageable);
		
		int nowPage = buyList.getPageable().getPageNumber() + 1;
		int blockSize = 5; // 페이징 블록의 크기

		int startBlock = (nowPage - 1) / blockSize * blockSize + 1;
		int endBlock = startBlock + blockSize - 1;

		int startPage = Math.max(startBlock, 1);
		int endPage = Math.min(endBlock, buyList.getTotalPages());

		model.addAttribute("buyList", buyList);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "/buy/UserBuyList";
	}

	@GetMapping("/cancelbuy")
	public String cancel(@ModelAttribute("user") MemberDTO user, int seq, ChBoard board, Model model) {

		BuyDTO dto = buyService.getBuy(seq);

		board = new ChBoard();
		board.setTitle("주문취소 신청");
		board.setType("취소");
		board.setId(user.getId());
		board.setWriter(user.getName());
		board.setProductName(dto.getProductName());
		board.setProductSeq(dto.getSeq());

		chService.save(board);

		model.addAttribute("message", "취소 신청이 완료되었습니다.");
		model.addAttribute("searchUrl", "/chMain");

		return "qaBoard/message";
	}

	// ~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
	// 매니저용 고객구매리스트
	@GetMapping("/getAllBuyList")
	public String usersBuyList(Model model) {

		List<BuyDTO> allBuyList = buyService.getAllBuyList();
		model.addAttribute("allBuyList", allBuyList);

		return "/manager/ManagerBuyList";
	}

}
