package me.ivillarroelr.techtest.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Set;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "rut")
    private String rut;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "age")
    @Min(value=18, message="must be equal or greater than 18")
    private int age;

    @ManyToMany
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "student_rut"),
            inverseJoinColumns = @JoinColumn(name = "course_code"))
    Set<Course> courses;

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
