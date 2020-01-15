package com.de.redispoc.repository;

import com.de.redispoc.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("studentRepository")
public interface StudentRepository extends CrudRepository<Student, String> {

}
