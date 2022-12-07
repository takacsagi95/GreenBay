package com.example.GreenBay.models.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false)
  private Long id;
  private String username;
  private String password;
  @ManyToMany(fetch = FetchType.EAGER)
  private List<Role> roles;
  private int greenBayDollars;
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Item> items;

  public User() {
  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.roles = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String userName) {
    this.username = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public int getGreenBayDollars() {
    return greenBayDollars;
  }

  public void setGreenBayDollars(int greenBayAmount) {
    this.greenBayDollars = greenBayAmount;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

}
