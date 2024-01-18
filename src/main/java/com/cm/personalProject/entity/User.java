package com.cm.personalProject.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cm.personalProject.domain.OauthId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(OauthId.class)
public class User implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	private String oauthtype;

	@Id
	private String oauthtoken;

	private String username;
	private String useremail;
}
