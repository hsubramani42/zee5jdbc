package com.zee.zee5app.repository.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.Episode;
import com.zee.zee5app.dto.Series;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.repository.EpisodeRepository;
import com.zee.zee5app.repository.SeriesRepository;
import com.zee.zee5app.utils.DBUtils;

public class EpisodeRepositoryImpl implements EpisodeRepository {

	private static EpisodeRepository episodeRepository = null;
	private DBUtils dbutils = null;
	private SeriesRepository seriesRepository = null;

	private EpisodeRepositoryImpl() throws IOException {
		dbutils = DBUtils.getInstance();
		seriesRepository = SeriesRepositoryImpl.getInstance();
	}

	public static EpisodeRepository getInstance() throws IOException {
		if (episodeRepository == null)
			episodeRepository = new EpisodeRepositoryImpl();
		return episodeRepository;
	}

	@Override
	public String addEpisode(Episode episode) throws IdNotFoundException {
		Series series;
		try {
			series = seriesRepository.getSeriesById(episode.getSerialId()).get();
		} catch (IdNotFoundException | InvalidIdLengthException e2) {
			e2.printStackTrace();
			throw new IdNotFoundException("Invalid Serial Id");
		}
		Connection connection = dbutils.getConnection();
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
		} finally {
			dbutils.closeConnection(connection);
		}
		return "fail";
	}

	@Override
	public String updateEpisodeById(String id, Episode episode) throws IdNotFoundException {
		Connection connection = dbutils.getConnection();
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
		} finally {
			dbutils.closeConnection(connection);
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
		Connection connection = dbutils.getConnection();
		String delQuery = "DELETE FROM episode where epiId=?";
		try {

			PreparedStatement prepStatement = connection.prepareStatement(delQuery);
			prepStatement.setString(1, id);
			int result = prepStatement.executeUpdate();
			if (result > 0) {
				try {
					series.setNoofepisodes(series.getNoofepisodes() - 1);
					return SeriesRepositoryImpl.getInstance().updateSeriesById(episode.get().getSerialId(), series);
				} catch (IdNotFoundException | IOException e) {
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
		} finally {
			dbutils.closeConnection(connection);
		}
		return "fail";
	}

	@Override
	public Optional<Episode> getEpisodeById(String id) throws IdNotFoundException, InvalidIdLengthException {
		Connection connection = dbutils.getConnection();

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
		} finally {
			dbutils.closeConnection(connection);
		}

		return Optional.empty();
	}

	@Override
	public List<Episode> getAllEpisodeList() throws InvalidIdLengthException {
		List<Episode> episodes = new ArrayList<>();
		Connection connection = dbutils.getConnection();

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
		} finally {
			dbutils.closeConnection(connection);
		}
		return episodes;
	}

	@Override
	public Episode[] getAllEpisode() throws InvalidIdLengthException {
		List<Episode> episode = this.getAllEpisodeList();
		return episode.toArray(new Episode[episode.size()]);
	}

}
