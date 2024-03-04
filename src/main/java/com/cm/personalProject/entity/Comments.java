package com.cm.personalProject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int comment_id;
	
	private int board_id;
	
	@Column(nullable=false)
	private String useremail;
	
	@Column(nullable=false)
	private int comment_root;
	
	@Column(nullable=false)
	private String comment_regdate;
	
	@Column(nullable=false)
	private String comment_content;
	
	private String comment_moddate;
	private String comment_deldate;
	
	@Column(nullable=false)
	@ColumnDefault("'N'")
	private char comment_delyn;
	
	@Column(nullable=false)
	private int comment_steps;
	
	@Column(nullable=false)
	private int comment_indent;
	
}
