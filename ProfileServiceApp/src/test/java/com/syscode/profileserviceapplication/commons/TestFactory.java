package com.syscode.profileserviceapplication.commons;

import com.syscode.profileserviceapplication.student.models.dtos.StudentDTO;
import com.syscode.profileserviceapplication.student.models.entities.Student;

import java.util.UUID;

public class TestFactory {

  public static Student getDefaultStudent() {
    return new Student
        (
            UUID.fromString("a7c65197-bcf5-40e4-ae32-631b3d3160f6"),
            "Default Student",
            "default@email.com"
        );
  }

  public static StudentDTO getDefaultStudentDTO() {
    return new StudentDTO
        (
            UUID.fromString("a7c65197-bcf5-40e4-ae32-631b3d3160f6"),
            "Default Student",
            "default@email.com"
        );
  }

}
