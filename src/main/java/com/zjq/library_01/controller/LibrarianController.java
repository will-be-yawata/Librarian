package com.zjq.library_01.controller;

import com.zjq.library_01.domain.*;
import com.zjq.library_01.service.*;
import com.zjq.library_01.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/lib")
public class LibrarianController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private LibrarianService librarianService;
    @Autowired
    private LogService logService;
    @GetMapping("/booklist")
    public String toBookList(Model model){
        model.addAttribute("categorys",categoryService.getAll());
        return "lib/booklist";
    }
    @GetMapping("/addBook")
    public String toBookAdd(Model model){
        model.addAttribute("categorys",categoryService.getAll());
        return "lib/add";
    }
    @PostMapping("/addBook")
    public String addBook(Book book, Model model,Integer category_id,HttpSession session){
        Category category=new Category();
        category.setId(category_id);
        book.setCategory(category);
        Book book1=bookService.add(book);
        Librarian librarian= (Librarian) session.getAttribute("USER_SESSION");
        Log log=new Log();
        log.setLibrarian_num(librarian.getNum());
        log.setBook_id(book1.getId());
        log.setAction("添加");
        log.setAction_time(new Date());
        logService.addLog(log);
        model.addAttribute("categorys",categoryService.getAll());
        return "lib/booklist";
    }
    @GetMapping("/updateBook/{id}")
    public String updatePage(@PathVariable("id") Integer id, Model model){
        model.addAttribute("book",bookService.findById(id));
        model.addAttribute("categorys",categoryService.getAll());
        return "lib/update";
    }
    @PostMapping("/updateBook")
    public String updateBook(Book book, Model model,Integer category_id,HttpSession session){
        Category category=new Category();
        category.setId(category_id);
        book.setCategory(category);
        bookService.update(book);
        Librarian librarian= (Librarian) session.getAttribute("USER_SESSION");
        Log log=new Log();
        log.setLibrarian_num(librarian.getNum());
        log.setBook_id(book.getId());
        log.setAction("修改");
        log.setAction_time(new Date());
        logService.addLog(log);

        model.addAttribute("categorys",categoryService.getAll());
        return "lib/booklist";
    }
    @PostMapping("/deletePre")
    @ResponseBody
    public Map<String,Object> delete(@RequestBody Integer[] ids,HttpSession session){
        Map<String,Object> result=new HashMap<>();
        result.put("status",-1);
        if(ids==null || ids.length<=0){
            result.put("message","传值错误");
            return result;
        }
        ArrayList<Integer> mids=new ArrayList<>();
        mids.add(ids[0]);
        if(ids.length>1){
            for (int i = 1; i < ids.length; i++) {
                if(!mids.contains(ids[i])) mids.add(ids[i]);
            }
        }
        //ids:可能包含重复book_id
        //mids:无重复的book_id
        //borrow:所有book_id的借阅信息
        //delId:可删除的book_id列表
        //unDelId:不可删除的book_id列表
        //n:实际删除条数
        List<Borrow> borrows=borrowService.findByBookId(mids,null);
        List<Integer> delId=new ArrayList<>();
        List<Integer> unDelId=new ArrayList<>();
        int n=0;
        for (Borrow borrow : borrows) {
            if (!unDelId.contains(borrow.getBook().getId())) {
                unDelId.add(borrow.getBook().getId());
            }
        }
        for (Integer mid : mids) {
            if (!unDelId.contains(mid)) {
                delId.add(mid);
            }
        }
        if(delId.size()>0){
            n=bookService.delete(delId);
            Librarian librarian= (Librarian) session.getAttribute("USER_SESSION");
            for(Integer id:delId){
                Log log=new Log();
                log.setLibrarian_num(librarian.getNum());
                log.setBook_id(id);
                log.setAction("删除");
                log.setAction_time(new Date());
                logService.addLog(log);
            }
        }

        List<Integer> bId=new ArrayList<>();
        for (Borrow b : borrows) {
            bId.add(b.getId());
        }
        result.put("status",0);
        result.put("delId",delId);
        result.put("unDelId",unDelId);
        result.put("n",n);
        result.put("bId",bId);
        return result;
    }
    @GetMapping("/delDetail")
    public String toDelDetail(){
        return "lib/deleteDetail";
    }
    @PostMapping("/delDetail")
    @ResponseBody
    public Map<String,Object> delDetail(@RequestParam(value = "ids[]") Integer[] ids,Integer page, Integer size){
        if(ids==null || ids.length==0) return null;
        Map<String,Object> result=new HashMap<>();
        List<Integer> mids = new ArrayList<>(Arrays.asList(ids));
        Pageable pageable= PageRequest.of(page-1,size);
        result.put("borrows",borrowService.findByBookId(mids,pageable));
        result.put("count",borrowService.countByBookId(mids));
        result.put("code",0);
        return result;
    }
    @GetMapping("/borrowlist")
    public String borrowlist(){
        return "lib/borrowlist";
    }
    @PostMapping("/getBorrows")
    @ResponseBody
    public Map<String,Object> getBorrows(Integer page, Integer size, Borrow borrow,Integer book_id,String student_num){
        if(borrow==null){
            borrow=new Borrow();
        }
        Book book=new Book();
        if(book_id!=null)
            book.setId(book_id);
        Student student=new Student();
        if(student_num!=null)
            student.setNum(student_num);
        borrow.setBook(book);
        borrow.setStudent(student);
        Pageable pageable=PageRequest.of(page-1,size);
        Map<String, Object> result = new HashMap<>(borrowService.findBorrows(borrow, pageable));
        result.put("code",0);
        return result;
    }
    @GetMapping("/updateStatus/{id}/{status}")
    @ResponseBody
    public boolean updateStatus(@PathVariable("id") Integer id,@PathVariable("status") Integer status){
        if(id==null || status ==null) return false;
        borrowService.updateBorrowStatus(id,status);
        return true;
    }
    @GetMapping("/timeout")
    public String timeout(){
        return "lib/timeout";
    }
    @PostMapping("/timeout")
    @ResponseBody
    public Map<String,Object> timeoutList(Integer page, Integer size){
        Pageable pageable=PageRequest.of(page-1,size);
        Map<String, Object> result = new HashMap<>(borrowService.getTimeoutList(new Date(),pageable));
        result.put("code",0);
        return result;
    }
    @GetMapping("/giveback")
    public String giveback(){
        return "lib/giveback";
    }
    @PostMapping("/giveback")
    @ResponseBody
    public Map<String,Object> givbackList(Integer page, Integer size){
        Pageable pageable=PageRequest.of(page-1,size);
        Map<String, Object> result = new HashMap<>(borrowService.getGivebackList(pageable));
        result.put("code",0);
        return result;
    }
    @GetMapping("/giveback/{id}")
    @ResponseBody
    public int delBorrow(@PathVariable("id") Integer id){
        return borrowService.deleteBorrow(id);
    }
    @PostMapping("/uploadImg")
    @ResponseBody
    public Map<String,String> uploadImg(MultipartFile file){
        Map<String,String> result=new HashMap<>();
        result.put("success","false");
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            if (Tool.checkImgFile(originalFilename)) {
                String dirPath=ClassUtils.getDefaultClassLoader().getResource("static/images/").getPath();
                File filePath = new File(dirPath);
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                String newFilename = (new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date())) + "_"
                        + UUID.randomUUID().toString().replaceAll("-", "") + "."
                        + originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length());
                try {
                    file.transferTo(new File(dirPath + newFilename));
                    System.out.println(dirPath + newFilename);
                    result.put("success", "true");
                    result.put("message", newFilename);
                } catch (IllegalStateException | IOException e ) {
                    e.printStackTrace();
                    result.put("message", "上传失败\n" + e.getMessage());
                }
            } else {
                result.put("message", "上传的文件有问题");
            }
        }
        return result;
    }
    @GetMapping("/info")
    public String info(){
        return "lib/info";
    }
    @PostMapping("/updatePassword")
    @ResponseBody
    public Map<String,Object> updatePassword(String password, HttpSession session){
        Map<String,Object> result=new HashMap<>();
        if(password==null || password.equals("")){
            result.put("status",1);
            result.put("message","密码不能为空");
            return result;
        }
        Librarian librarian= (Librarian) session.getAttribute("USER_SESSION");
        if(librarian==null){
            result.put("status",-1);
            result.put("message","请先登录");
            return result;
        }
        if(librarian.getPassword().equals(password)){
            result.put("status",1);
            result.put("message","密码与之前一致");
            return result;
        }
        int n=librarianService.updatePassword(librarian.getNum(),password);
        if(n>0){
            librarian.setPassword(password);
            session.setAttribute("USER_SESSION",librarian);
        }
        result.put("status",0);
        result.put("message",n);
        return result;
    }
}