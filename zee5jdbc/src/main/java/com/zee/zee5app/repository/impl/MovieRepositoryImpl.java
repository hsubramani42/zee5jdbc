package com.zee.zee5app.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zee.zee5app.dto.Movie;
import com.zee.zee5app.dto.enums.GENRE;
import com.zee.zee5app.dto.enums.LANGUAGE;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidNameException;
import com.zee.zee5app.exception.NameNotFoundException;
import com.zee.zee5app.repository.MovieRepository;

@Repository
public class MovieRepositoryImpl implements MovieRepository {

	@Autowired
	DataSource dataSource;

	@Override
	public String addMovie(Movie movie) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		String insertQuery = "INSERT INTO movie "
				+ "(id, name, agelimit, genre, length, releaseDate, cast, language, trailer) "
				+ "VALUES (?,?,?,?,?,?,?,?,?)";

		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(insertQuery);
			ps.setString(1, movie.getId());
			ps.setString(2, movie.getName());
			ps.setInt(3, movie.getAgelimit());
			ps.setString(4, movie.getGenre().toString());
			ps.setInt(5, movie.getLength());
			ps.setDate(6, new Date(movie.getReleaseDate().getTime()));
			ps.setString(7, String.join(",", movie.getCast()));
			ps.setString(8, movie.getLanguage().toString());
			ps.setString(9, movie.getTrailer());

			int result = ps.executeUpdate();

			if (result > 0) {
				connection.commit();
				return "success";
			} else {
				connection.rollback();
				return "fail";
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return "fail";
	}

	@Override
	public String updateMovieById(String id, Movie movie) throws IdNotFoundException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		String insertQuery = "UPDATE movie SET name = ?, agelimit = ?, genre = ?, length = ?, "
				+ "releaseDate = ?, cast = ?, language = ?, trailer = ? where id = ?";

		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(insertQuery);
			ps.setString(1, movie.getName());
			ps.setInt(2, movie.getAgelimit());
			ps.setString(3, movie.getGenre().toString());
			ps.setInt(4, movie.getLength());
			ps.setDate(5, new Date(movie.getReleaseDate().getTime()));
			ps.setString(6, String.join(",", movie.getCast()));
			ps.setString(7, movie.getLanguage().toString());
			ps.setString(8, movie.getTrailer());
			ps.setString(9, movie.getId());

			int result = ps.executeUpdate();

			if (result > 0) {
				connection.commit();
				return "success";
			} else {
				connection.rollback();
				return "fail";
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return "fail";
	}

	@Override
	public String deleteMovieById(String id) throws IdNotFoundException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		String delQuery = "DELETE FROM movie where id=?";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(delQuery);
			prepStatement.setString(1, id);
			int result = prepStatement.executeUpdate();
			if (result > 0) {
				connection.commit();
				return "success";
			} else {
				connection.rollback();
				throw new IdNotFoundException("Invalid Id");
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return "fail";
	}

	@Override
	public Optional<Movie> getMovieById(String id)
			throws IdNotFoundException, InvalidIdLengthException, InvalidNameException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		String getQuery = "SELECT * FROM movie where id=?";

		try {
			PreparedStatement prepStatement = connection.prepareStatement(getQuery);
			prepStatement.setString(1, id);
			ResultSet result = prepStatement.executeQuery();

			if (result.next()) {
				Movie movie = new Movie();
				movie.setId(result.getString("id"));
				movie.setName(result.getString("name"));
				movie.setAgelimit(result.getInt("agelimit"));
				movie.setGenre(GENRE.valueOf(result.getString("genre")));
				movie.setLength(result.getInt("length"));
				movie.setReleaseDate(result.getDate("releaseDate"));
				movie.setCast(result.getString("cast").split(","));
				movie.setLanguage(LANGUAGE.valueOf(result.getString("language")));
				movie.setTrailer(result.getString("trailer"));
				return Optional.of(movie);
			} else {
				throw new IdNotFoundException("Invalid Id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Optional.empty();
	}

	@Override
	public List<Movie> getAllMoviesList() throws InvalidIdLengthException, InvalidNameException {
		List<Movie> movies = new ArrayList<>();
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		String getQuery = "SELECT * FROM movie";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(getQuery);
			ResultSet result = prepStatement.executeQuery();

			while (result.next()) {
				Movie movie = new Movie();
				movie.setId(result.getString("id"));
				movie.setName(result.getString("name"));
				movie.setAgelimit(result.getInt("agelimit"));
				movie.setGenre(GENRE.valueOf(result.getString("genre")));
				movie.setLength(result.getInt("length"));
				movie.setReleaseDate(result.getDate("releaseDate"));
				movie.setCast(result.getString("cast").split(","));
				movie.setLanguage(LANGUAGE.valueOf(result.getString("language")));
				movie.setTrailer(result.getString("trailer"));
				movies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movies;
	}

	@Override
	public Movie[] getAllMovie() throws InvalidIdLengthException, InvalidNameException {
		List<Movie> movies = this.getAllMoviesList();
		return movies.toArray(new Movie[movies.size()]);
	}

	@Override
	public List<Movie> getMovieByName(String name)
			throws NameNotFoundException, InvalidIdLengthException, InvalidNameException {
		List<Movie> movies = new ArrayList<>();
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		String getQuery = "SELECT * FROM movie WHERE name=?";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(getQuery);
			prepStatement.setString(1, name);
			ResultSet result = prepStatement.executeQuery();

			while (result.next()) {
				Movie movie = new Movie();
				movie.setId(result.getString("id"));
				movie.setName(result.getString("name"));
				movie.setAgelimit(result.getInt("agelimit"));
				movie.setGenre(GENRE.valueOf(result.getString("genre")));
				movie.setLength(result.getInt("length"));
				movie.setReleaseDate(result.getDate("releaseDate"));
				movie.setCast(result.getString("cast").split(","));
				movie.setLanguage(LANGUAGE.valueOf(result.getString("language")));
				movie.setTrailer(result.getString("trailer"));
				movies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movies;
	}

}