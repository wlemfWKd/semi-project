package com.semi.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.semi.dto.MemberDTO;

public interface MemberRepository extends JpaRepository<MemberDTO, String>{

	MemberDTO findUsernameByEmail(String email);

	MemberDTO findPasswordByIdAndEmail(String id, String email);
	
	MemberDTO findUseridById(String id);

	MemberDTO findUserkakaoByKakao(String kakao);
}
