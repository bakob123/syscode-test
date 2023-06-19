package com.syscode.addressserviceapplication.address.models;

import lombok.Data;

import java.util.UUID;

@Data
public class Address {

  private UUID id;
  private String address;

  public Address() {
    this.id = UUID.randomUUID();
  }

  public Address(UUID id, String address) {
    this.id = id;
    this.address = address;
  }

}
