package com.descartes.qlf.repository;

import com.descartes.qlf.model.ProductCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {

  ProductCategory findByName(String name);

  Optional<ProductCategory> findById(Long id);
}
