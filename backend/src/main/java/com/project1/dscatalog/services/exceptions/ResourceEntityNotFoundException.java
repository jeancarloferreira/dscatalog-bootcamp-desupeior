package com.project1.dscatalog.services.exceptions;

public class ResourceEntityNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -1010375202917988742L;

    public ResourceEntityNotFoundException(String msg) {
        super(msg);
    }
}
