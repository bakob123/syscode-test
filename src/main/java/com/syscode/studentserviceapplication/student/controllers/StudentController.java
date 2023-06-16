package com.syscode.studentserviceapplication.student.controllers;

import com.syscode.studentserviceapplication.student.models.dtos.StudentDTO;
import com.syscode.studentserviceapplication.student.models.dtos.StudentListDTO;
import com.syscode.studentserviceapplication.student.models.dtos.StudentModificationDTO;
import com.syscode.studentserviceapplication.student.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

  private StudentService studentService;

  @GetMapping
  public ResponseEntity<StudentListDTO> getAllStudents() { //TODO: test
    return ResponseEntity.status(HttpStatus.OK).body(studentService.getAll());
  }

  @PostMapping
  public ResponseEntity<StudentDTO> createStudent(@RequestBody @Valid StudentDTO studentDTO) { //TODO: test
    return ResponseEntity.status(HttpStatus.CREATED).body(studentService.addStudent(studentDTO));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateStudent(@PathVariable String id, @RequestBody @Valid StudentDTO studentDTO) {
    return ResponseEntity.status(HttpStatus.OK).body(studentService.updateStudentData(id, studentDTO));
  }

}
