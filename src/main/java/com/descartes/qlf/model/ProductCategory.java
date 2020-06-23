package com.descartes.qlf.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_category")
public class ProductCategory implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private int category;
  private String name;

  public ProductCategory() {}

  public ProductCategory(long id, int category, String name) {
    this.id = id;
    this.category = category;
    this.name = name;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
