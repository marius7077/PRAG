package com.descartes.qlf.controller;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ConnectionController {

  @Autowired private CustomerService customerService;

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

  @GetMapping("/signup")
  public String signup() {
    return "signup";
  }

  @PostMapping("/signupconfirm")
  public String signup(
      @RequestParam(name = "lastName") String lastName,
      @RequestParam(name = "firstName") String firstName,
      @RequestParam(name = "email") String email,
      @RequestParam(name = "password") String password,
      @RequestParam(name = "address") String address,
      @RequestParam(name = "postalCode") String postalCode,
      @RequestParam(name = "city") String city,
      @RequestParam(name = "phoneNumber") String phoneNumber,
      @RequestParam(name = "type") String type,
      Model model) {
    if (customerService.testEmail(email)) {
      List<Double> coordinates = customerService.addressToCoordinates(address, postalCode, city);
      if (type.equals("producer")) {
        customerService.save(
            new Customer(
                firstName,
                lastName,
                email,
                bCryptPasswordEncoder.encode(password),
                address,
                postalCode,
                city,
                phoneNumber,
                coordinates.get(0),
                coordinates.get(1),
                "producer"));
      } else {
        customerService.save(
            new Customer(
                firstName,
                lastName,
                email,
                bCryptPasswordEncoder.encode(password),
                address,
                postalCode,
                city,
                phoneNumber,
                coordinates.get(0),
                coordinates.get(1),
                "consumer"));
      }
      model.addAttribute("firstName", firstName);
      model.addAttribute("lastName", lastName);
      return "signupconfirm";
    } else {
      model.addAttribute("error", "L'adresse email est déjà utilisée !");
      return "signup";
    }
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @PostMapping("/loginconfirm")
  public String login(
      @RequestParam(name = "email") String email,
      @RequestParam(name = "password") String password,
      HttpServletRequest request,
      Model model) {
    Customer customer = customerService.connect(email, password);
    if (customer != null) {
      request.getSession().setAttribute("customer", customer);
      model.addAttribute("firstName", customer.getFirstName());
      model.addAttribute("lastName", customer.getLastName());
      return "index";
    } else {
      model.addAttribute("error", "L'adresse email et le mot de passe ne correspondent pas !");
      return "login";
    }
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request) {
    request.getSession().invalidate();
    return "index";
  }
}
