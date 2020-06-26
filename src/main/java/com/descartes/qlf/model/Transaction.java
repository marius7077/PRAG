package com.descartes.qlf.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @OneToOne(targetEntity = Customer.class)
  private Customer customer;

  private long dateTransaction;
  private String label;
  private double price;

  public Transaction() {}

  public Transaction(Customer customer, long date, String label, double price) {
    this.customer = customer;
    this.dateTransaction = date;
    this.label = label;
    this.price = price;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public long getDateTransaction() {
    return dateTransaction;
  }

  public void setDateTransaction(long dateTransaction) {
    this.dateTransaction = dateTransaction;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
}
