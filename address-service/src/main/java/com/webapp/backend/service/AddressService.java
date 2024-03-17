package com.webapp.backend.service;

import com.webapp.backend.request.AddressRequest;
import com.webapp.backend.responce.AddressResponse;

public interface AddressService {

	public AddressResponse createAddress(AddressRequest createAddressRequest);
	public AddressResponse getById (long id);
}
