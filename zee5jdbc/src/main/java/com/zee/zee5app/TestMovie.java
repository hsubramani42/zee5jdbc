package com.zee.zee5app;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import com.zee.zee5app.dto.Movie;
import com.zee.zee5app.dto.enums.GENRE;
import com.zee.zee5app.dto.enums.LANGUAGE;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidNameException;
import com.zee.zee5app.exception.NameNotFoundException;
import com.zee.zee5app.service.MovieService;
import com.zee.zee5app.service.impl.MovieServiceImpl;

public class TestMovie {

	public static void main(String[] args) {

		MovieService movieService = null;

		try {
			movieService = MovieServiceImpl.getInstance();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		String movieNames[] = { "A", "B", "C", "D", "E" };
		Random rand = new Random();
		System.out.print("Adding MOV0000010: ");
		try {
			Movie movie = new Movie("MOV0000010", movieNames[rand.nextInt(5)], rand.nextInt(12, 21),
					GENRE.values()[rand.nextInt(6)], rand.nextInt(6000, 11000), new Date(),
					new String[] { "cast-1", "cat-2" }, LANGUAGE.values()[rand.nextInt(5)],
					"https://www.youtube.com/watch?v=Gq" + rand.nextInt(10, 1000000));
			System.out.println(movieService.addMovie(movie));
		} catch (InvalidNameException | InvalidIdLengthException e) {
			e.printStackTrace();
		}

		Movie movieRef = null;

		// Valid Object
		try {
			movieRef = new Movie("MOV0000010", movieNames[rand.nextInt(5)], rand.nextInt(12, 21),
					GENRE.values()[rand.nextInt(4)], rand.nextInt(6000, 11000), new Date(),
					new String[] { "cast-1", "cat-2" }, LANGUAGE.values()[rand.nextInt(4)],
					"https://www.youtube.com/watch?v=Gq" + rand.nextInt(10, 1000000));
		} catch (InvalidNameException | InvalidIdLengthException e) {
			e.printStackTrace();
		}

		// Checking the
		System.out.print("Checking MOV0000010: ");
		try {
			System.out.println(movieService.getMovieById("MOV0000010").isPresent());
		} catch (IdNotFoundException | InvalidIdLengthException | InvalidNameException e) {
			e.printStackTrace();
		}

		// Updating the object
		System.out.print("Updating MOV0000010: ");
		try {
			System.out.println(movieService.updateMovieById("MOV0000010", movieRef));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}

		// Deleting the object
		System.out.print("Deleting MOV0000010: ");
		try {
			System.out.println(movieService.deleteMovieById("MOV0000010"));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}

		// Checking the
		System.out.println("Checking MOV0000010: ");
		try {
			System.out.println(movieService.getMovieById("MOV0000010"));
		} catch (IdNotFoundException | InvalidIdLengthException | InvalidNameException e) {
			e.printStackTrace();
		}

		System.out.println("Movies List: ");
		try {
			movieService.getAllMoviesList().forEach(movie -> System.out.println(movie));
		} catch (InvalidIdLengthException | InvalidNameException e1) {
			e1.printStackTrace();
		}

		System.out.println("Movies Array: ");
		try {
			for (Movie movie : movieService.getAllMovie())
				System.out.println(movie);
		} catch (InvalidIdLengthException | InvalidNameException e1) {
			e1.printStackTrace();
		}

		System.out.println("Movies with Name 83: ");
		try {
			movieService.getMovieByName("83").forEach(movie -> System.out.println(movie));
		} catch (InvalidIdLengthException | InvalidNameException | NameNotFoundException e1) {
			e1.printStackTrace();
		}

	}

}
