package com.syscode.student.services;

import com.syscode.student.models.dtos.StudentDTO;
import com.syscode.student.models.dtos.StudentListDTO;
import com.syscode.student.models.entities.Student;
import com.syscode.student.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

  private StudentRepository studentRepository;

  @Override //TODO: test
  public StudentListDTO getAll() {
    List<StudentDTO> studentDTOS = studentRepository.findAll().stream()
        .map(this::convertToStudentDTO)
        .collect(Collectors.toList());
    return new StudentListDTO(studentDTOS);
  }

  @Override
  public StudentDTO addStudent(StudentDTO studentDTO) { //TODO: test
    Student student = studentRepository.save(convertToStudent(studentDTO));
    return convertToStudentDTO(student);
  }

  private StudentDTO convertToStudentDTO(Student student) {
    return new StudentDTO
        (
            student.getId(),
            student.getName(),
            student.getEmail()
        );
  }

  private Student convertToStudent(StudentDTO studentDTO) {
    return new Student(studentDTO.getName(), studentDTO.getEmail());
  }

}
