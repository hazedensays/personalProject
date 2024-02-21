package com.cm.personalProject.service;

import org.springframework.stereotype.Service;

import com.cm.personalProject.domain.LikesId;
import com.cm.personalProject.entity.Likes;
import com.cm.personalProject.repository.LikesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class LikesServiceImpl implements LikesService {

	private final LikesRepository repository;
	
	@Override
    public Likes findById(LikesId id) {
        return repository.findById(id).orElse(null);
    }

	@Override
	public int save(Likes entity) {
		repository.save(entity);
		return entity.getBoard_id();
	}

	@Override
	public void delete(Likes existingLike) {
		repository.delete(existingLike);
	}

}