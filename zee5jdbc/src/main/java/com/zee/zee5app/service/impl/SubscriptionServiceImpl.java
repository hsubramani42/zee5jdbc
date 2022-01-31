package com.zee.zee5app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zee.zee5app.dto.Subscription;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidAmountException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.repository.SubscriptionRepository;
import com.zee.zee5app.service.SubscriptionService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

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