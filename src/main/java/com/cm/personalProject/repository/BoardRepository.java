package com.cm.personalProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cm.personalProject.entity.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;

public interface BoardRepository extends JpaRepository<Board, Integer>{
	
}
