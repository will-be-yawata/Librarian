package com.zjq.library_01.domain;

import javax.persistence.*;
import java.util.Date;

@Entity(name="log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String action;
    private Date actiontime;
    private String librarian_num;
    private Integer book_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getAction_time() {
        return actiontime;
    }

    public void setAction_time(Date action_time) {
        this.actiontime = action_time;
    }

    public String getLibrarian_num() {
        return librarian_num;
    }

    public void setLibrarian_num(String librarian_num) {
        this.librarian_num = librarian_num;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", actiontime=" + actiontime +
                ", librarian_num='" + librarian_num + '\'' +
                ", book_id=" + book_id +
                '}';
    }
}
