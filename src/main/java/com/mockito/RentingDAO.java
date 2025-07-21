package com.mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RentingDAO {

	private final Map<String, RentingRequest> rentings = new HashMap<>();

	public String save(RentingRequest rentingRequest) {
		String id = UUID.randomUUID().toString();
		rentings.put(id, rentingRequest);
		return id;
	}
	
	public RentingRequest get(String id) {
		return rentings.get(id);
	}
	
	public void delete(String rentId) {
		rentings.remove(rentId);
	}

}
