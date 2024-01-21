package com.ra.service.address;

import com.ra.model.dto.request.AddressRequestAndResponse;
import com.ra.model.entity.Address;
import com.ra.model.entity.User;
import com.ra.repository.AddressRepository;
import com.ra.security.user_principal.UserPrinciple;
import com.ra.service.UserAndRole.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AddressServiceIMPL implements AddressService{
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AddressServiceIMPL.class);

    @Override
    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }
    @Transactional
    @Override
    public void deleteAddressById(Long id) {
        if (findAddressById(id)==null){
            logger.error("ID Address not found in this User");
            throw new RuntimeException();
        }
        addressRepository.deleteByUserAndId(userService.userLogin(),id);
    }

    @Override
    public Page<AddressRequestAndResponse> getAll(Pageable pageable) {
        Page<Address> addresses = addressRepository.findAllByUser(userService.userLogin(),pageable);
        return addresses.map(this::convertAddressToAddressResponse);
    }

    @Override
    public Address findAddressById(Long id) {
        return addressRepository.findByIdAndUser(id,userService.userLogin());
    }

    public Address convertAddressRequestToAddress(AddressRequestAndResponse addressRequestAndResponse){
        return Address.builder()
                .fullAddress(addressRequestAndResponse.getFullAddress())
                .receiveName(addressRequestAndResponse.getReceiveName())
                .phone(addressRequestAndResponse.getPhone())
                .user(userService.userLogin())
                .build();
    }

    public AddressRequestAndResponse convertAddressToAddressResponse(Address address){
        return AddressRequestAndResponse.builder()
                .fullAddress(address.getFullAddress())
                .receiveName(address.getReceiveName())
                .phone(address.getPhone())
                .build();
    }
}
