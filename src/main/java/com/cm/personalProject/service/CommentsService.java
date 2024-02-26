package com.cm.personalProject.service;

import java.util.List;

import com.cm.personalProject.domain.PageRequestDTO;
import com.cm.personalProject.domain.PageResultDTO;
import com.cm.personalProject.entity.Comments;

public interface CommentsService {
	
	public PageResultDTO<Comments> selectList(PageRequestDTO requestDTO, int board_id);
	
	public Comments selectDetail(int comment_id);
	
	public int save(Comments entity);

	public int delete(int comment_id);

}
