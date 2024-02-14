package com.cm.personalProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cm.personalProject.entity.Board;
import com.cm.personalProject.repository.BoardRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.cm.personalProject.entity.QBoard.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {
	
	private final BoardRepository repository;
	private final JPAQueryFactory queryFactory;

	@Override
	public List<Board> selectList() {
	    return queryFactory.selectFrom(board)
	            .where(board.board_delyn.eq('N'))
	            .orderBy(board.board_id.desc())
	            .fetch();
	}
	
	@Override
	public Board selectDetail(int board_id) {
		Optional<Board> result = repository.findById(board_id);

		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}
	
	@Override
	public int save(Board entity) {
		repository.save(entity);
		return entity.getBoard_id();
	}
	
	@Override
	public int delete(int board_id) {
		repository.deleteById(board_id);
		return board_id;
	}
	
}