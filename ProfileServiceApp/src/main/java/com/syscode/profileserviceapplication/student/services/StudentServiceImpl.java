package com.syscode.profileserviceapplication.student.services;

import com.syscode.profileserviceapplication.errorhandling.exceptions.AlreadyTakenException;
import com.syscode.profileserviceapplication.errorhandling.exceptions.StudentNotFoundException;
import com.syscode.profileserviceapplication.jwt.JwtTokenService;
import com.syscode.profileserviceapplication.student.models.dtos.AddressResponseDTO;
import com.syscode.profileserviceapplication.student.models.dtos.StudentDTO;
import com.syscode.profileserviceapplication.student.models.dtos.StudentListDTO;
import com.syscode.profileserviceapplication.student.models.entities.Student;
import com.syscode.profileserviceapplication.student.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
  @Autowired
  private JwtTokenService jwtTokenService;

  @Override
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
  public StudentDTO addStudent(StudentDTO studentDTO) throws AlreadyTakenException {
    isExistingEmail(studentDTO.getEmail());
    Student student = studentRepository.save(convertToStudent(studentDTO));
    return convertToStudentDTO(student);
  }

  @Override
  public boolean isExistingEmail(String email) throws AlreadyTakenException {
    if (studentRepository.findByEmail(email).isPresent()) {
      throw new AlreadyTakenException("Email address is already in use.");
    }
    return false;
  }

  @Override
  public StudentDTO updateStudentData(String id,
                                      StudentDTO studentDTO) throws StudentNotFoundException, AlreadyTakenException {
    Student student = getById(id);
    isExistingEmail(studentDTO.getEmail());
    student.setName(studentDTO.getName());
    student.setEmail(studentDTO.getEmail());
    return convertToStudentDTO(studentRepository.save(student));
  }

  @Override
  public Student deleteStudent(String id) throws StudentNotFoundException {
    Student student = getById(id);
    studentRepository.delete(student);
    return student;
  }

  @Override
  public AddressResponseDTO getAddressById(String id) {
    String jwtToken = jwtTokenService.createJwtToken(jwtTokenService.createDefaultClaims());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + jwtToken);
    HttpEntity<String> httpEntity = new HttpEntity<>(headers);
    AddressResponseDTO addressResponseDTO = restTemplate.exchange
        (
            addressServiceBaseUrl + "/{id}",
            HttpMethod.GET,
            httpEntity,
            AddressResponseDTO.class,
            id
        ).getBody();
    return addressResponseDTO;
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
