package com.zjq.library_01.controller;

import com.zjq.library_01.domain.Admin;
import com.zjq.library_01.domain.Librarian;
import com.zjq.library_01.service.AdminService;
import com.zjq.library_01.service.LibrarianService;
import com.zjq.library_01.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/adm")
public class AdminController {
    @Autowired
    private LibrarianService librarianService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private LogService logService;
    @GetMapping("/liblist")
    public String toLiblist(){
        return "adm/liblist";
    }
    @PostMapping("/liblist")
    @ResponseBody
    public Map<String,Object> liblist(Integer page,Integer size){
        Map<String,Object> result=new HashMap<>();
        Pageable pageable= PageRequest.of(page-1,size);
        result.putAll(librarianService.getAll(pageable));
        result.put("code",0);
        return result;
    }
    @GetMapping("/openLib/{num}/{open}")
    @ResponseBody
    public int openLib(@PathVariable("num") String num,@PathVariable("open") boolean open){
        return librarianService.updateStatus(num,open?1:2);
    }
    @GetMapping("/add")
    public String toAddLib(){
        return "adm/add";
    }
    @PostMapping("/add")
    public String addLib(Librarian librarian){
        if(librarian==null) return "login";
        if(librarian.getStatus()==0){
            librarian.setStatus(2);
        }
        if(librarianService.addLib(librarian)>0){
            return "adm/liblist";
        }
        return "adm/add";
    }
    @GetMapping("/log")
    public String toLog(){
        return "adm/log";
    }
    @PostMapping("/log")
    @ResponseBody
    public Map<String,Object> logList(Integer page,Integer size){
        Pageable pageable= PageRequest.of(page-1,size);
        Map<String, Object> result = new HashMap<>(logService.getAll(pageable));
        result.put("code",0);
        return result;
    }
    @GetMapping("/info")
    public String info(){
        return "/adm/info";
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
        Admin admin= (Admin) session.getAttribute("USER_SESSION");
        if(admin==null){
            result.put("status",-1);
            result.put("message","请先登录");
            return result;
        }
        if(admin.getPassword().equals(password)){
            result.put("status",1);
            result.put("message","密码与之前一致");
            return result;
        }
        int n=adminService.updatePassword(admin.getNum(),password);
        if(n>0){
            admin.setPassword(password);
            session.setAttribute("USER_SESSION",admin);
        }
        result.put("status",0);
        result.put("message",n);
        return result;
    }
}
