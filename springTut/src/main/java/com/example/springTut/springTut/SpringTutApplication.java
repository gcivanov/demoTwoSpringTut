package com.example.springTut.springTut;

import com.example.springTut.springTut.bookmarks.Account;
import com.example.springTut.springTut.bookmarks.AccountRepository;
import com.example.springTut.springTut.bookmarks.Bookmark;
import com.example.springTut.springTut.bookmarks.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@SpringBootApplication
public class SpringTutApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTutApplication.class, args);
	}

	@Bean
	CommandLineRunner init(AccountRepository accountRepository, BookmarkRepository bookmarkRepository) {
        return (evt) -> Arrays.asList(
                "jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
                .forEach( a -> {
                    System.out.println(" +++ in the bean");
                    Account account = accountRepository.save(new Account( a, "password"));
                    bookmarkRepository.save(new Bookmark(account,"http://bookmark.com/1/" + a, "A description"));
                    bookmarkRepository.save(new Bookmark(account,"http://bookmark.com/2/" + a, "A description"));
                });
    }

    @Component
    class TestConsole implements CommandLineRunner {
        @Override
        public void run(String... strings) throws Exception {
            System.out.println( " -- accounts:" );
            for( Account a : accountRepository.findAll() )
            {
                System.out.println( a.toString() );
            }
            System.out.println(" -- bookmarks:");
            for( Bookmark b : bookmarkRepository.findAll() )
            {
                System.out.println(b.toString());
            }
            System.out.println(" -- END;");
        }
        @Autowired AccountRepository accountRepository;
        @Autowired BookmarkRepository bookmarkRepository;
    }
}
