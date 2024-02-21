package com.cm.personalProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cm.personalProject.domain.LikesId;
import com.cm.personalProject.entity.Likes;

public interface LikesRepository extends JpaRepository<Likes, LikesId> {

}
