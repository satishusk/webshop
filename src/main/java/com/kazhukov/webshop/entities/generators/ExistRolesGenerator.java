package com.kazhukov.webshop.entities.generators;

import com.kazhukov.webshop.entities.Role;
import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;

import java.util.Set;

public class ExistRolesGenerator implements ValueGenerator<Set<Role>> {
  @Override
  public Set<Role> generateValue(Session session, Object owner) {
    return null;
  }
}
