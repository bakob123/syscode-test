package com.syscode.profileserviceapplication.student.repositories;

import com.syscode.profileserviceapplication.student.models.entities.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends CrudRepository<Student, UUID> {

  @Override
  List<Student> findAll();

  Optional<Student> findByEmail(String email);

}
