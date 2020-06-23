package com.descartes.qlf.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "produit")
public class Produit implements Serializable {

  private long id;
  private String nom;
  private String description;
  private String prix;
  private String urlPhoto;
  private CategorieProduit categorie;
  private Customer customer;

  public Produit() {}

  public Produit(
      long id,
      String nom,
      String description,
      String prix,
      String urlPhoto,
      CategorieProduit categorie,
      Customer customer) {
    this.id = id;
    this.nom = nom;
    this.description = description;
    this.prix = prix;
    this.urlPhoto = urlPhoto;
    this.categorie = categorie;
    this.customer = customer;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPrix() {
    return prix;
  }

  public void setPrix(String prix) {
    this.prix = prix;
  }

  public String getUrlPhoto() {
    return urlPhoto;
  }

  public void setUrlPhoto(String urlPhoto) {
    this.urlPhoto = urlPhoto;
  }

  @OneToOne
  @JoinColumn(name = "categorieProduit_id", nullable = false)
  public CategorieProduit getCategorie() {
    return categorie;
  }

  public void setCategorie(CategorieProduit categorie) {
    this.categorie = categorie;
  }

  @OneToOne
  @JoinColumn(name = "producteur_id", nullable = false)
  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }
}
