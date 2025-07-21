package com.mockito;

import java.util.Objects;

public class Book {
	private final String id;
	private final int pageCount;

	public Book(String id, int pageCount) {
		this.id = id;
		this.pageCount = pageCount;
	}

	public String getId() { return id; }
	public int getPageCount() { return pageCount; }

	@Override
	public int hashCode() {
		return Objects.hash(id, pageCount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Book other)) return false;
		return pageCount == other.pageCount && Objects.equals(id, other.id);
	}
}
