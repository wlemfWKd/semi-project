package com.semi.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "USBOARD")
public class UsBoardDTO {

	@Id
	@GeneratedValue
	private Long seq;
	
	@Column(updatable = false)
	private String usWriter;
	private String usTitle;
	private String usContent;
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date createDate;
	@Column(columnDefinition = "number default 0")
	private int usCnt;
	
	
}
