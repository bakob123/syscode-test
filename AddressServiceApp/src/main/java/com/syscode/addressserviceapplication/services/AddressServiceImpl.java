package com.syscode.addressserviceapplication.services;

import com.syscode.addressserviceapplication.models.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

  @Override
  public Address getAddress(String id, String address) {//TODO: test
    return new Address(id, address);
  }

}
