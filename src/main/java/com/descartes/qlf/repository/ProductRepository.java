package com.descartes.qlf.repository;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Optional<Product> findById(Long id);
}
