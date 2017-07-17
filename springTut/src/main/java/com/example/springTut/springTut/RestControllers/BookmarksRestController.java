package com.example.springTut.springTut.RestControllers;

import com.example.springTut.springTut.Exceptions.UserNotFoundException;
import com.example.springTut.springTut.bookmarks.Account;
import com.example.springTut.springTut.bookmarks.AccountRepository;
import com.example.springTut.springTut.bookmarks.Bookmark;
import com.example.springTut.springTut.bookmarks.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

/**
 * Created by root on 7/16/2017.
 */


@RestController
@RequestMapping("/{userId}/bookmarks")
public class BookmarksRestController {

    private final AccountRepository accountRepository;
    private final BookmarkRepository bookmarkRepository;

    @Autowired
    public BookmarksRestController(AccountRepository accountRepository, BookmarkRepository bookmarkRepository) {
        this.accountRepository = accountRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Bookmark> readBookmarks(@PathVariable String userId) {
        this.validateUser(userId);
        return this.bookmarkRepository.findByAccountUsername(userId);
    }

    /*
    @RequestMapping(method = RequestMethod.POST, path = "/add")
    ResponseEntity<?> addUser(@PathVariable String userId, @RequestHeader(value = "password") String password) {
        Account addedAccount = this.accountRepository.save( new Account(userId, password) );
        this.accountRepository.flush();
        System.out.println(" we are in add user method! +userId:" + userId + " password:" + password);
        return ResponseEntity.notFound().build();
    }
    */
        @RequestMapping(method = RequestMethod.POST)
        ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input) {
        this.validateUser(userId);

        return this.accountRepository.findByUsername(userId)
                .map( account -> {
                    Bookmark result = bookmarkRepository.save(new Bookmark(account, input.url, input.description));
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();
                    return ResponseEntity.created(location).build();
                } )
                .orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
    Bookmark readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId) {
        this.validateUser(userId);
        return this.bookmarkRepository.findById(bookmarkId).orElse(new Bookmark(null,"",""));
    }

    private void validateUser(String userId) {
        System.out.println( "! userId:" + userId + " find:" +this.accountRepository.findByUsername(userId));
        this.accountRepository.findByUsername(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
