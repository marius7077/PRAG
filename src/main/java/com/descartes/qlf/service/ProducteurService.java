package com.descartes.qlf.service;

import com.descartes.qlf.model.Adresse;
import com.descartes.qlf.model.Producteur;
import com.descartes.qlf.repository.ProducteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducteurService {

    @Autowired
    private ProducteurRepository producteurRepository;

    public void save(Producteur producteur){
        producteurRepository.save(producteur);
    }
}
