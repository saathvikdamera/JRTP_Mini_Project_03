package com.Damera.entity;

import java.time.LocalDate;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "blogs")
@Setter
@Getter
public class BlogsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer blogId;
	private String blogTitle;
	private String shortDesp;
	private String status;
	
	@Lob
	private String content;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserDtlsEntity user;
	
	@OneToMany(cascade = {CascadeType.REMOVE ,CascadeType.PERSIST},fetch = FetchType.EAGER,mappedBy = "blog")
	private List<CommentsEntity> lstOfComments;
	
}
