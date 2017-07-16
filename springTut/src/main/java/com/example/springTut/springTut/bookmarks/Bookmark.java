package com.example.springTut.springTut.bookmarks;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by root on 7/16/2017.
 */


@Entity
public class Bookmark {

    @Id
    @GeneratedValue
    private Long id;
    public String url;
    public String description;

    @JsonIgnore
    @ManyToOne
    private Account account;

    //JPA only
    Bookmark() {
    }

    public Bookmark(Account account, String url, String description) {
        this.url = url;
        this.description = description;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public Account getAccount() {
        return account;
    }
}
