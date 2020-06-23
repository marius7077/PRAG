package com.descartes.qlf.service;

import com.descartes.qlf.model.Adresse;
import com.descartes.qlf.model.Producteur;
import com.descartes.qlf.repository.AdresseRepository;
import com.descartes.qlf.repository.ProducteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdresseService {

    @Autowired
    private AdresseRepository adresseRepository;


    public void save(Adresse adresse){
        adresseRepository.save(adresse);
    }
}
