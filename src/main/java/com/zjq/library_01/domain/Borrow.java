package com.zjq.library_01.domain;

import javax.persistence.*;
import java.util.Date;
@Entity(name="borrow")
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "book_id",referencedColumnName="id")
    private Book book;
    @OneToOne
    @JoinColumn(name = "student_num",referencedColumnName="num")
    private Student student;
    private Date creationtime;
    private Date deadline;
    private int status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(Date creationtime) {
        this.creationtime = creationtime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "id='" + id + '\'' +
                ", book=" + book +
                ", student=" + student +
                ", creationtime=" + creationtime +
                ", deadline=" + deadline +
                ", status=" + status +
                '}';
    }
}
