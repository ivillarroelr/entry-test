package me.ivillarroelr.peopletechnicaltest.service.impl;

import me.ivillarroelr.peopletechnicaltest.model.Student;
import me.ivillarroelr.peopletechnicaltest.repo.IStudentRepo;
import me.ivillarroelr.peopletechnicaltest.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private IStudentRepo repo;

    @Override
    public Student save(Student student) {
        return repo.save(student);
    }

    @Override
    public Student modify(Student student) {
        return repo.save(student);
    }

    @Override
    public List<Student> listAll() {
        return repo.findAll();
    }

    @Override
    public Page<Student> listAllPaginable(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Student readById(String rut) {
        Optional<Student> op = repo.findById(rut);
        return op.isPresent() ? op.get() : new Student();
    }

    @Override
    public boolean delete(String rut) {
        repo.deleteById(rut);
        return true;
    }

    @Override
    public boolean validateChileanRut(String rut) {
        Pattern pattern = Pattern.compile("^[0-9]+-[0-9kK]{1}$");
        Matcher matcher = pattern.matcher(rut);
        if ( matcher.matches() == false ) return false;
        String[] stringRut = rut.split("-");
        return stringRut[1].toLowerCase().equals(this.validateDV(stringRut[0]));
    }

    private String validateDV(String rut){
        Integer M=0,S=1,T=Integer.parseInt(rut);
        for (;T!=0;T=(int) Math.floor(T/=10))
            S=(S+T%10*(9-M++%6))%11;
        return ( S > 0 ) ? String.valueOf(S-1) : "k";
    }
}
