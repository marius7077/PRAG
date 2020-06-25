package com.descartes.qlf.repository;

import com.descartes.qlf.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Optional<Product> findById(Long id);

    @Override
    List<Product> findAll();

    List<Product> findByCategory_Id(Long id);

}
