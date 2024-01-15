package com.cm.personalProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cm.personalProject.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}

