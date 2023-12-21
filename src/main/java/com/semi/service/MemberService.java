package com.semi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.semi.dto.MemberDTO;

public interface MemberService {
	
	// 회원 추가
	public void insertMember(MemberDTO dto);
	// 로그인
	public MemberDTO getMember(MemberDTO dto);
	// 회원 전체 
	public List<MemberDTO> userList();
	// 회원 페이징 
	public Page<MemberDTO> userList(Pageable pageable);
	// 아이디 찾기
	public MemberDTO searchId(String email, String name);
	// 비밀번호 찾기
	public String findPassword(MemberDTO dto);
	// 비밀번호 수정
	public void updatePassword(String id, String password);
	// 아이디 중복체크
	public MemberDTO checkMember(String id);
	
	//~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
	// 회원정보수정
	public void deleteUser(MemberDTO user);
	public void updateUser(MemberDTO user);

	//--------------------------------------------------
	//카카오확인
	public MemberDTO checkKakao(String kakao);
	//---------------------------------------------------
	// 회원탈퇴
	public void out(String id, String name);
}