package com.descartes.qlf.service;

import com.descartes.qlf.model.ProductCategory;
import com.descartes.qlf.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService {

  @Autowired private ProductCategoryRepository productCategoryRepository;

  public ProductCategory getByName(String name) {
    return productCategoryRepository.findByName(name);
  }
}
