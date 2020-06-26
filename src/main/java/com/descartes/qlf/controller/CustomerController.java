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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {

  @Autowired private CustomerService customerService;

  @Autowired private ProductService productService;

  @GetMapping("/viewproducers")
  public String viewProducers(Model model) {
    List<Customer> listCustomers = customerService.getAllCustomers();
    model.addAttribute("listCustomers", listCustomers.toArray());
    return "viewproducers";
  }

    @PostMapping("/searchresult2")
    public String SearchProducerbyKeyword(
            @RequestParam(name = "keyword", required = true) String keyword,
            Model model){
        List<Customer> listCustomers = new ArrayList<>();
        listCustomers = customerService.getBySearch(keyword.toUpperCase());
        if (listCustomers != null) {
      model.addAttribute("listCustomers", listCustomers.toArray());
      model.addAttribute("erreur","ok");
        }
        else{
            System.out.println(listCustomers);
            model.addAttribute("erreur",listCustomers);
        }
        return "viewproducteurs";
    }

  @GetMapping("/producer")
  public String producer(
      @RequestParam(name = "producerId", required = true) Long producerId, Model model) {

    Customer customer = customerService.getById(producerId);
    List<Product> listProducts = productService.getAllProductByCustomerId(customer.getId());
    model.addAttribute("listProducts", listProducts.toArray());
    model.addAttribute(customer);
    return "producer";
  }
}
