package me.ivillarroelr.peopletechnicaltest.repo;

import me.ivillarroelr.peopletechnicaltest.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface IStudentRepo extends JpaRepository<Student,String> {
    Page<Student> findAll(Pageable pageable);
}
