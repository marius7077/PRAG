package com.descartes.qlf.service;

import com.descartes.qlf.model.Product;
import com.descartes.qlf.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

  @Autowired private ProductRepository productRepository;

  public void save(Product product) {
    productRepository.save(product);
  }

  public List<Product> getAllProduct() {
    return productRepository.findAll();
  }

  public void removeById(Long id) {
    productRepository.deleteById(id);
  }

  public List<Product> getAllProductByCategoryId(long id) {
    return productRepository.findByCategory_Id(id);
  }

  public List<Product> getAllProductByCustomerId(long id) {
    return productRepository.findByCustomer_Id(id);
  }

  public void removeAllProductByCustomerId(long id) {
    productRepository.deleteAllByCustomer_Id(id);
  }

  public Product getById(Long id) {
    Optional<Product> product = productRepository.findById(id);
    return product.orElse(null);
  }

  public List<Product> getBySearch(String keyword) {
    if (keyword != null) {
      return productRepository.search(keyword);
    } else {
      return productRepository.findAll();
    }
  }
}
