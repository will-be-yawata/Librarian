package com.zjq.library_01;

import com.zjq.library_01.domain.Borrow;
import com.zjq.library_01.repository.BookRepository;
import com.zjq.library_01.repository.BorrowRepository;
import com.zjq.library_01.repository.LibrarianRepository;
import com.zjq.library_01.repository.LogRepository;
import com.zjq.library_01.service.BookService;
import com.zjq.library_01.service.BorrowService;
import com.zjq.library_01.service.LogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@SpringBootTest
class Library01ApplicationTests {
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LibrarianRepository librarianRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private LogRepository logRepository;
    @Test
    void test(){
        Pageable pageable= PageRequest.of(0,2);
        List<Borrow> borrows=borrowRepository.findBorrowsByStudentNumOrderByCreationtimeDesc("1740706151",pageable);
        for (Borrow borrow :
                borrows) {
            System.out.println(borrow);
        }
    }
    @Test
    void testtime(){
        Pageable pageable=PageRequest.of(0,10);
        List<Borrow> borrows=borrowRepository.findBorrowsByDeadlineBeforeAndStatusIs(new Date(),1,pageable);
        for (Borrow borrow :
                borrows) {
            System.out.println(borrow);
        }
    }
}
