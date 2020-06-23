package com.descartes.qlf.repository;

import com.descartes.qlf.model.Producteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProducteurRepository extends CrudRepository<Producteur, Long> {
    Producteur findByEmail(String email);
}
