package com.descartes.qlf.repository;

import com.descartes.qlf.model.Adresse;
import com.descartes.qlf.model.Producteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AdresseRepository extends CrudRepository<Adresse,Long> {



}
