package com.syscode.profileserviceapplication.student.services;

import com.syscode.profileserviceapplication.errorhandling.exceptions.AlreadyTakenException;
import com.syscode.profileserviceapplication.errorhandling.exceptions.StudentNotFoundException;
import com.syscode.profileserviceapplication.student.models.dtos.AddressResponseDTO;
import com.syscode.profileserviceapplication.student.models.dtos.StudentDTO;
import com.syscode.profileserviceapplication.student.models.dtos.StudentListDTO;
import com.syscode.profileserviceapplication.student.models.entities.Student;

public interface StudentService {
  StudentListDTO getAll();

  Student getById(String id) throws StudentNotFoundException;

  StudentDTO addStudent(StudentDTO studentDTO) throws AlreadyTakenException;

  boolean isExistingEmail(String email) throws AlreadyTakenException;

  StudentDTO updateStudentData(String id, StudentDTO studentDTO) throws StudentNotFoundException, AlreadyTakenException;

  Student deleteStudent(String id) throws StudentNotFoundException;

  AddressResponseDTO getAddressById(String id);

}
