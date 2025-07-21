package com.mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookService {

	private final Map<Book, Boolean> bookAvailability;
	{
		bookAvailability = new HashMap<>();
		bookAvailability.put(new Book("1", 200), true);
		bookAvailability.put(new Book("2", 127), true);
		bookAvailability.put(new Book("3", 502), true);
		bookAvailability.put(new Book("4", 309), true);
		bookAvailability.put(new Book("5", 49), true);
	}

	public String findAvailableBookId(RentingRequest rentingRequest) {
		return bookAvailability.entrySet().stream()
				.filter(entry -> entry.getValue()).map(entry -> entry.getKey())
				.filter(book -> book.getPageCount() == rentingRequest.getQuantity())
				.findFirst()
				.map(room -> room.getId())
				.orElseThrow(BusinessException::new);
	}
	
	public final List<Book> getAvailableBooks() {
		return bookAvailability.entrySet().stream()
				.filter(entry -> entry.getValue())
				.map(entry -> entry.getKey())
				.collect(Collectors.toList());
	}
	
	public int getBookCount() {
		return bookAvailability.size();
	}

	public void rentBook(String bookId) {
		Book book = bookAvailability.entrySet().stream()
			.filter(entry -> entry.getKey().getId().equals(bookId) && entry.getValue())
			.findFirst()
			.map(entry -> entry.getKey())
			.orElseThrow(BusinessException::new);
		
		bookAvailability.put(book, false);
	}
	
	public void unRentBook(String bookId) {
		Book book = bookAvailability.entrySet().stream()
			.filter(entry -> entry.getKey().getId().equals(bookId) && !entry.getValue())
			.findFirst()
			.map(entry -> entry.getKey())
			.orElseThrow(BusinessException::new);
		
		bookAvailability.put(book, true);
	}
	
}
