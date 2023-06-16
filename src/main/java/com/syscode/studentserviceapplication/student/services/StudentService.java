package com.syscode.studentserviceapplication.student.services;

import com.syscode.studentserviceapplication.student.models.dtos.StudentDTO;
import com.syscode.studentserviceapplication.student.models.dtos.StudentListDTO;

public interface StudentService {
  StudentListDTO getAll();

  StudentDTO addStudent(StudentDTO studentDTO);

}
