package com.syscode.profileserviceapplication.student.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDTO {

  private UUID id;
  private String address;

}
