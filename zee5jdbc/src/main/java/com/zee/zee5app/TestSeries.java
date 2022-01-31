package com.zee.zee5app;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import com.zee.zee5app.dto.Series;
import com.zee.zee5app.dto.enums.GENRE;
import com.zee.zee5app.dto.enums.LANGUAGE;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.service.SeriesService;
import com.zee.zee5app.service.impl.SeriesServiceImpl;

public class TestSeries {

	public static void main(String[] args) {

		SeriesService seriesService = null;
		try {
			seriesService = SeriesServiceImpl.getInstance();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Random rand = new Random();
		System.out.print("Adding SER0000010: ");
		try {
			Series series = new Series("SER0000010", rand.nextInt(12, 21), new String[] { "cast-1", "cat-2" },
					GENRE.values()[rand.nextInt(6)], rand.nextInt(600, 5000), "", new Date(),
					LANGUAGE.values()[rand.nextInt(5)], 0);
			System.out.println(seriesService.addSeries(series));;
		} catch (InvalidIdLengthException e) {
			e.printStackTrace();
		}
		Series seriesRef = null;

		// Valid Object
		try {
			seriesRef = new Series("SER0000010", rand.nextInt(12, 21), new String[] { "cast-1", "cat-2" },
					GENRE.values()[rand.nextInt(6)], rand.nextInt(600, 5000), "", new Date(),
					LANGUAGE.values()[rand.nextInt(5)], 0);
		} catch (InvalidIdLengthException e) {
			e.printStackTrace();
		}

		// Checking the
		System.out.print("Checking SER0000010: ");
		try {
			System.out.println(seriesService.getSeriesById("SER0000010").isPresent());
		} catch (IdNotFoundException | InvalidIdLengthException e) {
			e.printStackTrace();
		}

		// Updating the object
		System.out.print("Updating SER0000010: ");
		try {
			System.out.println(seriesService.updateSeriesById("SER0000010", seriesRef));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}

		// Deleting the object
		System.out.print("Deleting SER0000010: ");
		try {
			System.out.println(seriesService.deleteSeriesById("SER0000010"));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}

		// Checking the
		System.out.println("Checking SER0000010: ");
		try {
			System.out.println(seriesService.getSeriesById("SER0000010"));
		} catch (IdNotFoundException | InvalidIdLengthException e) {
			e.printStackTrace();
		}

		System.out.println("Seriess List: ");
		try {
			seriesService.getAllSeriesList().forEach(Series -> System.out.println(Series));
		} catch (InvalidIdLengthException e) {
			e.printStackTrace();
		}

		System.out.println("Seriess Array: ");
		try {
			for (Series Series : seriesService.getAllSeries())
				System.out.println(Series);
		} catch (InvalidIdLengthException e) {
			e.printStackTrace();
		}

	}

}
