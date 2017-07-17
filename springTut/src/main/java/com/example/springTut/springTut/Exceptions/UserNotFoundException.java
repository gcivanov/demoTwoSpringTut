package com.example.springTut.springTut.Exceptions;

/**
 * Created by root on 7/17/2017.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("cloud not find user '" + userId + "'.");
    }
}
