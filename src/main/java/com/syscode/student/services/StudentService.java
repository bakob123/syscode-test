package com.syscode.student.services;

import com.syscode.student.models.dtos.StudentDTO;
import com.syscode.student.models.dtos.StudentListDTO;

public interface StudentService {
  StudentListDTO getAll();

  StudentDTO addStudent(StudentDTO studentDTO);

}
