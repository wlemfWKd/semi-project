package com.semi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.semi.dto.BuyDTO;
import com.semi.dto.CartDTO;
import com.semi.dto.ChBoard;
import com.semi.dto.CommentDTO;
import com.semi.dto.MemberDTO;
import com.semi.dto.QaBoard;
import com.semi.dto.ReviewDTO;
import com.semi.persistence.BuyRepository;
import com.semi.persistence.CartRepository;
import com.semi.persistence.ChBoardRepository;
import com.semi.persistence.CommentRepository;
import com.semi.persistence.MemberRepository;
import com.semi.persistence.QaBoardRepository;
import com.semi.persistence.ReviewRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepo;
	@Autowired
	private BuyRepository buyRepo;
	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private ChBoardRepository chBoardRepo;
	@Autowired
	private CommentRepository commentRepo;
	@Autowired
	private QaBoardRepository qaBoardRepo;
	@Autowired
	private ReviewRepository reviewRepo;
	

//	-------------------------------------------------------------------------------------------

	@Override
	public void insertMember(MemberDTO dto) {
		memberRepo.save(dto);
	}

//	-------------------------------------------------------------------------------------------

	@Override
	public MemberDTO getMember(MemberDTO dto) {
		Optional<MemberDTO> user = memberRepo.findById(dto.getId());

		if (user.isPresent())
			return user.get();
		else
			return null;
	}

//	-------------------------------------------------------------------------------------------

	@Override
	public List<MemberDTO> userList() {
		List<MemberDTO> userList = (List<MemberDTO>) memberRepo.findAll();
		return userList;
	}

//	-------------------------------------------------------------------------------------------

	@Override
	public MemberDTO searchId(String email, String name) {
		List<MemberDTO> userList = (List<MemberDTO>) memberRepo.findAll();
	    
	    for (MemberDTO memberDTO : userList) {
	        if (memberDTO.getName().equals(name)) {
	        	if(memberDTO.getEmail().equals(email)) {
	        		return memberDTO;
	        	}

	        }
	    }

	    return null; // 이름이 일치하는 멤버가 없는 경우에만 null 반환
	}

//	-------------------------------------------------------------------------------------------

	@Override
	public String findPassword(MemberDTO dto) {
		MemberDTO dto1 = memberRepo.findPasswordByIdAndEmail(dto.getId(), dto.getEmail());
		if (dto.getId().equals(dto1.getId()) && dto.getEmail().equals(dto1.getEmail())) {
			return dto.getId();
		} else {
			return null;
		}
	}
	
	@Override
	public void updatePassword(String id, String password) {
		MemberDTO dto = memberRepo.findUseridById(id);
		dto.setPassword(password);
		memberRepo.save(dto);
	}
	

//	-------------------------------------------------------------------------------------------

	@Override
	public MemberDTO checkMember(String id) {
		return memberRepo.findUseridById(id);
	}

	// ~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-

	// 회원정보 수정
	
	@Override
	public void deleteUser(MemberDTO user) {
		memberRepo.deleteById(user.getId());
		List<BuyDTO> buydtoList = new ArrayList<>();
		buyRepo.findAllById(user.getId()).forEach(buydtoList::add);
		if (!buydtoList.isEmpty()) {
		    for (BuyDTO buydto : buydtoList) {
		        buyRepo.delete(buydto);
		    }
		}
		
		List<CartDTO> cartdtoList = new ArrayList<>();
		cartRepo.findAllByuserId(user.getId()).forEach(cartdtoList::add);
		if (!cartdtoList.isEmpty()) {
		    for (CartDTO cartdto : cartdtoList) {
		        cartRepo.delete(cartdto);
		    }
		}
		
		List<ChBoard> chdtoList = new ArrayList<>();
		chBoardRepo.findAllById(user.getId()).forEach(chdtoList::add);
		if (!chdtoList.isEmpty()) {
		    for (ChBoard chdto : chdtoList) {
		        chBoardRepo.delete(chdto);
		    }
		}
		
		List<CommentDTO> commentdtoList = new ArrayList<>();
		commentRepo.findAllByWriter(user.getName()).forEach(commentdtoList::add);
		if (!commentdtoList.isEmpty()) {
			for (CommentDTO commentdto : commentdtoList) {
				commentRepo.delete(commentdto);
			}
		}
		
		List<QaBoard> qadtoList = new ArrayList<>();
		qaBoardRepo.findAllById(user.getId()).forEach(qadtoList::add);
		if (!qadtoList.isEmpty()) {
			for (QaBoard qadto : qadtoList) {
				qaBoardRepo.delete(qadto);
			}
		}
		
		List<ReviewDTO> reviewdtoList = new ArrayList<>();
		reviewRepo.findAllByreviewid(user.getId()).forEach(reviewdtoList::add);
		if (!reviewdtoList.isEmpty()) {
			for (ReviewDTO reviewdto : reviewdtoList) {
				reviewRepo.delete(reviewdto);
			}
		}
		
	}

	@Override
	public void updateUser(MemberDTO dto) {
		Optional<MemberDTO> optionalMember = memberRepo.findById(dto.getId());
	       
	       if(optionalMember.isPresent()){
	           MemberDTO memberDTO = optionalMember.get();
	           memberDTO.setPassword(dto.getPassword());
	           memberDTO.setEmail(dto.getEmail());
	           memberDTO.setPhone(dto.getPhone());
	           memberDTO.setAddr(dto.getAddr());
	           
	           memberRepo.save(memberDTO);
	       }
	      
	}
	
	public MemberDTO checkKakao(String kakao) {
		return memberRepo.findUserkakaoByKakao(kakao);
	}

//	----------------------------------------------------------------

	// 회원탈퇴
	@Override
	public void out(String id, String name) {
		memberRepo.deleteById(id);
		List<BuyDTO> buydtoList = new ArrayList<>();
		buyRepo.findAllById(id).forEach(buydtoList::add);
		if (!buydtoList.isEmpty()) {
		    for (BuyDTO buydto : buydtoList) {
		        buyRepo.delete(buydto);
		    }
		}
		
		List<CartDTO> cartdtoList = new ArrayList<>();
		cartRepo.findAllByuserId(id).forEach(cartdtoList::add);
		if (!cartdtoList.isEmpty()) {
		    for (CartDTO cartdto : cartdtoList) {
		        cartRepo.delete(cartdto);
		    }
		}
		
		List<ChBoard> chdtoList = new ArrayList<>();
		chBoardRepo.findAllById(id).forEach(chdtoList::add);
		if (!chdtoList.isEmpty()) {
		    for (ChBoard chdto : chdtoList) {
		        chBoardRepo.delete(chdto);
		    }
		}
		
		List<CommentDTO> commentdtoList = new ArrayList<>();
		commentRepo.findAllByWriter(name).forEach(commentdtoList::add);
		if (!commentdtoList.isEmpty()) {
			for (CommentDTO commentdto : commentdtoList) {
				commentRepo.delete(commentdto);
			}
		}
		
		List<QaBoard> qadtoList = new ArrayList<>();
		qaBoardRepo.findAllById(id).forEach(qadtoList::add);
		if (!qadtoList.isEmpty()) {
			for (QaBoard qadto : qadtoList) {
				qaBoardRepo.delete(qadto);
			}
		}
		
		List<ReviewDTO> reviewdtoList = new ArrayList<>();
		reviewRepo.findAllByreviewid(id).forEach(reviewdtoList::add);
		if (!reviewdtoList.isEmpty()) {
			for (ReviewDTO reviewdto : reviewdtoList) {
				reviewRepo.delete(reviewdto);
			}
		}
		
	}
	public Page<MemberDTO> userList(Pageable pageable){
		return memberRepo.findAll(pageable);
	}


}

	
	
	






















	
	
	
	
	
	
	