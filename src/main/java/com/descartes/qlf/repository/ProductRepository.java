package com.descartes.qlf.repository;

import com.descartes.qlf.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
