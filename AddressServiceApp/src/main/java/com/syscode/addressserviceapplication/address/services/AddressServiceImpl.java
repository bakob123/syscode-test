package com.syscode.addressserviceapplication.address.services;

import com.syscode.addressserviceapplication.address.models.Address;
import com.syscode.addressserviceapplication.errorhandling.exceptions.InvalidUuidFormatException;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

  @Override
  public Address getAddress(String id, String address) {
    address = address.trim();
    try {
      UUID validatedId = UUID.fromString(id);
      return new Address(validatedId, address);
    } catch (IllegalArgumentException e) {
      throw new InvalidUuidFormatException();
    }
  }

}
