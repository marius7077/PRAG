package com.descartes.qlf.service;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.model.Product;
import com.descartes.qlf.repository.CustomerRepository;
import com.descartes.qlf.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

  @Autowired private ProductRepository productRepository;

  public void save(Product product) {
    productRepository.save(product);
  }

  public Product getById(Long id) {
    Optional<Product> product = productRepository.findById(id);
    return product.orElse(null);
  }
  public void removeById(Long id) {
    productRepository.deleteById(id);
  }
}
