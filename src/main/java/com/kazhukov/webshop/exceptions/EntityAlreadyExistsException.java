package com.kazhukov.webshop.exceptions;

import com.kazhukov.webshop.entities.Role;

public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException(Object entity) {
        super("Entity: " + entity + " already exists");
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
