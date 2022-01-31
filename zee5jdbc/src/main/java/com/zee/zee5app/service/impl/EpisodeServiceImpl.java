package com.zee.zee5app.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.Episode;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.repository.EpisodeRepository;
import com.zee.zee5app.repository.impl.EpisodeRepositoryImpl;
import com.zee.zee5app.service.EpisodeService;

public class EpisodeServiceImpl implements EpisodeService {
	private EpisodeRepository episodeRepository = null;
	private static EpisodeService episodeService = null;

	private EpisodeServiceImpl() throws IOException {
		episodeRepository = EpisodeRepositoryImpl.getInstance();
	}

	public static EpisodeService getInstance() throws IOException {
		if (episodeService == null)
			episodeService = new EpisodeServiceImpl();
		return episodeService;
	}

	@Override
	public String addEpisode(Episode episode) throws IdNotFoundException {
		return this.episodeRepository.addEpisode(episode);
	}

	@Override
	public String updateEpisodeById(String id, Episode episode) throws IdNotFoundException {
		return this.episodeRepository.updateEpisodeById(id, episode);
	}

	@Override
	public String deleteEpisodeById(String id) throws IdNotFoundException, InvalidIdLengthException {
		return this.episodeRepository.deleteEpisodeById(id);
	}

	@Override
	public Optional<Episode> getEpisodeById(String id) throws IdNotFoundException, InvalidIdLengthException {
		return this.episodeRepository.getEpisodeById(id);
	}

	@Override
	public List<Episode> getAllEpisodeList() throws InvalidIdLengthException {
		return this.episodeRepository.getAllEpisodeList();
	}

	@Override
	public Episode[] getAllEpisode() throws InvalidIdLengthException {
		return this.episodeRepository.getAllEpisode();
	}

}
