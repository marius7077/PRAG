package com.descartes.qlf.service;

import com.descartes.qlf.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.descartes.qlf.model.Product;
import java.util.List;

@Service
public class ProductService {

  @Autowired private ProductRepository productRepository;

  public List<Product> getAllProduct(){
    return productRepository.findAll();
  }
}