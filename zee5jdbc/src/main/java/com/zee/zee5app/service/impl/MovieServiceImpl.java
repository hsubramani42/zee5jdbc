package com.zee.zee5app.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.Movie;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidNameException;
import com.zee.zee5app.exception.NameNotFoundException;
import com.zee.zee5app.repository.MovieRepository;
import com.zee.zee5app.repository.impl.MovieRepositoryImpl;
import com.zee.zee5app.service.MovieService;

public class MovieServiceImpl implements MovieService {
	private MovieRepository movieRepository = null;
	private static MovieService movieService = null;

	private MovieServiceImpl() throws IOException {
		movieRepository = MovieRepositoryImpl.getInstance();
	}

	public static MovieService getInstance() throws IOException {
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
	public Optional<Movie> getMovieById(String id)
			throws IdNotFoundException, InvalidIdLengthException, InvalidNameException {
		return this.movieRepository.getMovieById(id);
	}

	@Override
	public List<Movie> getAllMoviesList() throws InvalidIdLengthException, InvalidNameException {
		return this.movieRepository.getAllMoviesList();
	}

	@Override
	public Movie[] getAllMovie() throws InvalidIdLengthException, InvalidNameException {
		return this.movieRepository.getAllMovie();
	}

	@Override
	public List<Movie> getMovieByName(String name)
			throws NameNotFoundException, InvalidIdLengthException, InvalidNameException {
		return this.movieRepository.getMovieByName(name);
	}

}
