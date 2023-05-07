package com.Damera.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Setter
@Getter
public class CommentsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer commentId;
	private String guestName;
	private String guestEmail;
	
	@Lob
	private String comment;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "blog_id")
	private BlogsEntity blog;

}
