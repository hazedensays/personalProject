package com.cm.personalProject.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cm.personalProject.domain.PageRequestDTO;
import com.cm.personalProject.domain.PageResultDTO;
import com.cm.personalProject.entity.Comments;
import com.cm.personalProject.entity.QComments;
import com.cm.personalProject.repository.CommentsRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.dml.UpdateClause;
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
	public PageResultDTO<Comments> selectList(PageRequestDTO requestDTO, int id){
	   QueryResults<Comments> result = queryFactory
	            .selectFrom(comments)
	            .where(comments.comment_delyn.eq('N').and(comments.board_id.eq(id)))
	            .orderBy(comments.comment_root.asc(),comments.comment_steps.asc())
	            .offset(requestDTO.getPageable().getOffset())
	            .limit(requestDTO.getPageable().getPageSize())
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
	
	@Override
	@Transactional
	public void stepUpdate(Comments entity) {
	   UpdateClause update = queryFactory.update(comments)
	          .set(comments.comment_steps, comments.comment_steps.add(1))
	          .where(comments.comment_root.eq(entity.getComment_root())
	          .and(comments.comment_steps.goe(entity.getComment_steps()))
	          .and(comments.comment_id.ne(entity.getComment_id()))
	          );

	   update.execute();
	}


}
