package com.kazhukov.webshop.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  private String username;
  private String password;
  private String email;
  private String phoneNumber;
  private MultipartFile avatarFile;
  private Set<RoleDTO> roleDTOSet = new HashSet<>(Set.of(new RoleDTO("ROLE_USER")));
  //TODO: Привязаны

  public UserDTO(String username, String password, String email, String phoneNumber, MultipartFile avatarFile) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.avatarFile = avatarFile;
  }
}
