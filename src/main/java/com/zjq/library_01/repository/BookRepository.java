package com.zjq.library_01.repository;


import com.zjq.library_01.domain.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    @Query(value = "select * from book b join category c on b.category_id=c.id " +
            "where b.ISBN like %?1% and b.name like %?2% and author like %?3% " +
            "and published like %?4% and (?5 is null or category_id=?5) order by b.id",nativeQuery = true)
    List<Book> getBooks(String ISBN,String name,String author,String published,Integer category_id, Pageable pageable);
    @Query(value = "select count(*) from book b join category c on b.category_id=c.id " +
            "where b.ISBN like %?1% and b.name like %?2% and author like %?3% " +
            "and published like %?4% and (?5 is null or category_id=?5)",nativeQuery = true)
    Integer getBooksCount(String ISBN,String name,String author,String published,Integer category_id);
    @Modifying
    @Transactional
    @Query(value="update book set ISBN=:#{#book.ISBN},name=:#{#book.name}," +
            "author=:#{#book.author},price=:#{#book.price},published=:#{#book.published}," +
            "img=:#{#book.img},category_id=:#{#book.category.id} where id=:#{#book.id}")
    int update(@Param("book") Book book);
//    @Modifying
//    @Transactional
//    @Query(value="insert into book(ISBN,name,author,price,published,img,category_id) " +
//            "values(?1,?2,?3,?4,?5,?6,?7)",nativeQuery=true)
//    int add(String ISBN,String name,String author,Double price,String published,String img,Integer category_id);
    @Modifying
    @Transactional
    int deleteBooksByIdIn(List<Integer> ids);
}
