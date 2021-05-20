package com.project1.dscatalog.services.exceptions;

public class EntityNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -1010375202917988742L;

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
