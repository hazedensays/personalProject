package com.cm.personalProject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int board_id;
	
	@Column(nullable=false)
	private String useremail;
	
	@Column(nullable=false)
	private String board_regdate;
	
	@Column(nullable=false)
	private String board_title;
	
	@Column(nullable=false)
	private String board_content;
	
	private String board_moddate;
	private String board_deldate;
	
	@Column(nullable=false)
	private char board_delyn;
	
	@Column(nullable=false)
	@ColumnDefault("0")
	private int board_likes;
	
	@Column(nullable=false)
	@ColumnDefault("0")
	private int board_views;
}
