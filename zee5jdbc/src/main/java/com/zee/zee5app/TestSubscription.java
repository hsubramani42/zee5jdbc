package com.zee.zee5app;

import java.io.IOException;
import java.util.Random;

import com.zee.zee5app.dto.Subscription;
import com.zee.zee5app.dto.enums.PLAN_AUTORENEWAL;
import com.zee.zee5app.dto.enums.PLAN_STATUS;
import com.zee.zee5app.dto.enums.PLAN_TYPE;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidAmountException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.service.SubscriptionService;
import com.zee.zee5app.service.impl.SubscriptionServiceImpl;

public class TestSubscription {

	public static void main(String[] args) {

		SubscriptionService subscriptionService = null;

		try {
			subscriptionService = SubscriptionServiceImpl.getInstance();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Random r = new Random();
		System.out.print("Adding SUB0000006: ");
		try {
			System.out.println(subscriptionService.addSubscription(
					new Subscription("SUB0000006", new java.util.Date(), new java.util.Date(), 400, PLAN_STATUS.active,
							PLAN_TYPE.monthly, PLAN_AUTORENEWAL.yes, "ZEE000000" + r.nextInt(1, 6))));
		} catch (InvalidAmountException | InvalidIdLengthException e) {
			e.printStackTrace();
		}

		// Checking the
		System.out.print("Checking SUB0000006: ");
		try {
			System.out.println(subscriptionService.getSubscriptionById("SUB0000006").isPresent());
		} catch (IdNotFoundException | InvalidIdLengthException | InvalidAmountException e) {
			e.printStackTrace();
		}

		// Valid Object
		Subscription subscriptionRef = null;
		try {
			subscriptionRef = new Subscription("SUB0000006", new java.util.Date(), new java.util.Date(), 99,
					PLAN_STATUS.inactive, PLAN_TYPE.monthly, PLAN_AUTORENEWAL.no, "ZEE000000" + r.nextInt(1, 6));

		} catch (InvalidAmountException | InvalidIdLengthException e) {
			e.printStackTrace();
		}

		// Updating the object
		System.out.print("Updating SUB0000006: ");
		try {
			System.out.println(subscriptionService.updateSubscriptionById("SUB0000006", subscriptionRef));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}

		// Deleting the object
		System.out.print("Deleting SUB0000006: ");
		try {
			System.out.println(subscriptionService.deleteSubscriptionById("SUB0000006"));
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}

		// Checking the
		System.out.println("Checking SUB0000006: ");
		try {
			System.out.println(subscriptionService.getSubscriptionById("SUB0000006").isPresent());
		} catch (IdNotFoundException | InvalidIdLengthException | InvalidAmountException e) {
			e.printStackTrace();
		}

		System.out.println("Subscriptions List: ");
		try {
			subscriptionService.getAllSubscriptionsList().forEach(Subscription -> System.out.println(Subscription));
		} catch (InvalidIdLengthException | InvalidAmountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Subscriptions Array: ");
		try {
			for (Subscription Subscription : subscriptionService.getAllSubscriptions())
				System.out.println(Subscription);
		} catch (InvalidIdLengthException | InvalidAmountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
