package com.library.authorservice.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Class representing a author in the library application.")
@Entity
@Data
@NoArgsConstructor
@Table(name = "author")
@JsonPropertyOrder({"id", "name", "email", "country"})
public class Author {

	@ApiModelProperty(notes = "Unique identifier of the Author.", example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(notes = "Name of the Author.", example = "Abhishek Agarwal", required = true)
	@NotBlank
	private String name;

	@ApiModelProperty(notes = "Email address of the Author.", example = "abhishek.agarwal@gmail.com", required = true)
	@Email(message = "Email Address")
	private String email;

	@ApiModelProperty(notes = "Country of the Author.", example = "India", required = false)
	private String country;

	public Author(Long id, String name, String email, String country) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.country = country;
	}

	public Author updateWith(Author author) {
		return new Author(this.id, author.name, author.email, author.country);
	}

}
