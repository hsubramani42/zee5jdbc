package com.zee.zee5app.repository;

import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.Movie;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.LocationNotFoundException;
import com.zee.zee5app.exception.NameNotFoundException;

public interface MovieRepository {
	public String addMovie(Movie movie);

	public String updateMovieById(String id, Movie movie) throws IdNotFoundException;

	public String deleteMovieById(String id) throws IdNotFoundException;

	public Optional<Movie> getMovieById(String id) throws IdNotFoundException;

	public List<Movie> getAllMoviesList();
	
	public Movie[] getAllMovie();
	
	public Optional<Movie> getMovieByLocation(String location) throws LocationNotFoundException;

	public List<Movie> getMovieByName(String name) throws NameNotFoundException;

}
