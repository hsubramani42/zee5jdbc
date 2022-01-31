package com.zee.zee5app.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zee.zee5app.dto.Episode;
import com.zee.zee5app.dto.Series;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.repository.EpisodeRepository;
import com.zee.zee5app.repository.SeriesRepository;

@Repository
public class EpisodeRepositoryImpl implements EpisodeRepository {

	@Autowired
	DataSource dataSource;

	@Autowired
	SeriesRepository seriesRepository;

	@Override
	public String addEpisode(Episode episode) throws IdNotFoundException {
		Series series;
		try {
			series = seriesRepository.getSeriesById(episode.getSerialId()).get();
		} catch (IdNotFoundException | InvalidIdLengthException e2) {
			e2.printStackTrace();
			throw new IdNotFoundException("Invalid Serial Id");
		}

		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String insertQuery = "INSERT INTO episode " + "(epiId, serialId, episodename, epilength, location) "
				+ "VALUES (?,?,?,?,?)";

		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(insertQuery);
			ps.setString(1, episode.getEpiId());
			ps.setString(2, episode.getSerialId());
			ps.setString(3, episode.getEpisodename());
			ps.setInt(4, episode.getLength());
			ps.setString(5, episode.getLocation());

			int result = ps.executeUpdate();
			if (result > 0) {
				try {
					series.setNoofepisodes(series.getNoofepisodes() + 1);
					return seriesRepository.updateSeriesById(episode.getSerialId(), series);
				} catch (IdNotFoundException e) {
					e.printStackTrace();
				}

			}
			connection.rollback();
			return "fail";

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return "fail";
	}

	@Override
	public String updateEpisodeById(String id, Episode episode) throws IdNotFoundException {

		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String insertQuery = "UPDATE episode SET " + "episodename=?, epilength=?, location=? " + "WHERE epiId=?";

		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(insertQuery);
			ps.setString(1, episode.getEpisodename());
			ps.setInt(2, episode.getLength());
			ps.setString(3, episode.getLocation());

			ps.setString(4, episode.getEpiId());

			int result = ps.executeUpdate();

			if (result > 0) {
				connection.commit();
				return "fail";

			}
			connection.rollback();
			return "fail";

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return "fail";
	}

	@Override
	public String deleteEpisodeById(String id) throws IdNotFoundException, InvalidIdLengthException {
		Series series;
		Optional<Episode> episode = this.getEpisodeById(id);
		if (episode.isEmpty())
			throw new IdNotFoundException("Invalid ID");
		try {
			series = seriesRepository.getSeriesById(episode.get().getSerialId()).get();
		} catch (IdNotFoundException | InvalidIdLengthException e2) {
			e2.printStackTrace();
			throw new IdNotFoundException("Invalid Serial Id");
		}
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String delQuery = "DELETE FROM episode where epiId=?";
		try {

			PreparedStatement prepStatement = connection.prepareStatement(delQuery);
			prepStatement.setString(1, id);
			int result = prepStatement.executeUpdate();
			if (result > 0) {
				try {
					series.setNoofepisodes(series.getNoofepisodes() - 1);
					return seriesRepository.updateSeriesById(episode.get().getSerialId(), series);
				} catch (IdNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				connection.rollback();
				throw new IdNotFoundException("Invalid Id");
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return "fail";
	}

	@Override
	public Optional<Episode> getEpisodeById(String id) throws IdNotFoundException, InvalidIdLengthException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String getQuery = "SELECT * FROM episode where epiId=?";

		try {
			PreparedStatement prepStatement = connection.prepareStatement(getQuery);
			prepStatement.setString(1, id);
			ResultSet result = prepStatement.executeQuery();

			if (result.next()) {
				Episode episode = new Episode();
				episode.setEpiId(result.getString("epiId"));
				episode.setSerialId(result.getString("serialId"));
				episode.setEpisodename(result.getString("episodename"));
				episode.setLength(result.getInt("epilength"));
				episode.setLocation(result.getString("location"));

				return Optional.of(episode);
			} else {
				throw new IdNotFoundException("Invalid Id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Optional.empty();
	}

	@Override
	public List<Episode> getAllEpisodeList() throws InvalidIdLengthException {
		List<Episode> episodes = new ArrayList<>();
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String getQuery = "SELECT * FROM episode";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(getQuery);
			ResultSet result = prepStatement.executeQuery();

			while (result.next()) {
				Episode episode = new Episode();
				episode.setEpiId(result.getString("epiId"));
				episode.setSerialId(result.getString("serialId"));
				episode.setEpisodename(result.getString("episodename"));
				episode.setLength(result.getInt("epilength"));
				episode.setLocation(result.getString("location"));
				episodes.add(episode);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return episodes;
	}

	@Override
	public Episode[] getAllEpisode() throws InvalidIdLengthException {
		List<Episode> episode = this.getAllEpisodeList();
		return episode.toArray(new Episode[episode.size()]);
	}

}
