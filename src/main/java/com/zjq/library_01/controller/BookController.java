package com.zjq.library_01.controller;

import com.zjq.library_01.domain.Book;
import com.zjq.library_01.domain.Category;
import com.zjq.library_01.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @PostMapping("/getBooks")
    public Map<String,Object> bookList(Integer page, Integer size, Book book, Integer category_id){
        Map<String,Object> result=new HashMap<>();
        Category c=new Category();
        c.setId(category_id);
        book.setCategory(c);
        Pageable pageable= PageRequest.of(page-1,size);
        result.put("books",bookService.getBooks(book,pageable));
        result.put("count",bookService.getBooksCount(book));
        result.put("code",0);
        return result;
    }
    @PostMapping("/book/update")
    public boolean update(Book book){
        System.out.println(book);
        return false;
    }
}
