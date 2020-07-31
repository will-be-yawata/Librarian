package com.zjq.library_01.service;

import com.zjq.library_01.domain.Student;
import com.zjq.library_01.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    public Student login(String num){
        return studentRepository.findStudentByNum(num);
    }
    public int updatePasswordByNum(String num,String password){
        return studentRepository.updatePasswordByNum(num,password);
    }
}
