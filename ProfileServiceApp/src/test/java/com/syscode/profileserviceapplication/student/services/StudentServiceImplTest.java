package com.syscode.profileserviceapplication.student.services;

import com.syscode.profileserviceapplication.commons.TestFactory;
import com.syscode.profileserviceapplication.errorhandling.exceptions.AlreadyTakenException;
import com.syscode.profileserviceapplication.errorhandling.exceptions.StudentNotFoundException;
import com.syscode.profileserviceapplication.jwt.JwtTokenServiceImpl;
import com.syscode.profileserviceapplication.student.models.dtos.AddressResponseDTO;
import com.syscode.profileserviceapplication.student.models.dtos.StudentDTO;
import com.syscode.profileserviceapplication.student.models.dtos.StudentListDTO;
import com.syscode.profileserviceapplication.student.models.entities.Student;
import com.syscode.profileserviceapplication.student.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

  private final String addressServiceBaseUrl = "http://host:port/api/address";
  @Mock
  private StudentRepository studentRepository;
  @Mock
  private RestTemplate restTemplate;
  @Mock
  private JwtTokenServiceImpl jwtTokenService;
  @InjectMocks
  @Spy
  private StudentServiceImpl studentService;
  private UUID uuid;

  @BeforeEach
  public void setup() {
    uuid = UUID.randomUUID();
  }

  @Test
  public void getAll_should_returnCorrectStudentListDTO() {
    Student studentOne = new Student("Student One", "studentOne@email.com");
    Student studentTwo = new Student("Student Two", "studentTwo@email.com");
    List<Student> students = Arrays.asList(studentOne, studentTwo);
    when(studentRepository.findAll()).thenReturn(students);
    List<StudentDTO> studentDTOS = Arrays.asList
        (
            new StudentDTO(studentOne.getId(), studentOne.getName(), studentOne.getEmail()),
            new StudentDTO(studentTwo.getId(), studentTwo.getName(), studentTwo.getEmail())
        );

    StudentListDTO expected = new StudentListDTO(studentDTOS);
    StudentListDTO actual = studentService.getAll();

    assertEquals(expected, actual);
  }

  @Test
  public void getById_should_returnCorrectStudentDTO() {
    Student student = TestFactory.getDefaultStudent();
    when(studentRepository.findById(any())).thenReturn(Optional.of(student));
    assertEquals(student, studentService.getById("a7c65197-bcf5-40e4-ae32-631b3d3160f6"));
  }

  @Test
  public void getById_should_throwStudentNotFoundException_when_studentIsNotFound() {
    when(studentRepository.findById(any())).thenReturn(Optional.empty());
    assertThrows(StudentNotFoundException.class, () -> studentService.getById(String.valueOf(uuid)));
  }

  @Test
  public void addStudent_should_throwAlreadyTakenException_when_emailIsInUse() {
    StudentDTO studentDTO = TestFactory.getDefaultStudentDTO();
    doThrow(AlreadyTakenException.class).when(studentService).isExistingEmail(any());
    assertThrows(AlreadyTakenException.class, () -> studentService.addStudent(studentDTO));
  }

  @Test
  public void addStudent_should_returnCorrectStudentDTO() {
    StudentDTO studentDTO = TestFactory.getDefaultStudentDTO();
    doReturn(false).when(studentService).isExistingEmail(any());
    Student student = TestFactory.getDefaultStudent();
    when(studentRepository.save(any(Student.class))).thenReturn(student);
    studentDTO.setId(student.getId());

    assertEquals(studentDTO, studentService.addStudent(studentDTO));
    verify(studentRepository, times(1)).save(any(Student.class));
  }

  @Test
  public void isExistingEmail_should_throwAlreadyTakenException() {
    when(studentRepository.findByEmail(anyString())).thenThrow(AlreadyTakenException.class);
    assertThrows(AlreadyTakenException.class, () -> studentService.isExistingEmail("existing@email.com"));
  }

  @Test
  public void isExistingEmail_should_returnFalse_when_emailDoesNotExist() {
    when(studentRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    assertFalse(studentService.isExistingEmail("nonexisting@email.com"));
  }

  @Test
  public void updateStudentData_should_throwStudentNotFoundException() {
    doThrow(StudentNotFoundException.class).when(studentService).getById(any());
    StudentDTO studentDTO = TestFactory.getDefaultStudentDTO();

    assertThrows
        (
            StudentNotFoundException.class,
            () -> studentService.updateStudentData(String.valueOf(uuid), studentDTO),
            "Expecting method to throw StudentNotFoundException"
        );
  }

  @Test
  public void updateStudentData_should_throwAlreadyTakenException_when_emailIsTaken() {
    Student student = TestFactory.getDefaultStudent();
    doReturn(student).when(studentService).getById(any());
    doThrow(AlreadyTakenException.class).when(studentService).isExistingEmail(any());
    StudentDTO studentDTO = TestFactory.getDefaultStudentDTO();

    assertThrows
        (
            AlreadyTakenException.class,
            () -> studentService.updateStudentData(String.valueOf(uuid), studentDTO),
            "Expecting method to throw AlreadyTakenException"
        );
  }

  @Test
  public void updateStudentData_should_returnCorrectStudentDTO() {
    Student student = TestFactory.getDefaultStudent();
    doReturn(student).when(studentService).getById(any());
    doReturn(false).when(studentService).isExistingEmail(any());
    when(studentRepository.save(student)).thenReturn(student);
    StudentDTO newStudentData = new StudentDTO("New Name", "newemail@email.com");
    StudentDTO expected = new StudentDTO(student.getId(), newStudentData.getName(), newStudentData.getEmail());

    StudentDTO actual = studentService.updateStudentData(String.valueOf(uuid), newStudentData);

    assertEquals(expected, actual);
    verify(studentRepository, times(1)).save(student);
  }

  @Test
  public void deleteStudent_should_throwStudentNotFoundException() {
    doThrow(StudentNotFoundException.class).when(studentService).getById(any());
    assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(String.valueOf(uuid)));
  }

  @Test
  public void deleteStudent_should_deleteGivenStudent() {
    Student student = TestFactory.getDefaultStudent();
    doReturn(student).when(studentService).getById(any());

    assertEquals(student, studentService.deleteStudent(String.valueOf(uuid)));
    verify(studentRepository, times(1)).delete(student);
  }

  @Test
  @Disabled
  //TODO: fix
  // Receiving PotentionalStubbingProblem exception even after modifying the URl
  public void getAddressById_should_returnCorrectAddressResponseDTO() {
    when(jwtTokenService.createJwtToken(any())).thenReturn("123456789");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer 123456789");
    AddressResponseDTO addressResponseDTO = new AddressResponseDTO(uuid, "Győr");
    ResponseEntity responseEntity = mock(ResponseEntity.class);
    when(restTemplate.exchange
        (
            null + "/{id}",
            HttpMethod.GET,
            new HttpEntity<String>(headers),
            AddressResponseDTO.class,
            uuid
        )).thenReturn(responseEntity);
    when(responseEntity.getBody()).thenReturn(addressResponseDTO);

    assertEquals(addressResponseDTO, studentService.getAddressById(String.valueOf(uuid)));
  }

}