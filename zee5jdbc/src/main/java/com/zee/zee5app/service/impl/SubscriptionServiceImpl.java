package com.zee.zee5app.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.Subscription;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidAmountException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.repository.SubscriptionRepository;
import com.zee.zee5app.repository.impl.SubscriptionRepositoryImpl;
import com.zee.zee5app.service.SubscriptionService;

public class SubscriptionServiceImpl implements SubscriptionService {

	private SubscriptionRepository subscriptionRepository = null;
	private static SubscriptionService subscriptionService = null;

	private SubscriptionServiceImpl() throws IOException {
		subscriptionRepository = SubscriptionRepositoryImpl.getInstance();
	}

	public static SubscriptionService getInstance() throws IOException {
		if (subscriptionService == null)
			subscriptionService = new SubscriptionServiceImpl();
		return subscriptionService;
	}

	@Override
	public String addSubscription(Subscription subscription) {
		return this.subscriptionRepository.addSubscription(subscription);
	}

	@Override
	public String updateSubscriptionById(String id, Subscription subscription) throws IdNotFoundException {
		return this.subscriptionRepository.updateSubscriptionById(id, subscription);
	}

	@Override
	public String deleteSubscriptionById(String id) throws IdNotFoundException {
		return this.subscriptionRepository.deleteSubscriptionById(id);
	}

	@Override
	public Optional<Subscription> getSubscriptionById(String id)
			throws IdNotFoundException, InvalidIdLengthException, InvalidAmountException {
		return this.subscriptionRepository.getSubscriptionById(id);
	}

	@Override
	public List<Subscription> getAllSubscriptionsList() throws InvalidIdLengthException, InvalidAmountException {
		return this.subscriptionRepository.getAllSubscriptionsList();
	}

	@Override
	public Subscription[] getAllSubscriptions() throws InvalidIdLengthException, InvalidAmountException {
		return this.subscriptionRepository.getAllSubscriptions();
	}
}