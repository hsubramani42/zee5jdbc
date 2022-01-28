package com.zee.zee5app.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import com.zee.zee5app.dto.Series;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.LocationNotFoundException;
import com.zee.zee5app.exception.NameNotFoundException;
import com.zee.zee5app.repository.SeriesRepository;

public class SeriesRepositoryImpl implements SeriesRepository {
	private static SeriesRepositoryImpl seriesRepository = null;
	private Set<Series> seriess = new TreeSet<>();

	private SeriesRepositoryImpl() {

	}

	public static SeriesRepositoryImpl getInstance() {
		if (seriesRepository == null)
			seriesRepository = new SeriesRepositoryImpl();
		return seriesRepository;
	}

	@Override
	public String addSeries(Series series) {
		boolean status = seriess.add(series);
		return status ? "success" : "failed";
	}

	@Override
	public String updateSeriesById(String id, Series series) throws IdNotFoundException {
		boolean status = seriess.remove(this.getSeriesById(id).get());
		if (status)
			status = seriess.add(series);
		return status ? "success" : "failed";
	}

	@Override
	public String deleteSeriesById(String id) throws IdNotFoundException {
		boolean status = seriess.remove(this.getSeriesById(id).get());
		return status ? "success" : "failed";
	}

	@Override
	public Optional<Series> getSeriesById(String id) throws IdNotFoundException {
		for (Series series : seriess)
			if (series.getId().equals(id))
				return Optional.of(series);
		Series notFound = null;
		return Optional.of(Optional.ofNullable(notFound).orElseThrow(() -> new IdNotFoundException("Invalid Id")));
	}

	@Override
	public List<Series> getAllSeriesList() {
		return new ArrayList<Series>(seriess);
	}

	@Override
	public Series[] getAllSeries() {
		return seriess.toArray(new Series[seriess.size()]);
	}

	@Override
	public Optional<Series> getSeriesByLocation(String location) throws LocationNotFoundException {
		for (Series series : seriess)
			if (series.getLocation().equals(location))
				return Optional.of(series);
		Series notFound = null;
		return Optional.of(
				Optional.ofNullable(notFound).orElseThrow(() -> new LocationNotFoundException("Invalid Location")));
	}

	@Override
	public List<Series> getSeriesByName(String name) throws NameNotFoundException {
		List<Series> seriess=new ArrayList<>();
		for (Series series : this.seriess)
			if (series.getName().equals(name))
				seriess.add(series);
		if(seriess.size()==0)
			throw new NameNotFoundException("Invalid Movie Name");
		return seriess;
	}
}
