package com.example.springTut.springTut.bookmarks;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 7/16/2017.
 */

@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    public String password;
    public String username;


    @OneToMany(mappedBy = "account")
    private Set<Bookmark> bookmarks = new HashSet<>();

    //JPA only
    Account() {
    }

    public Account(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return username;
    }

    public Set<Bookmark> getBookmarks() {
        return bookmarks;
    }
}
