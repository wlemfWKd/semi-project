package com.semi.dto;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "MEMBERS")
public class MemberDTO{

	@Id
	@Column(updatable = false)
	private String id;			// 아이디
	private String password;	// 비밀번호
	private String name;		// 이름
	private String email;		// 이메일
	private String phone;		// 폰번호
	private String addr;		// 주소
	private String kakao; 		// 카카오 ID
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date createDate;	// 생성날짜

	
}
