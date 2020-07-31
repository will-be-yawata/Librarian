package com.zjq.library_01.repository;

import com.zjq.library_01.domain.Borrow;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow,Integer> {
    List<Borrow> findBorrowsByStudentNum(String student_num);
    List<Borrow> findBorrowsByBookIdIn(List<Integer> book_ids);
    List<Borrow> findBorrowsByBookIdIn(List<Integer> book_ids, Pageable pageable);
    int countBorrowsByBookIdIn(List<Integer> book_ids);
    @Modifying
    @Transactional
    int deleteBorrowById(Integer id);
    @Query(value="select * from borrow bw join book bk on bw.book_id=bk.id join student st on bw.student_num=st.num " +
            "where (?1 = 0 or book_id=?1) and student_num like %?2% and (?3 = -1 or status=?3) " +
            "and (?4 is null or deadline>=date_format(?4,'%Y-%m-%d %H-%i-%S')) order by status",nativeQuery = true)
    List<Borrow> findBorrows(Integer book_id, String student_num, int status, Date deadline, Pageable pageable);
    @Query(value="select count(*) from borrow bw join book bk on bw.book_id=bk.id join student st on bw.student_num=st.num " +
            "where (?1 = 0 or book_id=?1) and student_num like %?2% and (?3 = -1 or status=?3) " +
            "and (?4 is null or deadline>=date_format(?4,'%Y-%m-%d %H-%i-%S'))",nativeQuery = true)
    int countBorrows(Integer book_id, String student_num, int status, Date deadline);
    List<Borrow> findBorrowsByDeadlineBeforeAndStatusIs(Date deadline,Integer status,Pageable pageable);
    int countBorrowsByDeadlineBeforeAndStatusIs(Date deadline,Integer status);
    List<Borrow> findBorrowsByStatusIsOrderByDeadline(Integer status,Pageable pageable);
    int countBorrowsByStatusIsOrderByDeadline(Integer status);
    List<Borrow> findBorrowsByBookIdAndStudentNum(Integer book_id,String student_num);
    @Modifying
    @Transactional
    @Query(value="insert into borrow(book_id,student_num,creationtime,deadline,status) " +
            "values(?1,?2,?3,?4,?5)",nativeQuery=true)
    int insertBorrow(Integer book_id,String student_num,Date creationtime,Date deadline,int status);
    List<Borrow> findBorrowsByStudentNumOrderByCreationtimeDesc(String num,Pageable pageable);
    int countBorrowsByStudentNum(String num);
}