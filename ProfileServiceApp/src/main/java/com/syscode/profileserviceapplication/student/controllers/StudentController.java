package com.syscode.profileserviceapplication.student.controllers;

import com.syscode.profileserviceapplication.student.models.dtos.AddressResponseDTO;
import com.syscode.profileserviceapplication.student.models.dtos.StudentDTO;
import com.syscode.profileserviceapplication.student.models.dtos.StudentListDTO;
import com.syscode.profileserviceapplication.student.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentController {

  private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
  private static final String requestBaseMessage = "Request received to ";
  @Value("${addressservice.base.url}")
  private String addressServiceBaseUrl;
  @Autowired
  private StudentService studentService;

  @GetMapping
  public ResponseEntity<StudentListDTO> getAllStudents() { //TODO: test
    logger.info(requestBaseMessage + "retrieve all students.");
    StudentListDTO studentListDTO = studentService.getAll();
    logger.info("Returning {} students.", studentListDTO.getStudents().size());
    return ResponseEntity.status(HttpStatus.OK).body(studentListDTO);
  }

  @PostMapping
  public ResponseEntity<StudentDTO> createStudent(@RequestBody @Valid StudentDTO studentDTO) { //TODO: test
    logger.info
        (
            requestBaseMessage + "create student: name={}, email={}", studentDTO.getName(), studentDTO.getEmail()
        );
    StudentDTO createdStudent = studentService.addStudent(studentDTO);
    logger.info("Student created with id={}", createdStudent.getId());
    return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
  }

  @PutMapping("/{id}")
  public ResponseEntity<StudentDTO> updateStudent(@PathVariable String id, @RequestBody @Valid StudentDTO studentDTO) { //TODO: test
    logger.info
        (
            requestBaseMessage + "update id={} student with new_name={}, new_email={}.",
            id,
            studentDTO.getName(),
            studentDTO.getEmail()
        );
    StudentDTO updatedStudentDTO = studentService.updateStudentData(id, studentDTO);
    logger.info("Updated id={} student. Name={}, email={}.", id, studentDTO.getName(), studentDTO.getEmail());
    return ResponseEntity.status(HttpStatus.OK).body(updatedStudentDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteStudent(@PathVariable String id) { //TODO: test
    logger.info(requestBaseMessage + "delete student with id={}", id);
    studentService.deleteStudent(id);
    logger.info("Deleted student with id={}", id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @GetMapping("/addresses/{id}") //TODO: test
  public ResponseEntity<AddressResponseDTO> getAddress(@PathVariable String id) {
    logger.info(requestBaseMessage + "get address for student with id={}", id);
    logger.info("Sending request to {} ", addressServiceBaseUrl + "/" + id);
    AddressResponseDTO addressResponseDTO = studentService.getAddressById(id);
    logger.info("Returning AddressResponseDTO: id={}, address={}", id, addressResponseDTO.getAddress());
    return ResponseEntity.status(HttpStatus.OK).body(addressResponseDTO);
  }

}
