package com.zjq.library_01.repository;

import com.zjq.library_01.domain.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface LogRepository extends JpaRepository<Log,Integer> {
    List<Log> findAllByOrderByActiontimeDesc(Pageable pageable);
    int countAllBy();
    @Modifying
    @Transactional
    @Query(value="insert into log(librarian_num,action,actiontime,book_id) " +
            "values(?1,?2,?3,?4)",nativeQuery=true)
    int addLog(String num,String action,Date actiontime,Integer book_id);
}
