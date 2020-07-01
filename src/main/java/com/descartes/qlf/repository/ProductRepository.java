package com.descartes.qlf.repository;

import com.descartes.qlf.model.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

  @Override
  List<Product> findAll();

  @Query("SELECT p FROM Product p WHERE CONCAT(UPPER(p.name), UPPER(p.description)) LIKE %?1%")
  List<Product> search(String keyword);

  Optional<Product> findById(Long id);

  List<Product> findByCategory_Id(Long id);

  List<Product> findByCustomer_Id(Long id);

  @Transactional
  @Modifying
  @Query("DELETE FROM Product p WHERE p.customer.id = :id")
  void deleteAllByCustomer_Id(@Param("id") long id);
}
