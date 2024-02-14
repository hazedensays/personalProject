package com.cm.personalProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cm.personalProject.entity.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {

}
