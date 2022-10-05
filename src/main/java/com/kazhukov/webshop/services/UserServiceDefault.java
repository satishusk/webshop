package com.kazhukov.webshop.services;

import com.kazhukov.webshop.data.entities.User;
import com.kazhukov.webshop.controllers.dtos.UserDTO;
import com.kazhukov.webshop.data.exceptions.EntityAlreadyExistsException;
import com.kazhukov.webshop.data.exceptions.EntityNotFoundException;
import com.kazhukov.webshop.data.repositories.UserRepository;
import com.kazhukov.webshop.services.factories.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceDefault implements UserService{
  private final UserRepository userRepository;

  @Autowired
  public UserServiceDefault(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User create(User user) {
    if (userByUsernameIsPresent(user.getUsername())) {
      throw new EntityAlreadyExistsException(user);
    }
    return userRepository.save(user);
  }

  @Override
  public boolean userByUsernameIsPresent(String username) {
    return userRepository.findByUsername(username).isPresent();
  }

  @Override
  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(username));
  }

  @Override
  public User getUserById(long id) {
    return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }


  @Override
  public void delete(long id) {
    userRepository.deleteById(id);
  }

  @Override
  public User edit(long id, User user) {
    User activeUser = getUserById(id);
    activeUser.update(user);
    return activeUser;
  }

  @Override
  public void activateUser(long id) {
    userRepository.activate(id);
  }

  @Override
  public void deactivateUser(long id) {
    userRepository.deactivate(id);
  }

  @Override
  public long count() {
    return userRepository.count();
  }
}
