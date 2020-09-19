package me.ivillarroelr.techtest.service.impl;

import me.ivillarroelr.techtest.model.Course;
import me.ivillarroelr.techtest.repo.ICourseRepo;
import me.ivillarroelr.techtest.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private ICourseRepo repo;

    @Override
    public Course save(Course course) {
        return repo.save(course);
    }

    @Override
    public Course modify(Course course) {
        return repo.save(course);
    }

    @Override
    public List<Course> listAll() {
        return repo.findAll();
    }

    @Override
    public Page<Course> listAllPaginable(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Course readById(String code) {
        Optional<Course> op = repo.findById(code);
        return op.isPresent() ? op.get() : new Course();
    }

    @Override
    public boolean delete(String code) {
        repo.deleteById(code);
        return true;
    }
}
