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

  public Address(String id, String address) {
    this.id = UUID.fromString(id);
    this.address = address;
  }

}