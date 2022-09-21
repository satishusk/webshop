package com.kazhukov.webshop.entities;

import com.kazhukov.webshop.controllers.dtos.RoleDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq_generator")
  @SequenceGenerator(name = "role_seq_generator", sequenceName = "role_id_seq", allocationSize = 1)
  @Column(name = "role_id")
  private Long id;

  @Column(name = "name", unique = true )
  private String name;

  public Role(String name) {
    this.name = name;
  }

  public Role(RoleDTO roleDto) {
    this.name = roleDto.getName();
  }

}
