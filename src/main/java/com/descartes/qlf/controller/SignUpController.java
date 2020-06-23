package com.descartes.qlf.controller;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Configuration
public class SignUpController {

  @Autowired private CustomerService customerService;

  @GetMapping("/signup")
  public String signUp() {
    return "signup";
  }

  @PostMapping("/signupconfirm")
  public String signUp(
      @RequestParam(name = "lastName", required = true) String lastName,
      @RequestParam(name = "firstName", required = true) String firstName,
      @RequestParam(name = "email", required = true) String email,
      @RequestParam(name = "password", required = true) String password,
      @RequestParam(name = "address", required = true) String address,
      @RequestParam(name = "postalCode", required = true) String postalCode,
      @RequestParam(name = "city", required = true) String city,
      @RequestParam(name = "phoneNumber", required = true) String phoneNumber,
      @RequestParam(name = "type", required = true) String type,
      Model model) {
    if (type.equals("producer")) {
      customerService.save(
          new Customer(
              firstName, lastName, email, password, address, postalCode, city, phoneNumber, "producer"));
    } else {
      customerService.save(
          new Customer(
              firstName, lastName, email, password, address, postalCode, city, phoneNumber, "consumer"));
    }
    return "signup";
  }

  @GetMapping("/login")
  public String logIn() {
    return "login";
  }

  @PostMapping("/loginconfirm")
  public String logIn(
          @RequestParam(name = "email", required = true) String email,
          @RequestParam(name = "password", required = true) String password,
          Model model) {
    Customer customer = customerService.connect(email, password);
    model.addAttribute("firstName", customer.getFirstName());
    model.addAttribute("lastName", customer.getLastName());
    return "greeting";
  }
}
