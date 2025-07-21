package com.mockito;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentService {

	private final Map<String, Double> payments = new ConcurrentHashMap<>();

	public String pay(RentingRequest rentingRequest, double price) {
		Objects.requireNonNull(rentingRequest, "rentingRequest must not be null");
		Objects.requireNonNull(rentingRequest.getBookId(), "bookId must not be set");
		if (price < 0) {
			throw new IllegalArgumentException("Price cannot be negative");
		}
		long rentalDays = ChronoUnit.DAYS.between(rentingRequest.getDateFrom(), rentingRequest.getDateTo());
		if (price > 200.0 && rentalDays < 3) {
			throw new UnsupportedOperationException("Payments over $200 are not allowed for rentals shorter than 3 days");
		}
		String id = UUID.randomUUID().toString();
		payments.put(id, price);
		return id;
	}
}