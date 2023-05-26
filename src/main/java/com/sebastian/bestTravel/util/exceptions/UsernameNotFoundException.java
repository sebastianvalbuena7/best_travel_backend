package com.sebastian.bestTravel.util.exceptions;

public class UsernameNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Username no exist in %s";

    public UsernameNotFoundException(String tableName) {
        super(String.format(ERROR_MESSAGE, tableName));
    }
}