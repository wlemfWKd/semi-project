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
@Table(name = "CHBOARD")
public class ChBoard {

	@Id
    @GeneratedValue
    private Long seq;
	
	private String id;
	
	@Column(updatable = false)
	private String writer;
	private String type;
    private String title;
    private String content;
    private String productName;
    private int productSeq;
    private String filename;
    private String filepath;
    private String ready = "승인 대기";    
    @Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date createDate;
}
