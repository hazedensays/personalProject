package com.cm.personalProject.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikesId implements Serializable {

	private static final long serialVersionUID = 1L;

	private int board_id;
	private String useremail;
}
