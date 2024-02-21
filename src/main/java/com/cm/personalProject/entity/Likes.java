package com.cm.personalProject.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.cm.personalProject.domain.LikesId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="likes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(LikesId.class)
public class Likes implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private int board_id;
	
	@Id
	private String useremail;
}
