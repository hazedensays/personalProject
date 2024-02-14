package com.cm.personalProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cm.personalProject.entity.Comments;
import com.cm.personalProject.repository.CommentsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommentsService {
	
	private final CommentsRepository repository;
	
	public List<Comments> selectList() {
		return repository.findAll();
	}
	
	public Comments selectDetail(int comment_id) {
		Optional<Comments> result = repository.findById(comment_id);
		
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}
	
	public int save(Comments entity) {
		repository.save(entity);
		return entity.getComment_id();
	}

	public int delete(int comment_id) {
		repository.deleteById(comment_id);
		return comment_id;
	}

}
