package com.kazhukov.webshop.exceptions;

import com.kazhukov.webshop.entities.Role;

import javax.persistence.CollectionTable;
import java.util.Collection;
import java.util.Set;

public class TransientEntityException extends RuntimeException{
    public TransientEntityException(Collection<?> collection) {
        super("Entities: " + collection
            + " were in Hibernate transient state, but the detached state was expected");
    }
}
