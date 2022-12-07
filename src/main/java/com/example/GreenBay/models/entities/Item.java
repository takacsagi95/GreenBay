package com.example.GreenBay.models.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_id", nullable = false)
  private long id;
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private User user;
  private String name;
  private String description;
  private Integer price;
  private boolean isSellable;

  public Item() {
    this.isSellable = true;
  }

  public Item(User user, String name, String description, Integer price) {
    this.user = user;
    this.name = name;
    this.description = description;
    this.price = price;
    this.isSellable = true;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public boolean isIsSellable() {
    return isSellable;
  }

  public void setIsSellable(boolean sellable) {
    this.isSellable = sellable;
  }

}
