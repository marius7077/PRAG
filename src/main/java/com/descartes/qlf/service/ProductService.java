package com.descartes.qlf.service;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.descartes.qlf.model.Product;
import java.util.List;

@Service
public class ProductService {

  @Autowired private ProductRepository productRepository;

  public void save(Product product) {
    productRepository.save(product);
}
  public List<Product> getAllProduct(){
    return productRepository.findAll();
  }

  public void removeById(Long id) {
    productRepository.deleteById(id);
  }

  public List<Product> getBySearch(String keyword)
  {
    if (keyword != null) {
      return productRepository.search(keyword);
    }
    else{
      return productRepository.findAll();
    }
  }
}