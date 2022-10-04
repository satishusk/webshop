package com.kazhukov.webshop.data.entities;

import com.kazhukov.webshop.controllers.dtos.RoleDTO;
import com.kazhukov.webshop.controllers.dtos.UserDTO;
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

  @Column(name = "password_hash", length = 1024)
  private String passwordHash;

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
  private boolean active = true;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "image_id")
  private Image avatar;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "id")
  private List<Product> products;

  public User(UserDTO userDTO, Set<Role> roles) {
    this.username = userDTO.getUsername();
    this.passwordHash = userDTO.getPassword();
    this.email = userDTO.getEmail();
    this.roles = roles;
    this.phoneNumber = userDTO.getPhoneNumber();
    this.avatar = new Image(userDTO.getAvatarFile());
  }

  public void update(User user) {
    this.username = user.getUsername();
    this.passwordHash = user.getPasswordHash();
    this.email = user.getEmail();
    this.phoneNumber = user.getPhoneNumber();
    this.avatar = user.getAvatar();
    this.roles = user.getRoles();
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", passwordHash='" + passwordHash + '\'' +
        ", email='" + email + '\'' +
        ", roles=" + roles +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", active=" + active +
        ", products=" + products +
        '}';
  }
}
