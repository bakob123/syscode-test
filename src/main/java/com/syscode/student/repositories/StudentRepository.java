package com.syscode.student.repositories;

import com.syscode.student.models.entities.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends CrudRepository<Student, UUID> {

  @Override
  List<Student> findAll();

}
