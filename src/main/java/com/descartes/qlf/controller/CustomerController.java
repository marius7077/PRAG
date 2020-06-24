package com.descartes.qlf.controller;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.model.Product;
import com.descartes.qlf.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/viewproducteurs")
    public String viewproducteurs(Model model) {
        List<Customer> listCustomers = new ArrayList<>();
        listCustomers = customerService.getAllCustomers();
        System.out.println(listCustomers.size());
        model.addAttribute("listCustomers", listCustomers);
        return "viewproducteurs";
    }
}
