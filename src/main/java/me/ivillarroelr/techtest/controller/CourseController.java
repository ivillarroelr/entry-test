package me.ivillarroelr.techtest.controller;

import me.ivillarroelr.techtest.model.Course;
import me.ivillarroelr.techtest.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private ICourseService service;

    @PostMapping()
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) {
        Course cs = service.save(course);
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Course> modifyCourse(@PathVariable() String code,
                                                 @Valid @RequestBody Course course) {
        Course cs = service.readById(code);
        if(cs.getCode()==null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "course code: "+code+" was not found in the database");
        } else {
            cs = service.modify(course);
            return new ResponseEntity<>(cs,HttpStatus.OK);
        }

    }

    @GetMapping(value = "/{code}")
    public ResponseEntity<Course> findOneCourse(@PathVariable String code) {
        Course cs = service.readById(code);
        if(cs.getCode()==null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "course code: "+code+" was not found in the database");
        } else {
            return new ResponseEntity<>(cs, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Course>> findAllCourses() {
        List<Course> lst = service.listAll();
        if(lst.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "There are no courses yet in the database");
        } else {
            return new ResponseEntity<>(lst,HttpStatus.OK);
        }
    }

    @GetMapping()
    public ResponseEntity<Map<String, Object>> findAllCoursesPaginated(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "5") int size) {
        try {
            List<Course> courses;
            Pageable paging = PageRequest.of(page, size);
            Page<Course> pageCourses;
            pageCourses = service.listAllPaginable(paging);
            courses = pageCourses.getContent();
            if (courses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map<String, Object> response = new HashMap<>();
            response.put("courses", courses);
            response.put("currentPage", pageCourses.getNumber());
            response.put("totalItems", pageCourses.getTotalElements());
            response.put("totalPages", pageCourses.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{code}")
    public ResponseEntity deleteCourse(@PathVariable() String code) {
        Course cs = service.readById(code);
        if(cs.getCode()==null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "course code: "+code+" was not found in the database");
        } else {
            service.delete(cs.getCode());
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}

