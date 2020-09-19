package me.ivillarroelr.techtest.service;

import me.ivillarroelr.techtest.model.Student;

public interface IStudentService extends ICRUD<Student> {

    boolean validateChileanRut(String rut);
}
