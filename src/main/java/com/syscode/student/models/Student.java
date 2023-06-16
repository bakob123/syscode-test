package com.syscode.student.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "students")
public class Student {

  @Id
  @GeneratedValue(generator = "uuid4")
  private UUID id;
  @NotBlank
  @Column(length = 50)
  private String name;
  @NotBlank
  @Column(length = 100, unique = true)
  private String email;

  public Student(String name, String email) {
    this.name = name;
    this.email = email;
  }

}
