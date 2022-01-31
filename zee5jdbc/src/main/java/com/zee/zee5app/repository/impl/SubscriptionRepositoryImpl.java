package com.zee.zee5app.repository.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.Subscription;
import com.zee.zee5app.dto.enums.PLAN_AUTORENEWAL;
import com.zee.zee5app.dto.enums.PLAN_STATUS;
import com.zee.zee5app.dto.enums.PLAN_TYPE;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidAmountException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.repository.SubscriptionRepository;
import com.zee.zee5app.utils.DBUtils;

public class SubscriptionRepositoryImpl implements SubscriptionRepository {

	private static SubscriptionRepositoryImpl subscriptionRepository = null;
	private DBUtils dbutils = null;

	private SubscriptionRepositoryImpl() throws IOException {
		dbutils = DBUtils.getInstance();
	}

	public static SubscriptionRepositoryImpl getInstance() throws IOException {
		if (subscriptionRepository == null)
			subscriptionRepository = new SubscriptionRepositoryImpl();
		return subscriptionRepository;
	}

	@Override
	public String addSubscription(Subscription subscription) {

		Connection connection = dbutils.getConnection();
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
		} finally {
			dbutils.closeConnection(connection);
		}
		return "fail";
	}

	@Override
	public String updateSubscriptionById(String id, Subscription subscription) throws IdNotFoundException {
		Connection connection = dbutils.getConnection();
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
		} finally {
			dbutils.closeConnection(connection);
		}
		return "fail";
	}

	@Override
	public String deleteSubscriptionById(String id) throws IdNotFoundException {
		Connection connection = dbutils.getConnection();
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
		} finally {
			dbutils.closeConnection(connection);
		}
		return "fail";
	}

	@Override
	public Optional<Subscription> getSubscriptionById(String id)
			throws IdNotFoundException, InvalidIdLengthException, InvalidAmountException {
		Connection connection = dbutils.getConnection();

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
		} finally {
			dbutils.closeConnection(connection);
		}

		return Optional.empty();
	}

	@Override
	public List<Subscription> getAllSubscriptionsList() throws InvalidIdLengthException, InvalidAmountException {
		List<Subscription> subscriptions = new ArrayList<>();
		Connection connection = dbutils.getConnection();

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
		} finally {
			dbutils.closeConnection(connection);
		}

		return subscriptions;
	}

	@Override
	public Subscription[] getAllSubscriptions() throws InvalidIdLengthException, InvalidAmountException {
		List<Subscription> subscriptions = this.getAllSubscriptionsList();
		return subscriptions.toArray(new Subscription[subscriptions.size()]);
	}

	public static void main(String[] args) {
		SubscriptionRepository subscriptionRepository = null;
		try {
			subscriptionRepository = SubscriptionRepositoryImpl.getInstance();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Adding an object
		System.out.println("Adding SUB0000006: ");
		try {
			System.out.println(subscriptionRepository
					.addSubscription(new Subscription("SUB0000006", new java.util.Date(), new java.util.Date(), 400,
							PLAN_STATUS.active, PLAN_TYPE.monthly, PLAN_AUTORENEWAL.yes, "ZEE0000001")));
		} catch (InvalidAmountException | InvalidIdLengthException e) {
			e.printStackTrace();
		}

		// Fetching an object
		System.out.println("Fetching SUB0000006: ");
		try {
			System.out.println(subscriptionRepository.getSubscriptionById("SUB0000006").isPresent());
		} catch (InvalidIdLengthException | InvalidAmountException | IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Modifying an object
		System.out.println("Updating SUB0000006: ");
		try {
			System.out.println(subscriptionRepository.updateSubscriptionById("SUB0000006",
					new Subscription("SUB0000006", new java.util.Date(), new java.util.Date(), 400,
							PLAN_STATUS.inactive, PLAN_TYPE.annual, PLAN_AUTORENEWAL.yes, "ZEE0000001")));
		} catch (InvalidAmountException | InvalidIdLengthException | IdNotFoundException e) {
			e.printStackTrace();
		}

		// Delete an object
		System.out.println("Updating SUB0000006: ");
		try {
			System.out.println(subscriptionRepository.deleteSubscriptionById("SUB0000006"));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}

		// Fetch all subscriptions as list
		System.out.println("Subscription List:");
		try {
			subscriptionRepository.getAllSubscriptionsList().forEach((subscription) -> {
				System.out.println(subscription);
			});
		} catch (InvalidIdLengthException | InvalidAmountException e) {
			e.printStackTrace();
		}

		// Fetch all subscriptions as array
		System.out.println("Subscription Array:");
		try {
			for (Subscription subscription : subscriptionRepository.getAllSubscriptions())
				System.out.println(subscription);
		} catch (InvalidIdLengthException | InvalidAmountException e) {
			e.printStackTrace();
		}

	}
}
