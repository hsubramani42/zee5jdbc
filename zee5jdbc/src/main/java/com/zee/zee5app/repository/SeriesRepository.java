package com.zee.zee5app.repository;

import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.Series;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.LocationNotFoundException;
import com.zee.zee5app.exception.NameNotFoundException;

public interface SeriesRepository {
	public String addSeries(Series series);

	public String updateSeriesById(String id, Series series) throws IdNotFoundException;

	public String deleteSeriesById(String id) throws IdNotFoundException;

	public Optional<Series> getSeriesById(String id) throws IdNotFoundException;

	public List<Series> getAllSeriesList();
	
	public Series[] getAllSeries();
	
	public Optional<Series> getSeriesByLocation(String location) throws LocationNotFoundException;
	
	public List<Series> getSeriesByName(String name) throws NameNotFoundException;
}
