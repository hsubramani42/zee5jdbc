package com.zee.zee5app.service.impl;

import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.Series;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.LocationNotFoundException;
import com.zee.zee5app.exception.NameNotFoundException;
import com.zee.zee5app.repository.impl.SeriesRepositoryImpl;
import com.zee.zee5app.service.SeriesService;

public class SeriesServiceImpl implements SeriesService {
	private SeriesRepositoryImpl seriesRepository = SeriesRepositoryImpl.getInstance();
	private static SeriesServiceImpl seriesService = null;
	private SeriesServiceImpl() {

	}
	public static SeriesService getInstance() {
		if(seriesService == null )
			seriesService = new SeriesServiceImpl();
		return seriesService;
	}
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
	public Optional<Series> getSeriesById(String id) throws IdNotFoundException {
		return this.seriesRepository.getSeriesById(id);
	}
	@Override
	public List<Series> getAllSeriesList() {
		return this.seriesRepository.getAllSeriesList();
	}
	@Override
	public Series[] getAllSeries() {
		return this.seriesRepository.getAllSeries();
	}
	@Override
	public Optional<Series> getSeriesByLocation(String location) throws LocationNotFoundException {
		return this.seriesRepository.getSeriesByLocation(location);
	}
	@Override
	public List<Series> getSeriesByName(String name) throws NameNotFoundException {
		return this.seriesRepository.getSeriesByName(name);
	}

}
