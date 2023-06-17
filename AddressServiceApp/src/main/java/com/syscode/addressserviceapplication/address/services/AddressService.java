package com.syscode.addressserviceapplication.address.services;

import com.syscode.addressserviceapplication.address.models.Address;

public interface AddressService {
  Address getAddress(String id, String address);

}
