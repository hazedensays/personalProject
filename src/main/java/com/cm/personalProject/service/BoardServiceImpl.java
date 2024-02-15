package com.cm.personalProject.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cm.personalProject.domain.PageRequestDTO;
import com.cm.personalProject.domain.PageResultDTO;
import com.cm.personalProject.entity.Board;
import com.cm.personalProject.entity.QBoard;
import com.cm.personalProject.repository.BoardRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

	private final QBoard board = QBoard.board;
	private final BoardRepository repository;
	private final JPAQueryFactory queryFactory;

	@Override
	public PageResultDTO<Board> selectList(PageRequestDTO requestDTO, String searchType, String keyword) {
		BooleanExpression searchCondition = getSearchCondition(searchType, keyword);

		QueryResults<Board> result = queryFactory.selectFrom(board)
				.where(board.board_delyn.eq('N').and(searchCondition)).orderBy(board.board_id.desc())
				.offset(requestDTO.getPageable().getOffset()).limit(requestDTO.getPageable().getPageSize())
				.fetchResults();

		return new PageResultDTO<>(result, requestDTO.getPageable());
	}

	private BooleanExpression getSearchCondition(String searchType, String keyword) {
		if ("all".equals(searchType) || "".equals(keyword)) {
			return null;
		}

		switch (searchType) {
		case "useremail":
			return board.useremail.contains(keyword);
		case "board_title":
			return board.board_title.contains(keyword);
		case "board_content":
			return board.board_content.contains(keyword);
		default:
			return null;
		}
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