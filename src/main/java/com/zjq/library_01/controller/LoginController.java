package com.zjq.library_01.controller;

import com.zjq.library_01.domain.Admin;
import com.zjq.library_01.domain.Librarian;
import com.zjq.library_01.domain.Student;
import com.zjq.library_01.service.AdminService;
import com.zjq.library_01.service.CategoryService;
import com.zjq.library_01.service.LibrarianService;
import com.zjq.library_01.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private LibrarianService librarianService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/stu")
    public String stuLogin(String num, String password, Model model, HttpSession session){
        if(num!=null && password!=null){
            if(num.equals("") || num.length()!=10){
                model.addAttribute("stuError","学号不能为空,且为10位");
                return "login";
            }
            if(password.equals("")){
                model.addAttribute("stuError","密码不能为空");
                return "login";
            }
            Student student=studentService.login(num);
            if(!student.getPassword().equals(password)){
                model.addAttribute("stuError","学号或密码错误");
                return "login";
            }
            session.setAttribute("USER_SESSION",student);
            model.addAttribute("categorys",categoryService.getAll());
            return "stu/index";
        }
        return "login";
    }
    @PostMapping("/lib")
    public String libLogin(String num,String password, Model model, HttpSession session){
        if(num!=null && password!=null){
            if(num.equals("") || num.length()!=4){
                model.addAttribute("libError","工号不能为空,且为4位");
                return "login";
            }
            if(password.equals("")){
                model.addAttribute("libError","密码不能为空");
                return "login";
            }
            Librarian librarian=librarianService.login(num);
            if(!librarian.getPassword().equals(password)){
                model.addAttribute("libError","工号或密码错误");
                return "login";
            }
            if(librarian.getStatus()==2){
                model.addAttribute("libError","用户已被禁用");
                return "login";
            }
            session.setAttribute("USER_SESSION",librarian);
            return "lib/index";
        }
        return "login";
    }
    @PostMapping("/adm")
    public String admLogin(String num,String password, Model model, HttpSession session){
        if(num!=null && password!=null){
            if(num.equals("") || num.length()!=4){
                model.addAttribute("admError","工号不能为空,且为4位");
                return "login";
            }
            if(password.equals("")){
                model.addAttribute("admError","密码不能为空");
                return "login";
            }
            Admin admin=adminService.login(num);
            if(!admin.getPassword().equals(password)){
                model.addAttribute("admError","工号或密码错误");
                return "login";
            }
            session.setAttribute("USER_SESSION",admin);
            return "adm/index";
        }
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }
}
