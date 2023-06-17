package com.syscode.profileserviceapplication.student.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StudentListDTO {

  private List<StudentDTO> students;

}
