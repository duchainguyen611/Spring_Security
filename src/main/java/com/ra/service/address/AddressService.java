package com.ra.service.address;

import com.ra.model.dto.request.AddressRequestAndResponse;
import com.ra.model.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {
    Address addAddress(Address address);
    void deleteAddressById(Long id);
    Page<AddressRequestAndResponse> getAll(Pageable pageable);
    Address findAddressById(Long id);
    Address convertAddressRequestToAddress(AddressRequestAndResponse addressRequestAndResponse);
    AddressRequestAndResponse convertAddressToAddressResponse(Address address);
}
