package com.descartes.qlf.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product")
public class Product implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;
  private String description;
  private String price;
  private String type;
  private String urlPhoto;

  @OneToOne(targetEntity = ProductCategory.class)
  private ProductCategory category;

  @OneToOne(targetEntity = Customer.class)
  private Customer customer;

  public Product() {}

  public Product(
      String name,
      String description,
      String price,
      String urlPhoto,
      String type,
      ProductCategory category,
      Customer customer) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.type = type;
    this.urlPhoto = urlPhoto;
    this.category = category;
    this.customer = customer;
  }

  public Product(String name, String description, String price,String type, String urlPhoto) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.type = type;
    this.urlPhoto = urlPhoto;
  }

  public String getType() { return type; }

  public void setType(String type) { this.type = type; }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getUrlPhoto() {
    return urlPhoto;
  }

  public void setUrlPhoto(String urlPhoto) {
    this.urlPhoto = urlPhoto;
  }

  public ProductCategory getCategory() {
    return category;
  }

  public void setCategory(ProductCategory category) {
    this.category = category;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }
}
