package com.library.authorservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.common.exception.LibraryException;

import io.swagger.annotations.*;

import com.library.authorservice.entity.Author;
import com.library.authorservice.service.AuthorService;

@RestController
@Api(produces = "application/json", value = "Operations pertaining to manage authors in the library application")
@RequestMapping("/api/authors")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@GetMapping
	@ApiOperation(value = "View all authors", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved all authors"),
			@ApiResponse(code = 204, message = "Authors list is empty"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<List<Author>> getAllAuthors() {
		List<Author> authors = authorService.getAllAuthors();

		if (authors.isEmpty())
			throw new LibraryException("no-content", "Authors list is empty", HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(authors, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Retrieve specific author with the specified author id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved author with the author id"),
			@ApiResponse(code = 404, message = "Author with specified author id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<Author> getAuthorById(@PathVariable("id") long id) {
		Author author = authorService.getAuthorById(id);

		if (author != null)
			return new ResponseEntity<>(author, HttpStatus.OK);
		else
			throw new LibraryException("author-not-found", String.format("Author with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@PostMapping
	@ApiOperation(value = "Create a new author", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created a author"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
		try {
			return new ResponseEntity<>(authorService.createAuthor(author), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Update a author information", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated author information"),
			@ApiResponse(code = 404, message = "Author with specified author id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<Author> updateAuthor(@PathVariable("id") long id, @RequestBody Author author) {
		Author updatedAuthor = authorService.updateAuthor(id, author);

		if (updatedAuthor != null)
			return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
		else
			throw new LibraryException("author-not-found", String.format("Author with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a author", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully deleted author information"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<String> deleteAuthor(@PathVariable("id") long id) {
		try {
			authorService.deleteAuthor(id);
			return new ResponseEntity<>("Author deleted successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
