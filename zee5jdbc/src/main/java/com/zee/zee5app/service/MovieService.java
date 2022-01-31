package com.zee.zee5app.service;

import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.Movie;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidNameException;
import com.zee.zee5app.exception.NameNotFoundException;

public interface MovieService {
	public String addMovie(Movie movie);

	public String updateMovieById(String id, Movie movie) throws IdNotFoundException;

	public String deleteMovieById(String id) throws IdNotFoundException;

	public Optional<Movie> getMovieById(String id)
			throws IdNotFoundException, InvalidIdLengthException, InvalidNameException;

	public List<Movie> getAllMoviesList() throws InvalidIdLengthException, InvalidNameException;

	public Movie[] getAllMovie() throws InvalidIdLengthException, InvalidNameException;

	public List<Movie> getMovieByName(String name)
			throws NameNotFoundException, InvalidIdLengthException, InvalidNameException;

}
