package me.ivillarroelr.techtest.controller;

import me.ivillarroelr.techtest.model.Student;
import me.ivillarroelr.techtest.service.IStudentService;
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
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private IStudentService service;

    @PostMapping()
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        boolean validation = service.validateChileanRut(student.getRut());
        if(validation){
            student.setRut(student.getRut().replace(".",""));
            Student st = service.save(student);
            return new ResponseEntity<>(st, HttpStatus.CREATED);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, student.getRut()+" is not a valid Chilean rut");
        }
    }

    @PutMapping("/{rut}")
    public ResponseEntity<Student> modifyStudent(@PathVariable() String rut,
                                             @Valid @RequestBody Student student) {
        boolean validation = service.validateChileanRut(rut);
        if(validation){
            student.setRut(student.getRut().replace(".",""));
            Student st = service.readById(rut);
            if(st.getRut()==null){
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "rut: "+rut+" was not found in the database");
            } else {
                st = service.modify(student);
                return new ResponseEntity<>(st, HttpStatus.OK);
            }

        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, student.getRut()+" is not a valid Chilean rut");
        }
    }

    @GetMapping("/{rut}")
    public ResponseEntity<Student> findOneStudent(@PathVariable String rut) {
        boolean validation = service.validateChileanRut(rut);
        if(validation){
            rut.replace(".","");
            Student st = service.readById(rut);
            if(st.getRut()==null){
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "rut: "+rut+" was not found in the database");
            } else {
                return new ResponseEntity<>(st, HttpStatus.OK);
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, rut+" is not a valid Chilean rut");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> findAllStudents() {
        List<Student> lst = service.listAll();
        if(lst.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "There are no students yet in the database");
        } else {
            return new ResponseEntity<>(lst,HttpStatus.OK);
        }
    }

    @GetMapping()
    public ResponseEntity<Map<String, Object>> findAllStudentsPaginated(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "5") int size) {
        try {
            List<Student> students;
            Pageable paging = PageRequest.of(page, size);
            Page<Student> pageStudents;
            pageStudents = service.listAllPaginable(paging);
            students = pageStudents.getContent();
            if (students.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map<String, Object> response = new HashMap<>();
            response.put("students", students);
            response.put("currentPage", pageStudents.getNumber());
            response.put("totalItems", pageStudents.getTotalElements());
            response.put("totalPages", pageStudents.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{rut}")
    public ResponseEntity deleteStudent(@PathVariable() String rut) {
        boolean validation = service.validateChileanRut(rut);
        if(validation){
            rut.replace(".","");
            Student st = service.readById(rut);
            if(st.getRut()==null){
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "rut: "+rut+" was not found in the database");
            } else {
                service.delete(rut);
                return new ResponseEntity(HttpStatus.OK);
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, rut+" is not a valid Chilean rut");
        }
    }
}
