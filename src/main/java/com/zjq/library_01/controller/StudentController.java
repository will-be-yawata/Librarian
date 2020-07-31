package com.zjq.library_01.controller;

import com.zjq.library_01.domain.Book;
import com.zjq.library_01.domain.Category;
import com.zjq.library_01.domain.Student;
import com.zjq.library_01.service.BookService;
import com.zjq.library_01.service.BorrowService;
import com.zjq.library_01.service.CategoryService;
import com.zjq.library_01.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stu")
public class StudentController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private StudentService studentService;
    @GetMapping("/showbook")
    public String showBookList(){
        return "stu/showbook";
    }
    @GetMapping("/showbookcount/{id}")
    @ResponseBody
    public int showBookCount(@PathVariable("id") Integer category_id){
        Book book=new Book();
        Category category=new Category();
        category.setId(category_id);
        book.setCategory(category);
        return bookService.getBooksCount(book);
    }
    @PostMapping("/showbook/{id}")
    @ResponseBody
    public List<Book> getBook(Integer page,Integer size,@PathVariable("id") Integer category_id){
        Book book=new Book();
        Category category=new Category();
        category.setId(category_id);
        book.setCategory(category);
        Pageable pageable= PageRequest.of(page-1,size);
        return bookService.getBooks(book,pageable);
    }
    @GetMapping("/bookDetail/{id}")
    public String bookDetail(@PathVariable("id") Integer id, HttpSession session, Model model){
        System.out.println();
        Student student = (Student) session.getAttribute("USER_SESSION");
        if(student==null){
            model.addAttribute("stuError","请先登录");
            return "login";
        }
        model.addAttribute("book",bookService.findById(id));
        model.addAttribute("isBorrow",borrowService.findByBIdAndSNum(id,student.getNum()));
        return "stu/bookDetail";
    }
    @GetMapping("/borrowBook/{id}")
    @ResponseBody
    public Map<String,Object> borrowBook(@PathVariable("id") Integer book_id, HttpSession session){
        Map<String,Object> result=new HashMap<>();
        if(book_id==0){
            result.put("status",-1);
            result.put("message","传值错误");
            return result;
        }
        Student student= (Student) session.getAttribute("USER_SESSION");
        if(student==null) {
            result.put("status",1);
            result.put("message","请先登录");
            return result;
        }
        boolean isBorrow=borrowService.findByBIdAndSNum(book_id,student.getNum());
        if(isBorrow){
            result.put("status",-1);
            result.put("message","你已经借过了，不能重复借");
            return result;
        }
        result.put("status",0);
        result.put("message",borrowService.borrowBook(book_id,student.getNum()));
        return result;
    }
    @GetMapping("/info")
    public String info(){
        return "stu/info";
    }
    @PostMapping("/updatePassword")
    @ResponseBody
    public Map<String,Object> updatePassword(String password,HttpSession session){
        Map<String,Object> result=new HashMap<>();
        if(password==null || password.equals("")){
            result.put("status",1);
            result.put("message","密码不能为空");
            return result;
        }
        Student student= (Student) session.getAttribute("USER_SESSION");
        if(student==null){
            result.put("status",-1);
            result.put("message","请先登录");
            return result;
        }
        if(student.getPassword().equals(password)){
            result.put("status",1);
            result.put("message","密码与之前一致");
            return result;
        }
        int n=studentService.updatePasswordByNum(student.getNum(),password);
        if(n>0){
            student.setPassword(password);
            session.setAttribute("USER_SESSION",student);
        }
        result.put("status",0);
        result.put("message",n);
        return result;
    }
    @GetMapping("/borrowStatus")
    public String borrowStatus(){
        return "stu/borrowStatus";
    }
    @PostMapping("/borrowStatus")
    @ResponseBody
    public Map<String,Object> borrowStatus(Integer page,Integer size,HttpSession session){
        Pageable pageable= PageRequest.of(page-1,size);
        Student student = (Student) session.getAttribute("USER_SESSION");
        Map<String,Object> result=new HashMap<>(borrowService.borrowsByNum(student.getNum(),pageable));
        result.put("code",0);
        return result;
    }
}
