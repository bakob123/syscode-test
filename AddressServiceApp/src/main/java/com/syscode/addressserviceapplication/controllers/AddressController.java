package com.syscode.addressserviceapplication.controllers;

import com.syscode.addressserviceapplication.models.Address;
import com.syscode.addressserviceapplication.services.AddressService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/address")
public class AddressController {

  private static final Logger logger = LoggerFactory.getLogger(AddressController.class);
  private AddressService addressService;
  private static final String requestBaseMessage = "Request received to ";


  @GetMapping("/{studentId}")
  public ResponseEntity<Address> getAddress(@PathVariable String studentId) {
    logger.info(requestBaseMessage + "return address for student with id={}", studentId);
    Address address = addressService.getAddress(studentId, "Győr");
    logger.info("Returning Address: id={}, address={}", address.getId(), address.getAddress());
    return ResponseEntity.status(HttpStatus.OK).body(address);
  }

}
