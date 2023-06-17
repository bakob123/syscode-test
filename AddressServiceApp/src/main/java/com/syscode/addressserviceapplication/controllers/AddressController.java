package com.syscode.addressserviceapplication.controllers;

import com.syscode.addressserviceapplication.models.Address;
import com.syscode.addressserviceapplication.services.AddressService;
import lombok.AllArgsConstructor;
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

  private AddressService addressService;

  @GetMapping("/{studentId}")
  public ResponseEntity<Address> getAddress(@PathVariable String studentId) {
    return ResponseEntity.status(HttpStatus.OK).body(addressService.getAddress(studentId, "Győr"));
  }

}
