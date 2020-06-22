package com.descartes.qlf.model;

public class CategorieProduit {

    private int categorie;
    private String nomCategorie;

    public CategorieProduit() {
    }

    public CategorieProduit(int categorie, String nomCategorie) {
        this.categorie = categorie;
        this.nomCategorie = nomCategorie;
    }

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }


}
