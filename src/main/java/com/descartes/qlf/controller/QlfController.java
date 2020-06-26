package com.descartes.qlf.controller;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.model.Product;
import com.descartes.qlf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.List;

@Controller
public class QlfController {

  @Autowired private DataSource dataSource;

  @Autowired private ProductService productService;

  @GetMapping("/")
  public String home() {
    return "index";
  }

  @GetMapping("/index")
  public String index() {
    return "index";
  }

  @GetMapping("/profil")
  public String profil(HttpServletRequest request, Model model) {
    Customer customer = (Customer) request.getSession().getAttribute("customer");
    List<Product> listProducts = productService.getAllProductByCustomerId(customer.getId());
    model.addAttribute("listProducts", listProducts.toArray());
    return "profil";
  }
}
