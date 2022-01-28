package com.zee.zee5app.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.Subscription;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.repository.SubscriptionRepository;

public class SubscriptionRepositoryImpl implements SubscriptionRepository {

	private static SubscriptionRepositoryImpl subscriptionRepository = null;
	private List<Subscription> subscriptions = new ArrayList<>();

	private SubscriptionRepositoryImpl() {

	}

	public static SubscriptionRepositoryImpl getInstance() {
		if (subscriptionRepository == null)
			subscriptionRepository = new SubscriptionRepositoryImpl();
		return subscriptionRepository;
	}

	@Override
	public String addSubscription(Subscription subscription) {
		boolean status = subscriptions.add(subscription);
		return status ? "success" : "failed";
	}

	@Override
	public String updateSubscriptionById(String id, Subscription subscription) throws IdNotFoundException {
		for (int i = 0; i < subscriptions.size(); i++)
			if (subscriptions.get(i).getId().equals(id)) {
				subscriptions.set(i, subscription);
				return "success";
			}
		throw new IdNotFoundException("Invalid Id");
	}

	@Override
	public String deleteSubscriptionById(String id) throws IdNotFoundException {
		boolean status = subscriptions.remove(this.getSubscriptionById(id).get());
		return status ? "success" : "failed";
		
	}

	@Override
	public Optional<Subscription> getSubscriptionById(String id) throws IdNotFoundException {
		for (Subscription subscription : subscriptions)
			if (subscription.getId().equals(id))
				return Optional.of(subscription);
		Subscription notFound = null;
		return Optional.of(
				Optional.ofNullable(notFound).orElseThrow(() -> new IdNotFoundException("Invalid Id")));
	}

	@Override
	public List<Subscription> getAllSubscriptionsList() {
		Collections.sort(subscriptions);
		return subscriptions;
	}

	@Override
	public Subscription[] getAllSubscriptions() {
		Collections.sort(subscriptions);
		return subscriptions.toArray(new Subscription[subscriptions.size()]);
	}

}
