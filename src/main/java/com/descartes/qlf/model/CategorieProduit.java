package com.descartes.qlf.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "categorieProduit")
public class CategorieProduit implements Serializable {

    private long id;
    private int categorie;
    private String nom;

    public CategorieProduit() {
    }

    public CategorieProduit(long id, int categorie, String nom) {
        this.id = id;
        this.categorie = categorie;
        this.nom = nom;
    }

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nomCategorie) {
        this.nom = nom;
    }


}
