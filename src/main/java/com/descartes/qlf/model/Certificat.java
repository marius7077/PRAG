package com.descartes.qlf.model;

public class Certificat {

    private int idProducteur;
    private String label;

    public Certificat() {
    }

    public Certificat(int idProducteur, String label) {
        this.idProducteur = idProducteur;
        this.label = label;
    }

    public int getIdProducteur() {
        return idProducteur;
    }

    public void setIdProducteur(int idProducteur) {
        this.idProducteur = idProducteur;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
