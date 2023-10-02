package com.library.authorservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.authorservice.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
