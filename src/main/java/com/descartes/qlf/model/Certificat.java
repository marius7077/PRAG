package com.descartes.qlf.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "certificat")
public class Certificat implements Serializable {

    private long id;
    private String idProducteur;
    private String label;

    public Certificat() {
    }

    public Certificat(long id, String idProducteur, String label) {
        this.id = id;
        this.idProducteur = idProducteur;
        this.label = label;
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
