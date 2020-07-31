package com.zjq.library_01.service;

import com.zjq.library_01.domain.Librarian;
import com.zjq.library_01.repository.LibrarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

@Service
public class LibrarianService {
    @Autowired
    private LibrarianRepository librarianRepository;
    public Librarian login(String num){
        return librarianRepository.findLibrarianByNum(num);
    }
    public Map<String,Object> getAll(Pageable pageable){
        Map<String,Object> result=new HashMap<>();
        result.put("librarians",librarianRepository.findAllByOrderByStatus(pageable));
        result.put("count",librarianRepository.countAllBy());
        return result;
    }
    public int updateStatus(String num,Integer status){
        return librarianRepository.updateStatus(num,status);
    }
    public int addLib(Librarian lib){
        return librarianRepository.addLib(lib.getNum(),lib.getName(),lib.getPassword(),lib.getStatus());
    }
    public int updatePassword(String num,String password){
        return librarianRepository.updatePasswordByNum(num,password);
    }
}
