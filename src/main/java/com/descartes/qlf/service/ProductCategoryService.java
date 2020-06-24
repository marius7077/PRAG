package com.descartes.qlf.service;

import com.descartes.qlf.model.Product;
import com.descartes.qlf.repository.ProductCategoryRepository;
import com.descartes.qlf.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService {

  @Autowired private ProductCategoryRepository productCategoryRepository;


}
