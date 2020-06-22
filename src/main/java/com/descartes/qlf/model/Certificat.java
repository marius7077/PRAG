package com.descartes.qlf.model;

public class Certificat {

    private String idProducteur;
    private String label;

    public Certificat() {
    }

    public Certificat(String idProducteur, String label) {
        this.idProducteur = idProducteur;
        this.label = label;
    }

    public String getIdProducteur() {
        return idProducteur;
    }

    public void setIdProducteur(String idProducteur) {
        this.idProducteur = idProducteur;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
