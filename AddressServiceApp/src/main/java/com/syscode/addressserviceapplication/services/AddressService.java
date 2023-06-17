package com.syscode.addressserviceapplication.services;

import com.syscode.addressserviceapplication.models.Address;

public interface AddressService {
  Address getAddress(String id, String address);

}
