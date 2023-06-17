package com.syscode.profileserviceapplication.student.models.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
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

  public Student() {
    this.id = UUID.randomUUID();
  }

  public Student(String name, String email) {
    this();
    this.name = name;
    this.email = email;
  }

}
