package com.ra.controller.User;

import com.ra.model.dto.request.AddressRequestAndResponse;
import com.ra.model.entity.Address;
import com.ra.service.address.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user/account/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping("")
    public ResponseEntity<AddressRequestAndResponse> addAddress(@RequestBody @Valid AddressRequestAndResponse addressRequestAndResponse) {
        Address address = addressService.convertAddressRequestToAddress(addressRequestAndResponse);
        Address addressNew = addressService.addAddress(address);
        return new ResponseEntity<>(addressService.convertAddressToAddressResponse(addressNew), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddressById(id);
        return new ResponseEntity<>("Delete success!", HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllAddress(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sort));
        Page<AddressRequestAndResponse> addresses = addressService.getAll(pageable);
        return new ResponseEntity<>(addresses,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable Long id){
        Address address = addressService.findAddressById(id);
        if (address==null){
            return new ResponseEntity<>("ID not found in this User",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(addressService.convertAddressToAddressResponse(address),HttpStatus.OK);
    }

}
