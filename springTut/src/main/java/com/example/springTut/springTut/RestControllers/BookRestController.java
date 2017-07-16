package com.example.springTut.springTut.RestControllers;

import com.example.springTut.springTut.bookmarks.AccountRepository;
import com.example.springTut.springTut.bookmarks.Bookmark;
import com.example.springTut.springTut.bookmarks.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by root on 7/16/2017.
 */


@RestController
@RequestMapping("/{userId}/bookmarks")
public class BookRestController {

    private final AccountRepository accountRepository;
    private final BookmarkRepository bookmarkRepository;

    @Autowired
    public BookRestController(AccountRepository accountRepository, BookmarkRepository bookmarkRepository) {
        this.accountRepository = accountRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Optional<Bookmark> readBookmarks(@PathVariable String userId) {
        this.validateUser(userId);
        return this.bookmarkRepository.findByAccountUsername(userId);
    }

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

    private void validateUser(String userId) {
        this.accountRepository.findByUsername(userId);
//                .orElse( () -> new Exception(userId) );
    }
}
