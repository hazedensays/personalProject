package com.cm.personalProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cm.personalProject.entity.Board;
import com.cm.personalProject.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardService {
	
	private final BoardRepository repository;

	public List<Board> selectList() {
		return repository.findAll();
	}
	
	public Board selectDetail(int board_id) {
		Optional<Board> result = repository.findById(board_id);

		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}
	
	public int save(Board entity) {
		repository.save(entity);
		return entity.getBoard_id();
	}
	
	public int delete(int board_id) {
		repository.deleteById(board_id);
		return board_id;
	}
	
}