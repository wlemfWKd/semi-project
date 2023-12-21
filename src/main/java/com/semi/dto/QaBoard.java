package com.semi.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "QABOARD")
public class QaBoard {

	@Id
    @GeneratedValue
    private Long seq;
	
	private String id;
	
	@Column(updatable = false)
	private String writer;
    private String title;
    private String content;
    @Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date createDate;
	@Column(columnDefinition = "integer default 0")
	private int cnt;
}

