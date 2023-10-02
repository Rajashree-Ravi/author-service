package com.library.authorservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.authorservice.entity.Author;
import com.library.authorservice.repository.AuthorRepository;
import com.library.authorservice.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	AuthorRepository authorRepository;

	@Override
	public List<Author> getAllAuthors() {
		return authorRepository.findAll();
	}

	@Override
	public Author getAuthorById(long id) {
		Optional<Author> author = authorRepository.findById(id);
		return (author.isPresent() ? author.get() : null);
	}

	@Override
	public Author createAuthor(Author author) {
		return authorRepository.save(author);
	}

	@Override
	public Author updateAuthor(long id, Author author) {
		Optional<Author> updatedAuthor = authorRepository.findById(id).map(existingAuthor -> {
			return authorRepository.save(existingAuthor.updateWith(author));
		});

		return (updatedAuthor.isPresent() ? updatedAuthor.get() : null);
	}

	@Override
	public void deleteAuthor(long id) {
		authorRepository.deleteById(id);
	}

}
