package com.zee.zee5app.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zee.zee5app.dto.Subscription;
import com.zee.zee5app.dto.enums.PLAN_AUTORENEWAL;
import com.zee.zee5app.dto.enums.PLAN_STATUS;
import com.zee.zee5app.dto.enums.PLAN_TYPE;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidAmountException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.repository.SubscriptionRepository;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

	@Autowired
	DataSource dataSource;

	@Override
	public String addSubscription(Subscription subscription) {

		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		String insertQuery = "INSERT INTO subscription "
				+ "(id, dop, expiry, amount, status, type, autorenewal, regId) " + "VALUES (?,?,?,?,?,?,?,?)";

		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(insertQuery);
			ps.setString(1, subscription.getId());
			ps.setDate(2, new Date(subscription.getDateOfPurchase().getTime()));
			ps.setDate(3, new Date(subscription.getExpiryDate().getTime()));
			ps.setFloat(4, subscription.getAmount());
			ps.setString(5, subscription.getStatus().toString());
			ps.setString(6, subscription.getType().toString());
			ps.setString(7, subscription.getAutoRenewal().toString());
			ps.setString(8, subscription.getRegId());

			int result = ps.executeUpdate();

			if (result > 0) {
				connection.commit();
				return "success";
			} else {
				connection.rollback();
				return "fail";
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
	public String updateSubscriptionById(String id, Subscription subscription) throws IdNotFoundException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		String updateQuery = "UPDATE subscription SET "
				+ "dop = ?, expiry = ?, amount = ?, status = ?, type = ?, autorenewal = ?, regId = ? " + "where id = ?";

		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(updateQuery);

			ps.setDate(1, new Date(subscription.getDateOfPurchase().getTime()));
			ps.setDate(2, new Date(subscription.getExpiryDate().getTime()));
			ps.setFloat(3, subscription.getAmount());
			ps.setString(4, subscription.getStatus().toString());
			ps.setString(5, subscription.getType().toString());
			ps.setString(6, subscription.getAutoRenewal().toString());
			ps.setString(7, subscription.getRegId());
			ps.setString(8, id);

			int result = ps.executeUpdate();

			if (result > 0) {
				connection.commit();
				return "success";
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
	public String deleteSubscriptionById(String id) throws IdNotFoundException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		String delQuery = "DELETE FROM subscription where id=?";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(delQuery);
			prepStatement.setString(1, id);
			int result = prepStatement.executeUpdate();
			if (result > 0) {
				connection.commit();
				return "success";
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
	public Optional<Subscription> getSubscriptionById(String id)
			throws IdNotFoundException, InvalidIdLengthException, InvalidAmountException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String getQuery = "SELECT * FROM subscription where id=?";

		try {
			PreparedStatement prepStatement = connection.prepareStatement(getQuery);
			prepStatement.setString(1, id);
			ResultSet result = prepStatement.executeQuery();

			if (result.next()) {
				Subscription subscription = new Subscription();
				subscription.setId(result.getString("id"));
				subscription.setDateOfPurchase(result.getDate("dop"));
				subscription.setExpiryDate(result.getDate("expiry"));
				subscription.setAmount(result.getFloat("amount"));
				subscription.setStatus(PLAN_STATUS.valueOf(result.getString("status")));
				subscription.setType(PLAN_TYPE.valueOf(result.getString("type")));
				subscription.setAutoRenewal(PLAN_AUTORENEWAL.valueOf(result.getString("autorenewal")));
				subscription.setRegId(result.getString("regId"));
				return Optional.of(subscription);
			} else {
				throw new IdNotFoundException("Invalid Id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public List<Subscription> getAllSubscriptionsList() throws InvalidIdLengthException, InvalidAmountException {
		List<Subscription> subscriptions = new ArrayList<>();
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String getQuery = "SELECT * FROM subscription";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(getQuery);
			ResultSet result = prepStatement.executeQuery();

			while (result.next()) {
				Subscription subscription = new Subscription();
				subscription.setId(result.getString("id"));
				subscription.setDateOfPurchase(result.getDate("dop"));
				subscription.setExpiryDate(result.getDate("expiry"));
				subscription.setAmount(result.getFloat("amount"));
				subscription.setStatus(PLAN_STATUS.valueOf(result.getString("status")));
				subscription.setType(PLAN_TYPE.valueOf(result.getString("type")));
				subscription.setAutoRenewal(PLAN_AUTORENEWAL.valueOf(result.getString("autorenewal")));
				subscription.setRegId(result.getString("regId"));
				subscriptions.add(subscription);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return subscriptions;
	}

	@Override
	public Subscription[] getAllSubscriptions() throws InvalidIdLengthException, InvalidAmountException {
		List<Subscription> subscriptions = this.getAllSubscriptionsList();
		return subscriptions.toArray(new Subscription[subscriptions.size()]);
	}

}
