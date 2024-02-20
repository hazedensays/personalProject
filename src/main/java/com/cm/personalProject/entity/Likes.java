package com.cm.personalProject.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="likes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Likes  implements Serializable {
	
	@Id
	private String useremail;
	
	@Id
	private int board_id;
	
}
