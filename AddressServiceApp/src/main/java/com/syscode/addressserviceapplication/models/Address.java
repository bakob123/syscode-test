package com.syscode.addressserviceapplication.models;

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
    this();
    this.address = address;
  }

}
