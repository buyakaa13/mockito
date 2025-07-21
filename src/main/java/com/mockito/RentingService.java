package com.mockito;

import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class RentingService {

	private final PaymentService paymentService;
	private final BookService bookService;
	private final RentingDAO rentingDAO;
	private final MailSender mailSender;

	private static final double BASE_PRICE_USD = 50.0; // Price per book per day

	public RentingService(PaymentService paymentService, BookService bookService, RentingDAO rentingDAO, MailSender mailSender) {
		this.paymentService = Objects.requireNonNull(paymentService, "paymentService must not be null");
		this.bookService = Objects.requireNonNull(bookService, "bookService must not be null");
		this.rentingDAO = Objects.requireNonNull(rentingDAO, "rentingDAO must not be null");
		this.mailSender = Objects.requireNonNull(mailSender, "mailSender must not be null");
	}

	public int getAvailableBookCount() {
		return bookService.getAvailableBooks().size();
	}

	public double calculatePrice(RentingRequest rentingRequest) {
		Objects.requireNonNull(rentingRequest, "rentingRequest must not be null");
		long days = ChronoUnit.DAYS.between(rentingRequest.getDateFrom(), rentingRequest.getDateTo());
		if (days < 1) {
			throw new IllegalArgumentException("Rental duration must be at least 1 day");
		}
		return BASE_PRICE_USD * rentingRequest.getQuantity() * days;
	}

	public double calculatePriceEuro(RentingRequest rentingRequest) {
		return CurrencyConverter.toEuro(calculatePrice(rentingRequest));
	}

	public String makeBooking(RentingRequest rentingRequest) {
		Objects.requireNonNull(rentingRequest, "rentingRequest must not be null");
		if (rentingRequest.getQuantity() < 1) {
			throw new IllegalArgumentException("Quantity must be at least 1");
		}
		String bookId = rentingRequest.getBookId() != null ? rentingRequest.getBookId() : bookService.findAvailableBookId(rentingRequest);
		if (bookId == null) {
			throw new IllegalStateException("No available book found for the request");
		}
		double price = calculatePrice(rentingRequest);

		String paymentId = null;
		if (rentingRequest.isPrepaid()) {
			paymentId = paymentService.pay(rentingRequest, price);
		}

		RentingRequest updatedRequest = new RentingRequest(
				rentingRequest.getUserId(),
				rentingRequest.getDateFrom(),
				rentingRequest.getDateTo(),
				rentingRequest.getQuantity(),
				rentingRequest.isPrepaid()
		);
		String bookingId = rentingDAO.save(updatedRequest);
		bookService.rentBook(bookId);
		mailSender.sendRentingConfirmation(bookingId);
		return bookingId;
	}

	public void cancelBooking(String id) {
		Objects.requireNonNull(id, "bookingId must not be null");
		RentingRequest request = rentingDAO.get(id);
		if (request == null) {
			throw new IllegalArgumentException("No booking found with ID: " + id);
		}
		if (request.getBookId() != null) {
			bookService.unRentBook(request.getBookId());
		}
		rentingDAO.delete(id);
	}
}