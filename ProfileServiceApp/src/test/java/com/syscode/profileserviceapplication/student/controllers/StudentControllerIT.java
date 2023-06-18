package com.syscode.profileserviceapplication.student.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syscode.profileserviceapplication.commons.TestFactory;
import com.syscode.profileserviceapplication.errorhandling.exceptions.StudentNotFoundException;
import com.syscode.profileserviceapplication.student.models.dtos.StudentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class StudentControllerIT {

  private final MediaType contentType = new MediaType
      (
      MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      StandardCharsets.UTF_8
      );
  @Autowired
  private MockMvc mockMvc;
  private ObjectMapper mapper;

  @BeforeEach
  public void setup() {
    this.mapper = new ObjectMapper();
  }

  @Test
  public void getAllStudents_should_correctStudentListDTO() throws Exception {
    mockMvc.perform(get("/api/students"))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.students").isArray())
        .andExpect(jsonPath("$.students").isNotEmpty());
  }

  @Test
  public void createStudent_should_returnStatus400_when_insufficientRequestBodyIsReceived() throws Exception {
    mockMvc.perform(post("/api/students")
            .contentType(contentType)
            .content(mapper.writeValueAsString(new StudentDTO("Student", null))))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("Email is required.")));
  }

  @Test
  public void createStudent_should_returnStatus409_when_emailIsAlreadyInUse() throws Exception {
    mockMvc.perform(post("/api/students")
            .contentType(contentType)
            .content(mapper.writeValueAsString(TestFactory.getDefaultStudentDTO())))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("Email address is already in use.")));
  }

  @Test
  public void createStudent_should_returnStatus404_when_emailIsInvalidFormat() throws Exception {
    StudentDTO studentDTO = new StudentDTO("Invalid Email Student", "email");
    mockMvc.perform(post("/api/students")
            .contentType(contentType)
            .content(mapper.writeValueAsString(studentDTO)))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("Please provide a valid email address.")));
  }

  @Test
  public void createStudent_should_returnCorrectStudentDTO() throws Exception {
    mockMvc.perform(post("/api/students")
            .contentType(contentType)
            .content(mapper.writeValueAsString(new StudentDTO("New Student", "newstudent@email.com"))))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", is("New Student")))
        .andExpect(jsonPath("$.email", is("newstudent@email.com")));
  }

  @Test
  public void updateStudent_should_returnStatus404_when_studentIsNotFound() throws Exception {
    mockMvc.perform(put("/api/students/123b5473-3efe-0000-bbac-0ef0000f000a")
            .contentType(contentType)
            .content(mapper.writeValueAsString(TestFactory.getDefaultStudentDTO())))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is(StudentNotFoundException.MESSAGE)));
  }

  @Test
  public void updateStudent_should_returnStatus409_when_emailIsAlreadyInUse() throws Exception {
    mockMvc.perform(put("/api/students/717b5473-3efe-4291-bbac-3ef2119f096a")
            .contentType(contentType)
            .content(mapper.writeValueAsString(TestFactory.getDefaultStudentDTO())))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("Email address is already in use.")));
  }

  @Test
  public void updateStudent_should_returnUpdatesStudentDTO() throws Exception {
    StudentDTO newStudentData = new StudentDTO("Updated Student", "updated@email.com");
    mockMvc.perform(put("/api/students/717b5473-3efe-4291-bbac-3ef2119f011a")
            .contentType(contentType)
            .content(mapper.writeValueAsString(newStudentData)))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("Updated Student")))
        .andExpect(jsonPath("$.email", is("updated@email.com")));
  }

  @Test
  public void updateStudent_should_returnStatus400_when_emailIsInvalidFormat() throws Exception {
    StudentDTO studentDTO = new StudentDTO("Invalid Email Student", "email");
    mockMvc.perform(put("/api/students/717b5473-3efe-4291-bbac-3ef2119f011a")
            .contentType(contentType)
            .content(mapper.writeValueAsString(studentDTO)))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("Please provide a valid email address.")));
  }

  @Test
  public void deleteStudent_should_returnStatus404_when_studentIsNotFound() throws Exception {
    mockMvc.perform(delete("/api/students/000c5473-3efe-0000-bbac-0ef0000f000a"))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is(StudentNotFoundException.MESSAGE)));
  }

  @Test
  public void deleteStudent_should_returnStatus204_when_studentIsDeleted() throws Exception {
    mockMvc.perform(delete("/api/students/717b5473-3efe-0000-bbac-0ef0000f000a"))
        .andExpect(status().isNoContent());
  }

  @Test
  public void getAddress_should_returnStatus504_when_unableToConnect() throws Exception {
    mockMvc.perform(get("/api/students/addresses/000c5473-3efe-0000-bbac-0ef0000f000a"))
        .andExpect(status().isGatewayTimeout());
  }

  /*
  Could not find the solution for the happy case

  @Test
  public void getAddress_should_returnCorrectAddressResponseDTO() {
   ...
  }
  */

}