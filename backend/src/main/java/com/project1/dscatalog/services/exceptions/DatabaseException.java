package com.project1.dscatalog.services.exceptions;

public class DatabaseException extends RuntimeException {
    private static final long serialVersionUID = -3439983441025439632L;

    public DatabaseException(String msg){
        super(msg);
    }
}
