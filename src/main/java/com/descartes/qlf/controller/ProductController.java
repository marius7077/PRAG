package com.descartes.qlf.controller;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.model.Product;
import com.descartes.qlf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        productService.save(new Product(name, description, price, urlPhoto ));

        return "profil";
    }
    @GetMapping("/removeproduct")
    public String removeProduct() {
        return "removeproduct";
    }

    @PostMapping("/removeproductconfirm")
    public String removeProduct(
            @RequestParam(name = "id", required = true) Long id,
            Model model) {
        model.addAttribute("id", id);
        productService.removeById(id);
        return "removeproduct";
    }

    @GetMapping("/search")
    public String SearchProductbyKeyword() {
        return "search";
    }
    @PostMapping("/searchresult")
    public String SearchProductbyKeyword(
            @RequestParam(name = "keyword", required = true) String keyword,
            Model model){
        List<Product> listProducts = new ArrayList<>();
        listProducts = productService.getBySearch(keyword);
        System.out.println(listProducts.toArray());
        model.addAttribute("ListProducts", listProducts.toArray());
        return "searchresult";
    }


}
