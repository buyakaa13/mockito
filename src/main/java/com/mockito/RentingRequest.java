package com.mockito;

import java.time.LocalDate;
import java.util.Objects;

public class RentingRequest {
	private final String userId;
	private final LocalDate dateFrom;
	private final LocalDate dateTo;
	private final int quantity;
	private final boolean prepaid;
	private String bookId;

	public RentingRequest(String userId, LocalDate dateFrom, LocalDate dateTo, int quantity, boolean prepaid) {
		Objects.requireNonNull(userId, "userId must not be null");
		Objects.requireNonNull(dateFrom, "dateFrom must not be null");
		Objects.requireNonNull(dateTo, "dateTo must not be null");
		if (dateTo.isBefore(dateFrom)) {
			throw new IllegalArgumentException("dateTo cannot be before dateFrom");
		}
		if (quantity < 0) {
			throw new IllegalArgumentException("quantity must be non-negative");
		}
		this.userId = userId;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.quantity = quantity;
		this.prepaid = prepaid;
	}

	public String getUserId() { return userId; }
	public LocalDate getDateFrom() { return dateFrom; }
	public LocalDate getDateTo() { return dateTo; }
	public int getQuantity() { return quantity; }
	public boolean isPrepaid() { return prepaid; }
	public String getBookId() { return bookId; }
	public void setBookId(String bookId) { this.bookId = bookId; }

	@Override
	public int hashCode() {
		return Objects.hash(userId, dateFrom, dateTo, quantity, prepaid, bookId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof RentingRequest other)) return false;
		return Objects.equals(userId, other.userId) &&
				Objects.equals(dateFrom, other.dateFrom) &&
				Objects.equals(dateTo, other.dateTo) &&
				quantity == other.quantity &&
				prepaid == other.prepaid &&
				Objects.equals(bookId, other.bookId);
	}
}