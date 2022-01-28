package com.zee.zee5app.service.impl;


import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.Subscription;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.repository.impl.SubscriptionRepositoryImpl;
import com.zee.zee5app.service.SubscriptionService;

public class SubscriptionServiceImpl implements SubscriptionService {
	
	private SubscriptionRepositoryImpl subscriptionRepository = SubscriptionRepositoryImpl.getInstance();
	private static SubscriptionServiceImpl subscriptionService = null;

	private SubscriptionServiceImpl() {

	}

	public static SubscriptionServiceImpl getInstance() {
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
	public Optional<Subscription> getSubscriptionById(String id) throws IdNotFoundException {
		return this.subscriptionRepository.getSubscriptionById(id);
	}

	@Override
	public List<Subscription> getAllSubscriptionsList() {
		return this.subscriptionRepository.getAllSubscriptionsList();
	}

	@Override
	public Subscription[] getAllSubscriptions() {
		return this.subscriptionRepository.getAllSubscriptions();
	}
}