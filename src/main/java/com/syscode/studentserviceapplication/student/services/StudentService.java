package com.syscode.studentserviceapplication.student.services;

import com.syscode.studentserviceapplication.errorhandling.exceptions.AlreadyTakenException;
import com.syscode.studentserviceapplication.errorhandling.exceptions.StudentNotFoundException;
import com.syscode.studentserviceapplication.student.models.dtos.StudentDTO;
import com.syscode.studentserviceapplication.student.models.dtos.StudentListDTO;

public interface StudentService {
  StudentListDTO getAll();

  StudentDTO addStudent(StudentDTO studentDTO) throws AlreadyTakenException;

  boolean isExistingEmail(String email) throws AlreadyTakenException;

  StudentDTO updateStudentData(String id, StudentDTO studentDTO) throws StudentNotFoundException, AlreadyTakenException;

}
