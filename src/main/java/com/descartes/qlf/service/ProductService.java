package com.descartes.qlf.service;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.model.Product;
import com.descartes.qlf.repository.CustomerRepository;
import com.descartes.qlf.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public List<Product> getAllProductByCategoryId(long id){
    return productRepository.findByCategory_Id(id);
  }

}
