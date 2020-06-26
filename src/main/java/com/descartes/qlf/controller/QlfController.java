package com.descartes.qlf.controller;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.model.Product;
import com.descartes.qlf.service.ProductService;
import com.descartes.qlf.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QlfController {

  @Autowired private DataSource dataSource;

  @Autowired private ProductService productService;

  @Autowired private CustomerService customerService;

  @GetMapping("/")
  public String home() {
    return "index";
  }

  @GetMapping("/index")
  public String index() {
    return "index";
  }

  @GetMapping("/profil")
  public String profil(
          HttpServletRequest request,
          Model model) {
    Customer customer = (Customer) request.getSession().getAttribute("customer");
    List<Product> listProducts = productService.getAllProductByCustomerId(customer.getId());
    model.addAttribute("listProducts", listProducts.toArray());
    model.addAttribute("CustomerInformation", customerService.getById(customer.getId()));
    return "profil";
  }

  @GetMapping("/queryparam")
  public String greeting(
          @RequestParam(name = "name", required = false, defaultValue = "Empty Name") String name,
          Model model) {
    model.addAttribute("name", name);
    return "queryparam";
  }

  @GetMapping("/crud")
  public String crud() {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt1 = connection.createStatement();
      stmt1.executeUpdate(
              "INSERT INTO customer(address, city, email, first_name, last_name, password, phone_number, postal_code, rib, type) "
                      + "VALUES ('crud', 'crud', 'crud', 'crud', 'crud', 'crud', 'crud', 'crud', null, 'crud')");
      stmt1.executeUpdate("UPDATE customer SET type='producer' WHERE type='crud'");
      stmt1.executeUpdate(
              "INSERT INTO customer(address, city, email, first_name, last_name, password, phone_number, postal_code, rib, type) "
                      + "VALUES ('crud', 'crud', 'crud', 'crud', 'crud', 'crud', 'crud', 'crud', null, 'crud')");
      stmt1.executeUpdate("DELETE FROM customer WHERE type = 'crud'");
      return "crud";
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return "crud";
    }
  }
}
