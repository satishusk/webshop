package com.kazhukov.webshop.data.exceptions;

public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException(Object entity) {
        super("Entity: " + entity + " already exists");
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
