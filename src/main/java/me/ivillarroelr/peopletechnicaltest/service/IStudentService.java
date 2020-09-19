package me.ivillarroelr.peopletechnicaltest.service;

import me.ivillarroelr.peopletechnicaltest.model.Student;

public interface IStudentService extends ICRUD<Student> {

    boolean validateChileanRut(String rut);
}
