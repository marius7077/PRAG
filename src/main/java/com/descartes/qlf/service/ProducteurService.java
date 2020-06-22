package com.descartes.qlf.service;

import com.descartes.qlf.dto.ProducteurDTO;
import com.descartes.qlf.model.Adresse;
import com.descartes.qlf.model.Producteur;

public class ProducteurService implements IProducteurService {


    @Override
    public Producteur register(ProducteurDTO producteurDTO) {

        Adresse adresse = new Adresse();
        Producteur producteur = new Producteur();

        adresse.setRue(producteurDTO.getRue());
        adresse.setNum(producteurDTO.getNum());
        adresse.setCodePostal(producteurDTO.getCodePostal());
        adresse.setPays(producteurDTO.getPays());
        adresse.setVille(producteurDTO.getVille());

        producteur.setNom(producteurDTO.getNom());
        producteur.setPrenom(producteurDTO.getPrenom());
        producteur.setNom(producteurDTO.getNom());
        producteur.setEmail(producteurDTO.getEmail());
        producteur.setTelephone(producteurDTO.getTelephone());
        producteur.setMotDePasse(producteurDTO.getMotDePasse());
        producteur.setRib(producteurDTO.getRib());
        producteur.setAdresse(adresse);

       // userRepository.save(producteur);

        return null;
    }
}
