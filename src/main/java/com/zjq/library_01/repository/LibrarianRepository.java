package com.zjq.library_01.repository;

import com.zjq.library_01.domain.Librarian;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LibrarianRepository extends JpaRepository<Librarian,String> {
    Librarian findLibrarianByNum(String num);
    List<Librarian> findAllByOrderByStatus(Pageable pageable);
    int countAllBy();
    @Modifying
    @Transactional
    @Query(value="update librarian set status=?2 where num=?1",nativeQuery=true)
    int updateStatus(String num,Integer status);
    @Modifying
    @Transactional
    @Query(value="insert into librarian values(?1,?2,?3,?4)",nativeQuery=true)
    int addLib(String num,String name,String password,Integer status);
    @Modifying
    @Transactional
    @Query(value="update librarian set password=?2 where num=?1",nativeQuery = true)
    int updatePasswordByNum(String num,String password);
}
