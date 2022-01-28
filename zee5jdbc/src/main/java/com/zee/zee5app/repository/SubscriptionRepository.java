package com.zee.zee5app.repository;

import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.Subscription;
import com.zee.zee5app.exception.IdNotFoundException;

public interface SubscriptionRepository {
	
	public String addSubscription(Subscription subscription);

	public String updateSubscriptionById(String id, Subscription subscription) throws IdNotFoundException;

	public String deleteSubscriptionById(String id) throws IdNotFoundException;

	public Optional<Subscription> getSubscriptionById(String id) throws IdNotFoundException;

	public List<Subscription> getAllSubscriptionsList();
	
	public Subscription[] getAllSubscriptions();
	
}
