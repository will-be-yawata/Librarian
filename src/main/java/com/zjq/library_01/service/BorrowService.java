package com.zjq.library_01.service;

import com.zjq.library_01.domain.Book;
import com.zjq.library_01.domain.Borrow;
import com.zjq.library_01.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;
    public List<Borrow> findByStudentNum(String num){
        return borrowRepository.findBorrowsByStudentNum(num);
    }
    public List<Borrow> findByBookId(List<Integer> ids, Pageable pageable){
        if(pageable==null) return borrowRepository.findBorrowsByBookIdIn(ids);
        return borrowRepository.findBorrowsByBookIdIn(ids,pageable);
    }
    public int countByBookId(List<Integer> ids){
        return borrowRepository.countBorrowsByBookIdIn(ids);
    }
    public int deleteBorrow(Integer id){
        return borrowRepository.deleteBorrowById(id);
    }
    public Map<String,Object> findBorrows(Borrow borrow,Pageable pageable){
        Map<String,Object> result=new HashMap<>();
        if(borrow!=null){
            int book_id=0;
            String student_num="";
            int status;
            Date deadline=null;
            if(borrow.getBook()!=null){
                book_id=borrow.getBook().getId();
            }
            if(borrow.getStudent()!=null && borrow.getStudent().getNum()!=null){
                    student_num=borrow.getStudent().getNum();
            }
            status=borrow.getStatus();
            deadline=borrow.getDeadline();
            result.put("borrows",borrowRepository.findBorrows(book_id,student_num,status,deadline,pageable));
            result.put("count",borrowRepository.countBorrows(book_id,student_num,status,deadline));
            return result;
        }
        return null;
    }
    public void updateBorrowStatus(Integer id,Integer status){
        Optional<Borrow> byId = borrowRepository.findById(id);
        Borrow borrow=byId.get();
        borrow.setStatus(status);
        borrowRepository.save(borrow);
    }
    public Map<String,Object> getTimeoutList(Date date,Pageable pageable){
        Map<String,Object> result=new HashMap<>();
        result.put("timeouts",borrowRepository.findBorrowsByDeadlineBeforeAndStatusIs(date,1,pageable));
        result.put("count",borrowRepository.countBorrowsByDeadlineBeforeAndStatusIs(date,1));
        return result;
    }
    public Map<String,Object> getGivebackList(Pageable pageable){
        Map<String,Object> result=new HashMap<>();
        result.put("givebacklist",borrowRepository.findBorrowsByStatusIsOrderByDeadline(1,pageable));
        result.put("count",borrowRepository.countBorrowsByStatusIsOrderByDeadline(1));
        return result;
    }
    public boolean findByBIdAndSNum(Integer book_id,String student_num){
        List<Borrow> borrows=borrowRepository.findBorrowsByBookIdAndStudentNum(book_id,student_num);
        if(borrows==null) return false;
        return borrows.size() > 0;
    }
    public int borrowBook(Integer book_id,String student_num){
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.MONTH,curr.get(Calendar.MONTH)+1); //增加一月
        Date deadlineTime=curr.getTime();
        return borrowRepository.insertBorrow(book_id,student_num,new Date(),deadlineTime,0);
    }
    public Map<String,Object> borrowsByNum(String num,Pageable pageable){
        Map<String,Object> result=new HashMap<>();
        result.put("borrows",borrowRepository.findBorrowsByStudentNumOrderByCreationtimeDesc(num,pageable));
        result.put("count",borrowRepository.countBorrowsByStudentNum(num));
        return result;
    }
}