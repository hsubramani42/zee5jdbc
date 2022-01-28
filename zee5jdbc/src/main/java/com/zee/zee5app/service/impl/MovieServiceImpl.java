package com.zee.zee5app.service.impl;

import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.Movie;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.LocationNotFoundException;
import com.zee.zee5app.exception.NameNotFoundException;
import com.zee.zee5app.repository.impl.MovieRepositoryImpl;
import com.zee.zee5app.service.MovieService;

public class MovieServiceImpl implements MovieService {
	private MovieRepositoryImpl movieRepository = MovieRepositoryImpl.getInstance();
	private static MovieServiceImpl movieService = null;

	private MovieServiceImpl() {

	}

	public static MovieServiceImpl getInstance() {
		if (movieService == null)
			movieService = new MovieServiceImpl();
		return movieService;
	}

	@Override
	public String addMovie(Movie movie) {
		return this.movieRepository.addMovie(movie);
	}

	@Override
	public String updateMovieById(String id, Movie movie) throws IdNotFoundException {
		return this.movieRepository.updateMovieById(id, movie);
	}

	@Override
	public String deleteMovieById(String id) throws IdNotFoundException {
		return this.movieRepository.deleteMovieById(id);
	}

	@Override
	public Optional<Movie> getMovieById(String id) throws IdNotFoundException {
		return this.movieRepository.getMovieById(id);
	}

	@Override
	public List<Movie> getAllMoviesList() {
		return this.movieRepository.getAllMoviesList();
	}

	@Override
	public Movie[] getAllMovie() {
		return this.movieRepository.getAllMovie();
	}

	@Override
	public Optional<Movie> getMovieByLocation(String location) throws LocationNotFoundException {
		return this.movieRepository.getMovieByLocation(location);
	}

	@Override
	public List<Movie> getMovieByName(String name) throws NameNotFoundException {
		return this.movieRepository.getMovieByName(name);
	}

}
