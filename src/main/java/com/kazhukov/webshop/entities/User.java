package com.kazhukov.webshop.entities;

import com.kazhukov.webshop.controllers.dtos.UserDTO;
import com.kazhukov.webshop.exceptions.TransientEntityException;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_generator")
  @SequenceGenerator(name = "user_seq_generator", sequenceName = "user_id_seq")
  @Column(name = "user_id")
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "password", length = 1024)
  private String password;

  @Column(name = "email")
  private String email;

  @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "active")
  private boolean active;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "image_id")
  private Image avatar;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "id")
  private List<Product> products;

  public User(UserDTO userDto, Set<Role> roles) {
    this.username = userDto.getUsername();
    this.password = userDto.getPassword();
    this.email = userDto.getEmail();
    this.phoneNumber = userDto.getPhoneNumber();
    this.avatar = new Image(userDto.getAvatarFile());
    this.roles = requiresNotTransientRoles(roles);
  }

  private Set<Role> requiresNotTransientRoles(Set<Role> roles) {
    Set<Role> supposedlyTransientRoles = findAnyTransientRole(roles);
    if (supposedlyTransientRoles.size() != 0) {
      throw new TransientEntityException(supposedlyTransientRoles);
    }
    return roles;
  }

  private Set<Role> findAnyTransientRole(Set<Role> roles) {
    return roles.stream()
        .filter(role -> role.getId() == null)
        .collect(Collectors.toSet());
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", email='" + email + '\'' +
        ", roles=" + roles +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", active=" + active +
        ", products=" + products +
        '}';
  }
}
