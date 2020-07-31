package com.zjq.library_01.service;

import com.zjq.library_01.domain.Book;
import com.zjq.library_01.domain.Category;
import com.zjq.library_01.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    public List<Book> getBooks(Book book,Pageable pageable){
        validBook(book);
        List<Book> books=bookRepository.getBooks(
                book.getISBN(),
                book.getName(),
                book.getAuthor(),
                book.getPublished(),
                book.getCategory().getId(),
                pageable
        );
        return books;
    }
    public Integer getBooksCount(Book book){
        validBook(book);
        int count=bookRepository.getBooksCount(
                book.getISBN(),
                book.getName(),
                book.getAuthor(),
                book.getPublished(),
                book.getCategory().getId()
        );
        return count;
    }
    public Book findById(Integer id){
        Optional<Book> byId = bookRepository.findById(id);
        return byId.get();
    }
    public int update(Book book){
        if(book.getId()==0) return 0;
        return bookRepository.update(book);
    }
    public Book add(Book book){
        book.setId(0);
        return bookRepository.save(book);
//        return bookRepository.add(book.getISBN(),book.getName()
//                ,book.getAuthor(),book.getPrice(),book.getPublished()
//                ,book.getImg(),book.getCategory().getId());
    }
    public int delete(List<Integer> ids){
        if(ids==null || ids.size()<=0) return 0;
        return bookRepository.deleteBooksByIdIn(ids);
    }
    private void validBook(Book book){
        if(book!=null){
            if(book.getISBN()==null){
                book.setISBN("");
            }
            if(book.getName()==null){
                book.setName("");
            }
            if(book.getAuthor()==null){
                book.setAuthor("");
            }
            if(book.getPublished()==null){
                book.setPublished("");
            }
            if(book.getCategory()==null){
                Category c=new Category();
                book.setCategory(c);
            }
        }
    }
}
