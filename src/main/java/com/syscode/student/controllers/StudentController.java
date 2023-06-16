package com.syscode.student.controllers;

import com.syscode.student.models.dtos.StudentListDTO;
import com.syscode.student.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

  private StudentService studentService;

  @GetMapping
  public ResponseEntity<StudentListDTO> getAllStudents() {
    return ResponseEntity.status(HttpStatus.OK).body(studentService.getAll());
  }

}
