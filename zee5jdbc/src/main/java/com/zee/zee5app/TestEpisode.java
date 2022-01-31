package com.zee.zee5app;

import java.io.IOException;
import java.util.Random;

import com.zee.zee5app.dto.Episode;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.service.EpisodeService;
import com.zee.zee5app.service.impl.EpisodeServiceImpl;

public class TestEpisode {

	public static void main(String[] args) {
		EpisodeService episodeService = null;
		try {
			episodeService = EpisodeServiceImpl.getInstance();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Random rand = new Random();
		System.out.print("Adding EPI0000010: ");
		try {
			Episode episode = new Episode("EPI0000010", "SER0000001", "In the forest.", rand.nextInt(600, 7200), "USA",
					"");
			System.out.println(episodeService.addEpisode(episode));
		} catch (IdNotFoundException | InvalidIdLengthException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}

		Episode episodeRef = null;

		// Valid Object
		try {
			episodeRef = new Episode("EPI0000010", "SER0000001", "Inside the forest.", rand.nextInt(600, 7200),
					"America", "");
			;
		} catch (InvalidIdLengthException e) {
			e.printStackTrace();
		}

		// Checking the
		System.out.print("Checking EPI0000010: ");
		try {
			System.out.println(episodeService.getEpisodeById("EPI0000010").isPresent());
		} catch (IdNotFoundException | InvalidIdLengthException e) {
			e.printStackTrace();
		}

		// Updating the object
		System.out.print("Updating EPI0000010: ");
		try {
			System.out.println(episodeService.updateEpisodeById("EPI0000010", episodeRef));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}

		// Deleting the object
		System.out.print("Deleting EPI0000010: ");
		try {
			System.out.println(episodeService.deleteEpisodeById("EPI0000010"));
		} catch (IdNotFoundException | InvalidIdLengthException e) {
			e.printStackTrace();
		}

		// Checking the existence
		System.out.println("Checking EPI0000010: ");
		try {
			System.out.println(episodeService.getEpisodeById("EPI0000010"));
		} catch (IdNotFoundException | InvalidIdLengthException e) {
			e.printStackTrace();
		}

		System.out.println("Episodes List: ");
		try {
			episodeService.getAllEpisodeList().forEach(Episode -> System.out.println(Episode));
		} catch (InvalidIdLengthException e) {
			e.printStackTrace();
		}

		System.out.println("Episodes Array: ");
		try {
			for (Episode Episode : episodeService.getAllEpisode())
				System.out.println(Episode);
		} catch (InvalidIdLengthException e) {
			e.printStackTrace();
		}

	}

}
