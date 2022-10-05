package com.kazhukov.webshop.data.exceptions;

import java.util.Collection;

public class TransientEntityException extends RuntimeException{
    public TransientEntityException(Collection<?> collection) {
        super("Entities: " + collection
            + " were in Hibernate transient state, but the detached state was expected");
    }
}
