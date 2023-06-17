package com.syscode.addressserviceapplication.address.controllers;

import com.syscode.addressserviceapplication.address.models.Address;
import com.syscode.addressserviceapplication.address.services.AddressService;
import com.syscode.addressserviceapplication.jwt.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
public class AddressController {

  private static final Logger logger = LoggerFactory.getLogger(AddressController.class);
  private static final String requestBaseMessage = "Request received to ";
  @Autowired
  private AddressService addressService;
  @Autowired
  private JwtTokenService jwtTokenService;


  @GetMapping("/{studentId}") //TODO: test
  public ResponseEntity<Address> getAddress(@PathVariable String studentId,
                                            @RequestHeader("Authorization") String authorization) {
    logger.info(requestBaseMessage + "return address for student with id={}", studentId);
    jwtTokenService.validate(authorization);
    Address address = addressService.getAddress(studentId, "Győr");
    logger.info("Returning Address: id={}, address={}", address.getId(), address.getAddress());
    return ResponseEntity.status(HttpStatus.OK).body(address);
  }

}
