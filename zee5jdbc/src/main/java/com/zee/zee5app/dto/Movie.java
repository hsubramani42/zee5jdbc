package com.zee.zee5app.dto;

import java.util.Date;

import com.zee.zee5app.dto.enums.GENRE;
import com.zee.zee5app.dto.enums.LANGUAGE;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidNameException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Setter
@ToString
public class Movie implements Comparable<Movie> {

	@Setter(value = AccessLevel.NONE)
	private String id;
	@Setter(value = AccessLevel.NONE)
	private String name;
	private int agelimit;
	private GENRE genre;
	private int length;
	private Date releaseDate;
	private String[] cast;
	private LANGUAGE language;
	private String trailer;

	public Movie(String id, String name, int agelimit, GENRE genre, int length, Date releaseDate, String[] cast,
			LANGUAGE language, String trailer) throws InvalidIdLengthException, InvalidNameException {
		this.setId(id);
		this.setName(name);
		this.setAgelimit(agelimit);
		this.setGenre(genre);
		this.setLength(length);
		this.setReleaseDate(releaseDate);
		this.setCast(cast);
		this.setLanguage(language);
		this.setTrailer(trailer);
	}

	public void setName(String name) throws InvalidNameException {
		if ((name == null) || (name.length() == 0))
			throw new InvalidNameException("Invalid Name");
		this.name = name;
	}

	public void setId(String id) throws InvalidIdLengthException {
		if ((id == null) || (id.length() < 8))
			throw new InvalidIdLengthException("Invalid Id length");
		this.id = id;
	}

	@Override
	public int compareTo(Movie obj) {
		return this.id.compareTo(obj.id);
	}

}
