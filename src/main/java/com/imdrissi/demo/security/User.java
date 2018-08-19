package com.imdrissi.demo.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "USER")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class User {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @NotNull private String username;

  @NotNull private String password;

  private boolean enabled = true;

  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> authorities;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
