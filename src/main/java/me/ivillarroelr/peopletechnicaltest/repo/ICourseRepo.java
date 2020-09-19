package me.ivillarroelr.peopletechnicaltest.repo;

import me.ivillarroelr.peopletechnicaltest.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface ICourseRepo extends JpaRepository<Course, String> {
    Page<Course> findAll(Pageable pageable);
}
