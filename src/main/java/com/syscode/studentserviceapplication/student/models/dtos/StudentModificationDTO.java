package com.syscode.studentserviceapplication.student.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentModificationDTO {

  private String name;
  private String email;

}
