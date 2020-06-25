package com.descartes.qlf.controller;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.model.Product;
import com.descartes.qlf.service.CustomerService;
import com.descartes.qlf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired private ProductService productService;


    @GetMapping("/viewproducteurs")
    public String viewproducteurs(Model model) {
        List<Customer> listCustomers = new ArrayList<>();
        listCustomers = customerService.getAllCustomers();
        model.addAttribute("listCustomers", listCustomers.toArray());
        return "viewproducteurs";
    }

    @GetMapping("/producer")
    public String producer(
            @RequestParam(name = "producerId", required = true) Long producerId,
            Model model) {

        Customer customer = customerService.getById(producerId);
        List<Product> listProducts = new ArrayList<>();
        listProducts = productService.getAllProductByCustomerId(customer.getId());
        model.addAttribute("listProducts", listProducts.toArray());
        model.addAttribute(customer);
        return "producer";
    }
}
