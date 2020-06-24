package com.descartes.qlf.controller;

import com.descartes.qlf.model.Product;
import com.descartes.qlf.model.ProductCategory;
import com.descartes.qlf.service.CustomerService;
import com.descartes.qlf.service.ProductCategoryService;
import com.descartes.qlf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/addproduct")
    public String addProduct() {
        return "addproduct";
    }

    @PostMapping("/addproductconfirm")
    public String addProduct(
            @RequestParam(name = "name", required = true) String name,
            @RequestParam(name = "productCategory", required = true) String productCategory,
            @RequestParam(name = "description", required = true) String description,
            @RequestParam(name = "price", required = true) String price,
            @RequestParam(name = "urlPhoto", required = true) String urlPhoto,
            @RequestParam(name = "picture", required = true) MultipartFile file,
            Model model) {

        try {
            FileInputStream fis = new FileInputStream((File) file);
            ProductCategory productCategoryRef = productCategoryService.getByName(productCategory);
            System.out.println(customerService.getById(1L).getFirstName());
            Product product = new Product(name, description, price, urlPhoto, fis, productCategoryRef, customerService.getById(1L));
            productService.save(product);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        return "addproduct";
    }

}
