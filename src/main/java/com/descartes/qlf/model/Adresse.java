package com.descartes.qlf.model;


public class Adresse {

    private String rue;
    private String num;
    private String codePostal;
    private String ville;
    private String pays;

    public Adresse() {
    }

    public Adresse(String rue, String num, String codePostal, String ville, String pays) {
        this.rue = rue;
        this.num = num;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}
