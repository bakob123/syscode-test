package com.syscode.studentserviceapplication.student.services;

import com.syscode.studentserviceapplication.errorhandling.exceptions.AlreadyTakenException;
import com.syscode.studentserviceapplication.errorhandling.exceptions.StudentNotFoundException;
import com.syscode.studentserviceapplication.student.models.dtos.AddressResponseDTO;
import com.syscode.studentserviceapplication.student.models.dtos.StudentDTO;
import com.syscode.studentserviceapplication.student.models.dtos.StudentListDTO;
import com.syscode.studentserviceapplication.student.models.entities.Student;
import com.syscode.studentserviceapplication.student.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

  @Autowired
  private StudentRepository studentRepository;
  @Value("${addressservice.base.url}")
  private String addressServiceBaseUrl;
  @Autowired
  private RestTemplate restTemplate;

  @Override //TODO: test
  public StudentListDTO getAll() {
    List<StudentDTO> studentDTOS = studentRepository.findAll().stream()
        .map(this::convertToStudentDTO)
        .collect(Collectors.toList());
    return new StudentListDTO(studentDTOS);
  }

  @Override
  public Student getById(String id) throws StudentNotFoundException {
    return studentRepository.findById(UUID.fromString(id)).orElseThrow(StudentNotFoundException::new);
  }

  @Override
  public StudentDTO addStudent(StudentDTO studentDTO) throws AlreadyTakenException { //TODO: test
    isExistingEmail(studentDTO.getEmail());
    Student student = studentRepository.save(convertToStudent(studentDTO));
    return convertToStudentDTO(student);
  }

  @Override //TODO: test
  public boolean isExistingEmail(String email) throws AlreadyTakenException {
    if (studentRepository.findByEmail(email).isPresent()) {
      throw new AlreadyTakenException("Email address is already in use.");
    }
    return false;
  }

  @Override //TODO: test
  public StudentDTO updateStudentData(String id,
                                      StudentDTO studentDTO) throws StudentNotFoundException, AlreadyTakenException {
    Student student = getById(id);
    isExistingEmail(studentDTO.getEmail());
    student.setName(studentDTO.getName());
    student.setEmail(studentDTO.getEmail());
    return convertToStudentDTO(studentRepository.save(student));
  }

  @Override //TODO: test
  public Student deleteStudent(String id) throws StudentNotFoundException {
    Student student = getById(id);
    studentRepository.delete(student);
    return student;
  }

  @Override
  public AddressResponseDTO getAddressById(String id) { //TODO: test
    return restTemplate.getForObject(addressServiceBaseUrl+ "/{id}", AddressResponseDTO.class, id);
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