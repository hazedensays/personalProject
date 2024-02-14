package com.cm.personalProject.service;

import java.util.List;

import com.cm.personalProject.entity.Comments;

public interface CommentsService {
	
	public List<Comments> selectList();
	
	public Comments selectDetail(int comment_id);
	
	public int save(Comments entity);

	public int delete(int comment_id);

}
