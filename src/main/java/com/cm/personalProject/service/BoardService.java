package com.cm.personalProject.service;

import java.util.List;

import com.cm.personalProject.entity.Board;

public interface BoardService {
	
	public List<Board> selectList();
	
	public Board selectDetail(int board_id);
	
	public int save(Board entity);
	
	public int delete(int board_id);

}
