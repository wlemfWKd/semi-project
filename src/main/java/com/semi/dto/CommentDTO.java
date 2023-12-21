package com.semi.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "commento")
public class CommentDTO {
	
	@Id
	@GeneratedValue
	@Column(nullable=false)
	private Long commentNum;
	private Long qaseq;
	private String writer;
	@Column(length=100000)
	private String commento;
	@Column(name = "create_date", insertable = false, updatable = false, columnDefinition = "DATE DEFAULT SYSDATE")
	private Date createDate;
	
	public Long getCommentNum() {
	    return commentNum != null ? commentNum : 0L; // 또는 다른 기본값으로 대체
	}
}
