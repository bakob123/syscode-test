package com.syscode.addressserviceapplication.address.services;

import com.syscode.addressserviceapplication.address.models.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

  @Override
  public Address getAddress(String id, String address) {
    address = address.trim();
    return new Address(id, address);
  }

}
