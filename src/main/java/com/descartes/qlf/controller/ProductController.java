package com.descartes.qlf.controller;

import com.descartes.qlf.model.Customer;
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

import java.util.ArrayList;
import java.util.List;

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
            Model model) {
        model.addAttribute("name", name);
        model.addAttribute("productCategory", productCategory);
        model.addAttribute("description", description);
        model.addAttribute("price", price);
        model.addAttribute("urlPhoto", urlPhoto);

//        productCategoryService.findByName(productCategory);

        ProductCategory productCategoryRef = productCategoryService.getByName(productCategory);
        System.out.println(customerService.getById(10L).getFirstName());
        Product product = new Product(name, description, price, urlPhoto, productCategoryRef, customerService.getById(10L));
        productService.save(product);
        return "addproduct";
    }

    @GetMapping("/viewproducts")
    public String viewproducts() {
        List<Product> listProducts = new ArrayList<>();
        listProducts = productService.getAllProduct();
        System.out.println(listProducts.size());
        return "viewproducts";
    }

}
