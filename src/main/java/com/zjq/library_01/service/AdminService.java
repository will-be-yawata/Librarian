package com.zjq.library_01.service;

import com.zjq.library_01.domain.Admin;
import com.zjq.library_01.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    public Admin login(String num){
        return adminRepository.findAdminByNum(num);
    }
    public int updatePassword(String num,String password){
        return adminRepository.updatePasswordByNum(num,password);
    }
}
