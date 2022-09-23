package com.kazhukov.webshop.services;

import com.kazhukov.webshop.entities.User;
import com.kazhukov.webshop.controllers.dtos.UserDTO;
import com.kazhukov.webshop.exceptions.EntityAlreadyExistsException;
import com.kazhukov.webshop.exceptions.EntityNotFoundException;
import com.kazhukov.webshop.repositories.UserRepository;
import com.kazhukov.webshop.services.factories.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceDefault implements UserService{
  private final UserRepository userRepository;
  private final UserFactory userFactory;

  @Autowired
  public UserServiceDefault(UserRepository userRepository, UserFactory userFactory) {
    this.userRepository = userRepository;
    this.userFactory = userFactory;
  }

  public User create(UserDTO userDTO) {
    User user = userFactory.generate(userDTO);
    if (userByUsernameIsPresent(user.getUsername())) throw new EntityAlreadyExistsException(user);
    return userRepository.save(user);
  }

  public boolean userByUsernameIsPresent(String username) {
    return userRepository.findByUsername(username).isPresent();
  }

  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(username));
  }

  public User getUserById(long id) {
    return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public void delete(long id) {
    userRepository.deleteById(id);
  }

  public User edit(long id, UserDTO userDTO) {
    User activeUser = getUserById(id);
    if (activeUser != null) {
      User newUser = userFactory.generate(userDTO);
      newUser.setId(activeUser.getId());
      activeUser = userRepository.save(newUser);
      return activeUser;
    } else {
      throw new EntityNotFoundException(id);
    }
  }

  public void activateUser(long id) {
    User userById = getUserById(id);
    userById.setActive(true);
    userRepository.save(userById);
    //userRepository.activate(id);
  }

  public void deactivateUser(long id) {
    User userById = getUserById(id);
    userById.setActive(false);
    userRepository.save(userById);
    //userRepository.deactivate(id);
  }

  @Override
  public long count() {
    return userRepository.count();
  }
}
