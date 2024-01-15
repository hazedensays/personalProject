package com.cm.personalProject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private int userid;

	   private String username;
	   private String userbirthday;
	   private String userphone;
	   private String oauthtype;
	   private String oauthtoken;
	   private String useremail;
}

