package com.syscode.studentserviceapplication.student.repositories;

import com.syscode.studentserviceapplication.student.models.entities.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends CrudRepository<Student, UUID> {

  @Override
  List<Student> findAll();

  Optional<Student> findByEmail(String email);

}
