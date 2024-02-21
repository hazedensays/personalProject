package com.cm.personalProject.service;

import com.cm.personalProject.domain.LikesId;
import com.cm.personalProject.entity.Likes;

public interface LikesService {
	
    public Likes findById(LikesId id);
	
	public int save(Likes entity);
	
	public void delete(Likes existingLike);

}
