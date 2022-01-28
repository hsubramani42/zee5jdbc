package com.zee.zee5app;

import java.util.Date;

import com.zee.zee5app.dto.Subscription;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidAmountException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.service.SubscriptionService;
import com.zee.zee5app.service.impl.SubscriptionServiceImpl;

public class TestSubscription {

	public static void main(String[] args) {

		SubscriptionService subscriptionService = SubscriptionServiceImpl.getInstance();
		for (int i = 0; i < 10; i++) {

			try {
				Subscription subscription = new Subscription("type-" + i, new Date(), (i % 2 == 0) ? true : false,
						"country-" + (i % 2), "mode - " + i % 3, (i % 2 == 0) ? true : false, new Date(),
						"00000000" + i, 500f);
				subscriptionService.addSubscription(subscription);
			} catch (InvalidAmountException | InvalidIdLengthException e) {
				e.printStackTrace();
			}
		}

		Subscription subscriptionRef = null;
		// Invalid Id
		int i = 11;
		try {
			subscriptionRef = new Subscription("type-" + i, new Date(), (i % 2 == 0) ? true : false,
					"country-" + (i % 2), "mode - " + i % 3, (i % 2 == 0) ? true : false, new Date(), "0000" + i,
					500f);
		} catch (InvalidAmountException | InvalidIdLengthException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Invalid Amount
		try {
			subscriptionRef = new Subscription("type-" + i, new Date(), (i % 2 == 0) ? true : false,
					"country-" + (i % 2), "mode - " + i % 3, (i % 2 == 0) ? true : false, new Date(), "00000000" + i,
					0f);
		} catch (InvalidAmountException | InvalidIdLengthException e) {
			e.printStackTrace();
		}

		// Valid Object
		try {
			subscriptionRef = new Subscription("type-" + i, new Date(), (i % 2 == 0) ? true : false,
					"country-" + (i % 2), "mode - " + i % 3, (i % 2 == 0) ? true : false, new Date(), "000000122",
					500f);

		} catch (InvalidAmountException | InvalidIdLengthException e) {
			e.printStackTrace();
		}

		// Updating the object
		System.out.print("Updating 000000001: ");
		try {
			System.out.println(subscriptionService.updateSubscriptionById("00000000" + 1, subscriptionRef));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}

		// Deleting the object
		System.out.print("Deleting 000000122: ");
		try {
			System.out.println(subscriptionService.deleteSubscriptionById("000000122"));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}

		// Checking the
		System.out.println("Checking 000000122: ");
		try {
			System.out.println(subscriptionService.getSubscriptionById("000000122"));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Checking 000000002: ");
		try {
			System.out.println(subscriptionService.getSubscriptionById("000000002"));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("Subscriptions List: ");
		subscriptionService.getAllSubscriptionsList().forEach(Subscription -> System.out.println(Subscription));

		System.out.println("Subscriptions Array: ");
		for (Subscription Subscription : subscriptionService.getAllSubscriptions())
			System.out.println(Subscription);

	}

}
