package com.library.authorservice.service;

import java.util.List;

import com.library.authorservice.entity.Author;

public interface AuthorService {

	List<Author> getAllAuthors();

	Author getAuthorById(long id);

	Author createAuthor(Author author);

	Author updateAuthor(long id, Author author);

	void deleteAuthor(long id);

}
