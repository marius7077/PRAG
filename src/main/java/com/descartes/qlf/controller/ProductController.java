package com.descartes.qlf.controller;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.model.Product;
import com.descartes.qlf.service.CustomerService;
import com.descartes.qlf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;




    @GetMapping("/addproduct")
    public String signUp() {
        return "addproduct";
    }

    /*@PostMapping("/addproductconfirm")
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

        productService.save(new Product(name, description, price, urlPhoto));

        return "profil";
    }*/
    @GetMapping("/profil")
    public String profil() {
        return "profil";
    }

    @GetMapping("/viewproducts")
    public String viewproducts() {
//        List<Customer> listCustomers = new ArrayList<>();
//        listCustomers = customerService.getAllCustomers();
//        System.out.println(listCustomers.size());

        List<Product> listProducts = new ArrayList<>();
        listProducts = productService.getAllProduct();
        System.out.println(listProducts.size());


//
//        for (Product product : listProducts) {
//            System.out.println(product.getName());
//        }


        return "viewproducts";
    }
}
