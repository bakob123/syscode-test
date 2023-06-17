package com.syscode.addressserviceapplication.address.services;

import com.syscode.addressserviceapplication.address.models.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressServiceImplTest {

  private AddressServiceImpl addressService;
  private Address expected;

  @BeforeEach
  public void setup() {
    addressService = new AddressServiceImpl();
  }

  @Test
  public void getAddress_should_returnCorrectAddressObject() {
    expected = new Address("0f14d0ab-9605-4a62-a9e4-5ed26688389b", "testAddress");
    assertEquals(expected, addressService.getAddress("0f14d0ab-9605-4a62-a9e4-5ed26688389b", "testAddress"));
  }

  @Test
  public void getAddress_should_trimInvalidAddressInput() {
    expected = new Address("0f14d0ab-9605-4a62-a9e4-5ed26688389b", "testAddress");
    Address actual = addressService.getAddress("0f14d0ab-9605-4a62-a9e4-5ed26688389b", "testAddress ");
    assertEquals(expected, actual);
  }

}