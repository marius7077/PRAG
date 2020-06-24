package com.descartes.qlf.repository;

import com.descartes.qlf.model.ProductCategory;
import org.springframework.data.repository.CrudRepository;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {

  ProductCategory findByName(String name);
}
