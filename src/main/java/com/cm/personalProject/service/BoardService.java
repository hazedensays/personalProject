package com.cm.personalProject.service;

import java.util.List;

import com.cm.personalProject.domain.PageRequestDTO;
import com.cm.personalProject.domain.PageResultDTO;
import com.cm.personalProject.entity.Board;
import com.cm.personalProject.entity.Comments;

public interface BoardService {
	
	public PageResultDTO<Board> selectList(PageRequestDTO requestDTO, String searchType, String keyword);
	
	public Board selectDetail(int board_id);
	
	public int save(Board entity);
	
	public int delete(int board_id);

}
