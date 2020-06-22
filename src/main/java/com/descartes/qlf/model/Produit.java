package com.descartes.qlf.model;

public class Produit {

    private int id;
    private String nom;
    private String description;
    private String prix;
    private String urlPhoto;
    private CategorieProduit categorie;
    private Producteur producteur;

    public Produit() {
    }

    public Produit(int id, String nom, String description, String prix, String urlPhoto, CategorieProduit categorie, Producteur producteur) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.urlPhoto = urlPhoto;
        this.categorie = categorie;
        this.producteur = producteur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public CategorieProduit getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieProduit categorie) {
        this.categorie = categorie;
    }

    public Producteur getProducteur() {
        return producteur;
    }

    public void setProducteur(Producteur producteur) {
        this.producteur = producteur;
    }
}
