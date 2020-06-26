package com.descartes.qlf.repository;

import com.descartes.qlf.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

  @Override
  List<Product> findAll();

  @Query("SELECT p FROM Product p WHERE CONCAT(p.name, p.description) LIKE %?1%")
  List<Product> search(String keyword);

  Optional<Product> findById(Long id);

  List<Product> findByCategory_Id(Long id);

  List<Product> findByCustomer_Id(Long id);
}
