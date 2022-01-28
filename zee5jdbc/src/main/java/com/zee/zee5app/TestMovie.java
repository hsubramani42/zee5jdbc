package com.zee.zee5app;

import java.util.Date;
import java.util.List;
import java.util.Random;

import com.zee.zee5app.dto.Movie;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidNameException;
import com.zee.zee5app.exception.LocationNotFoundException;
import com.zee.zee5app.exception.NameNotFoundException;
import com.zee.zee5app.service.MovieService;
import com.zee.zee5app.service.impl.MovieServiceImpl;

public class TestMovie {

	public static void main(String[] args) {

		MovieService movieService = MovieServiceImpl.getInstance();
		String movieNames[] = { "A", "B", "C", "D", "E" };
		String catNames[] = { "1", "2", "3", "4", "5" };
		String language[] = { "Tel", "Tam", "Hin", "Eng", "Kan" };
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {

			try {
				Movie movie = new Movie(movieNames[rand.nextInt(4)], catNames[rand.nextInt(4)],
						new Date(), "trailer-" + i, language[rand.nextInt(4)],
						new String[] { "cast-1", "cat-2" }, 200, "00000000" + i, "D:\\Personal\\" + i + ".mp4");
				movieService.addMovie(movie);
			} catch (InvalidNameException | InvalidIdLengthException e) {
				e.printStackTrace();
			}
		}

		Movie movieRef = null;
		// Invalid Id
		try {
			movieRef = new Movie(movieNames[rand.nextInt(4)], catNames[rand.nextInt(4)],
					new Date(), "trailer-Updated", language[rand.nextInt(4)],
					new String[] { "cast-1", "cat-2" }, 200, "000122", "D:\\Personal\\122.mp4");
		} catch (InvalidNameException | InvalidIdLengthException e) {
			e.printStackTrace();
		}

		// Invalid Name
		try {
			movieRef = new Movie("", catNames[rand.nextInt(4)], new Date(), "trailer-Updated",
					language[(int) Math.random() * 4], new String[] { "cast-1", "cat-2" }, 200, "000122",
					"D:\\Personal\\122.mp4");
		} catch (InvalidNameException | InvalidIdLengthException e) {
			e.printStackTrace();
		}
		
		//Valid Object
		try {
			movieRef = new Movie(movieNames[rand.nextInt(4)], catNames[rand.nextInt(4)],
					new Date(), "trailer-Updated", language[rand.nextInt(4)],
					new String[] { "cast-1", "cat-2" }, 200, "000000122", "D:\\Personal\\122.mp4");
		} catch (InvalidNameException | InvalidIdLengthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Updating the object
		System.out.print("Updating 000000001: ");
		try {
			System.out.println(movieService.updateMovieById("00000000"+1, movieRef));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}
		
		//Deleting the object
		System.out.print("Deleting 000000122: ");
		try {
			System.out.println(movieService.deleteMovieById("000000122"));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}
		
		//Checking the 
		System.out.println("Checking 000000122: ");
		try {
			System.out.println(movieService.getMovieById("000000122"));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Checking 000000002: ");
		try {
			System.out.println(movieService.getMovieById("000000002"));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("Movies List: ");
		movieService.getAllMoviesList().forEach(movie -> System.out.println(movie));
		
		System.out.println("Movies Array: ");
		for(Movie movie : movieService.getAllMovie())
			System.out.println(movie);
		
		System.out.print("Check location "+"D:\\Personal\\" + 2 +" :");
		try {
			System.out.println(movieService.getMovieByLocation("D:\\Personal\\2.mp4"));
		} catch (LocationNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.print("Check location "+"D:\\Personal\\" + 200 +" :");
		try {
			System.out.println(movieService.getMovieByLocation("D:\\Personal\\200.mp4"));
		} catch (LocationNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Check \"A Movie\": ");
		try {
			List<Movie> result = movieService.getMovieByName("A");
			result.forEach(movie -> System.out.println(movie));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("Check \"Z Movie\": ");
		try {
			List<Movie> result = movieService.getMovieByName("Z");
			result.forEach(movie -> System.out.println(movie));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
	
	}

}
