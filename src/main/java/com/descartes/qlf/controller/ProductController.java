package com.descartes.qlf.controller;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.model.Product;
import com.descartes.qlf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/addproduct")
    public String signUp() {
        return "addproduct";
    }

    @PostMapping("/addproductconfirm")
    public String signUp(
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

        productService.save(new Product(name, description, price, urlPhoto, ));

        return "profil";
    }

}
