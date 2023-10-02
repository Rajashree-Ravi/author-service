package com.library.authorservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.library.authorservice.entity.Author;
import com.library.authorservice.repository.AuthorRepository;
import com.library.authorservice.service.AuthorService;
import com.library.common.exception.LibraryException;

@Service
public class AuthorServiceImpl implements AuthorService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorService.class);

	@Autowired
	private AuthorRepository authorRepository;
	
	private final RestTemplate restTemplate;
	private static final String BOOK_SERVICE_URL = "http://localhost:8083/api/books/author";

	public AuthorServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

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
		//Delete books before deleting author
		
		ResponseEntity<String> result = restTemplate.exchange(BOOK_SERVICE_URL + "/" + id, HttpMethod.PUT, null, String.class);
		LOGGER.info("Response Status: " + result.getStatusCode());
		LOGGER.info("Response Body: " + result.getBody());
		
		if (result.getStatusCode() == HttpStatus.OK || result.getStatusCode() == HttpStatus.NO_CONTENT) {
			authorRepository.deleteById(id);
		} else {
			throw new LibraryException("delete-failed", result.getStatusCode() + " - Received from book service.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
