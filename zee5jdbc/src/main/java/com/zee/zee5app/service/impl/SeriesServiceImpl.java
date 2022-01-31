package com.zee.zee5app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zee.zee5app.dto.Series;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.repository.SeriesRepository;
import com.zee.zee5app.service.SeriesService;

@Service
public class SeriesServiceImpl implements SeriesService {
	@Autowired
	private SeriesRepository seriesRepository;

	@Override
	public String addSeries(Series series) {
		return this.seriesRepository.addSeries(series);
	}

	@Override
	public String updateSeriesById(String id, Series series) throws IdNotFoundException {
		return this.seriesRepository.updateSeriesById(id, series);
	}

	@Override
	public String deleteSeriesById(String id) throws IdNotFoundException {
		return this.seriesRepository.deleteSeriesById(id);
	}

	@Override
	public Optional<Series> getSeriesById(String id) throws IdNotFoundException, InvalidIdLengthException {
		return this.seriesRepository.getSeriesById(id);
	}

	@Override
	public List<Series> getAllSeriesList() throws InvalidIdLengthException {
		return this.seriesRepository.getAllSeriesList();
	}

	@Override
	public Series[] getAllSeries() throws InvalidIdLengthException {
		return this.seriesRepository.getAllSeries();
	}

}
