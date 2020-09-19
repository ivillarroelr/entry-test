package me.ivillarroelr.techtest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICRUD<T> {
    T save(T obj);
    T modify(T obj);
    List<T> listAll();
    Page<T> listAllPaginable(Pageable pageable);
    T readById(String id);
    boolean delete(String id);
}
