package com.descartes.qlf.service;

import com.descartes.qlf.model.ProductCategory;
import com.descartes.qlf.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCategoryService {

  @Autowired private ProductCategoryRepository productCategoryRepository;

  public ProductCategory getByName(String name) {
    return productCategoryRepository.findByName(name);
  }

  public ProductCategory getById(Long id) {
    Optional<ProductCategory> ProductCategory = productCategoryRepository.findById(id);
    return ProductCategory.orElse(null);
  }
}
