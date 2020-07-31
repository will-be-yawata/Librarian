package com.zjq.library_01.repository;

import com.zjq.library_01.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AdminRepository extends JpaRepository<Admin,String> {
    Admin findAdminByNum(String num);
    @Modifying
    @Transactional
    @Query(value="update admin set password=?2 where num=?1",nativeQuery = true)
    int updatePasswordByNum(String num,String password);
}
