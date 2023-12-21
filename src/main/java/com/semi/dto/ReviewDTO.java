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
@Table(name = "review")
public class ReviewDTO {
   
   
   @Id
   @GeneratedValue
   private Long seq;
   private String reviewContent;
   private String reviewid;
   private String reviewImage;
   private String rating;
   private String product;
   @Column(updatable = false)
   private String reviewWriter;
   @Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
   private Date reviewDate;
   

}
















