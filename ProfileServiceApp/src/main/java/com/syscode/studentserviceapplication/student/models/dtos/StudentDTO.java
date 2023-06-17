package com.syscode.studentserviceapplication.student.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

  private UUID id;
  @NotBlank
  @Length(message = "Name must be shorter than 50 characters.", max = 50)
  private String name;
  @NotBlank
  @Email(message = "Please provide a valid email address.", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
  @Length(message = "Email must be shorter than 100 characters.", max = 100)
  private String email;

  public StudentDTO(String name, String email) {
    this.name = name;
    this.email = email;
  }

}
