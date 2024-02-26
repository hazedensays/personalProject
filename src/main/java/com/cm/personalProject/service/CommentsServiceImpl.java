package com.cm.personalProject.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cm.personalProject.domain.PageRequestDTO;
import com.cm.personalProject.domain.PageResultDTO;
import com.cm.personalProject.entity.Comments;
import com.cm.personalProject.entity.QComments;
import com.cm.personalProject.repository.CommentsRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommentsServiceImpl implements CommentsService {

	private final QComments comments = QComments.comments;
	private final CommentsRepository repository;
	private final JPAQueryFactory queryFactory;

	@Override
	public PageResultDTO<Comments> selectList(PageRequestDTO requestDTO, int board_id) {
		QueryResults<Comments> result = queryFactory.selectFrom(comments)
				.where(comments.comment_delyn.eq('N').and(comments.board_id.eq(board_id)))
				.offset(requestDTO.getPageable().getOffset()).limit(requestDTO.getPageable().getPageSize())
				.fetchResults();

		return new PageResultDTO<>(result, requestDTO.getPageable());
	}

	@Override
	public Comments selectDetail(int comment_id) {
		Optional<Comments> result = repository.findById(comment_id);

		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}

	@Override
	public int save(Comments entity) {
		repository.save(entity);
		return entity.getComment_id();
	}

	@Override
	public int delete(int comment_id) {
		repository.deleteById(comment_id);
		return comment_id;
	}

}
