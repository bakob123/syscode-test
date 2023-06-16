package com.syscode.student.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class StudentDTO {

  private UUID id;
  private String name;
  private String email;

}
