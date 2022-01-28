package com.zee.zee5app;

import java.util.Date;
import java.util.List;
import java.util.Random;

import com.zee.zee5app.dto.Series;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidNameException;
import com.zee.zee5app.exception.LocationNotFoundException;
import com.zee.zee5app.exception.NameNotFoundException;
import com.zee.zee5app.service.SeriesService;
import com.zee.zee5app.service.impl.SeriesServiceImpl;

public class TestSeries {

	public static void main(String[] args) {

		SeriesService seriesService = SeriesServiceImpl.getInstance();
		String seriesNames[] = { "A", "B", "C", "D", "E" };
		String catNames[] = { "1", "2", "3", "4", "5" };
		String language[] = { "Tel", "Tam", "Hin", "Eng", "Kan" };
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {

			try {
				Series series = new Series(seriesNames[rand.nextInt(4)], catNames[rand.nextInt(4)], new Date(),
						"trailer-" + i, language[rand.nextInt(4)], new String[] { "cast-1", "cat-2" }, 200,
						"00000000" + i, "D:\\Personal\\" + i + ".mp4");
				seriesService.addSeries(series);
			} catch (InvalidNameException | InvalidIdLengthException e) {
				e.printStackTrace();
			}
		}

		Series seriesRef = null;
		// Invalid Id
		try {
			seriesRef = new Series(seriesNames[rand.nextInt(4)], catNames[rand.nextInt(4)], new Date(),
					"trailer-Updated", language[rand.nextInt(4)], new String[] { "cast-1", "cat-2" }, 200, "000122",
					"D:\\Personal\\122.mp4");
		} catch (InvalidNameException | InvalidIdLengthException e) {
			e.printStackTrace();
		}

		// Invalid Name
		try {
			seriesRef = new Series("", catNames[rand.nextInt(4)], new Date(), "trailer-Updated",
					language[(int) Math.random() * 4], new String[] { "cast-1", "cat-2" }, 200, "000122",
					"D:\\Personal\\122.mp4");
		} catch (InvalidNameException | InvalidIdLengthException e) {
			e.printStackTrace();
		}

		// Valid Object
		try {
			seriesRef = new Series(seriesNames[rand.nextInt(4)], catNames[rand.nextInt(4)], new Date(),
					"trailer-Updated", language[rand.nextInt(4)], new String[] { "cast-1", "cat-2" }, 200, "000000122",
					"D:\\Personal\\122.mp4");
		} catch (InvalidNameException | InvalidIdLengthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Updating the object
		System.out.print("Updating 000000001: ");
		try {
			System.out.println(seriesService.updateSeriesById("00000000" + 1, seriesRef));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}

		// Deleting the object
		System.out.print("Deleting 000000122: ");
		try {
			System.out.println(seriesService.deleteSeriesById("000000122"));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}

		// Checking the
		System.out.println("Checking 000000122: ");
		try {
			System.out.println(seriesService.getSeriesById("000000122"));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Checking 000000002: ");
		try {
			System.out.println(seriesService.getSeriesById("000000002"));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("Seriess List: ");
		seriesService.getAllSeriesList().forEach(Series -> System.out.println(Series));

		System.out.println("Seriess Array: ");
		for (Series Series : seriesService.getAllSeries())
			System.out.println(Series);

		System.out.print("Check location " + "D:\\Personal\\" + 2 + " :");
		try {
			System.out.println(seriesService.getSeriesByLocation("D:\\Personal\\2.mp4"));
		} catch (LocationNotFoundException e) {
			e.printStackTrace();
		}

		System.out.print("Check location " + "D:\\Personal\\" + 200 + " :");
		try {
			System.out.println(seriesService.getSeriesByLocation("D:\\Personal\\200.mp4"));
		} catch (LocationNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Check \"A Series\": ");
		try {
			List<Series> result = seriesService.getSeriesByName("A");
			result.forEach(series -> System.out.println(series));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("Check \"Z Series\": ");
		try {
			List<Series> result = seriesService.getSeriesByName("Z");
			result.forEach(series -> System.out.println(series));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

	}

}
