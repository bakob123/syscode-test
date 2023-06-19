package com.syscode.addressserviceapplication.address.services;

import com.syscode.addressserviceapplication.address.models.Address;
import com.syscode.addressserviceapplication.errorhandling.exceptions.InvalidUuidFormatException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressServiceImplTest {

  private AddressServiceImpl addressService;
  private Address expected;
  private UUID uuid;

  @BeforeEach
  public void setup() {
    addressService = new AddressServiceImpl();
    uuid = UUID.fromString("0f14d0ab-9605-4a62-a9e4-5ed26688389b");
  }

  @Test
  public void getAddress_should_returnCorrectAddressObject() {
    expected = new Address(uuid, "testAddress");
    assertEquals(expected, addressService.getAddress("0f14d0ab-9605-4a62-a9e4-5ed26688389b", "testAddress"));
  }

  @Test
  public void getAddress_should_trimInvalidAddressInput() {
    expected = new Address(uuid, "testAddress");
    Address actual = addressService.getAddress("0f14d0ab-9605-4a62-a9e4-5ed26688389b", "testAddress ");
    assertEquals(expected, actual);
  }

  @Test
  public void getAddress_should_throwInvalidUuidException() {
    assertThrows(InvalidUuidFormatException.class, () -> addressService.getAddress("1", "testAddress"));
  }

}