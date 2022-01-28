package com.zee.zee5app.repository.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import com.zee.zee5app.dto.Movie;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.LocationNotFoundException;
import com.zee.zee5app.exception.NameNotFoundException;
import com.zee.zee5app.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {

	private static MovieRepositoryImpl movieRepository = null;
	private Set<Movie> movies = new HashSet<>();

	private MovieRepositoryImpl() {

	}

	public static MovieRepositoryImpl getInstance() {
		if (movieRepository == null)
			movieRepository = new MovieRepositoryImpl();
		return movieRepository;
	}

	@Override
	public String addMovie(Movie movie) {

		boolean status = movies.add(movie);
		return status ? "success" : "failed";
	}

	@Override
	public String updateMovieById(String id, Movie movie) throws IdNotFoundException {
		boolean status = movies.remove(this.getMovieById(id).get());
		if (status)
			status = movies.add(movie);
		return status ? "success" : "failed";
	}

	@Override
	public String deleteMovieById(String id) throws IdNotFoundException {
		boolean status = movies.remove(this.getMovieById(id).get());
		return status ? "success" : "failed";
	}

	@Override
	public Optional<Movie> getMovieById(String id) throws IdNotFoundException {
		for (Movie movie : movies)
			if (movie.getId().equals(id))
				return Optional.of(movie);
		Movie notFound = null;
		return Optional.of(Optional.ofNullable(notFound).orElseThrow(() -> new IdNotFoundException("Invalid Id")));
	}

	@Override
	public ArrayList<Movie> getAllMoviesList() {
		return new ArrayList<Movie>(new TreeSet<Movie>(movies));
	}

	@Override
	public Movie[] getAllMovie() {
		return new TreeSet<Movie>(movies).toArray(new Movie[movies.size()]);
	}

	@Override
	public Optional<Movie> getMovieByLocation(String location) throws LocationNotFoundException {
		for (Movie movie : movies)
			if (movie.getLocation().equals(location))
				return Optional.of(movie);
		Movie notFound = null;
		return Optional.of(
				Optional.ofNullable(notFound).orElseThrow(() -> new LocationNotFoundException("Invalid Location")));
	}

	@Override
	public List<Movie> getMovieByName(String name) throws NameNotFoundException {
		List<Movie> movies = new ArrayList<>();
		for (Movie movie : this.movies)
			if (movie.getName().equals(name))
				movies.add(movie);
		if(movies.size()==0)
			throw new NameNotFoundException("Invalid Movie Name");
		return movies;
	}
}