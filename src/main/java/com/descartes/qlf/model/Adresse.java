package com.descartes.qlf.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "adresse")
public class Adresse implements Serializable {

    private long id;
    private String rue;
    private String num;
    private String codePostal;
    private String ville;
    private String pays;

    public Adresse() {
    }

    public Adresse(long id, String rue, String num, String codePostal, String ville, String pays) {
        this.id = id;
        this.rue = rue;
        this.num = num;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
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
