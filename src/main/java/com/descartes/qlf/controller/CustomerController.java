package com.descartes.qlf.controller;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.model.Product;
import com.descartes.qlf.service.CustomerService;
import com.descartes.qlf.service.ProductService;
import com.descartes.qlf.service.GeolocationService;
import com.descartes.qlf.service.TransactionService;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class CustomerController {

  @Autowired private CustomerService customerService;

  @Autowired private TransactionService transactionService;

  @Autowired private ProductService productService;

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

  @GetMapping("/viewproducers")
  public String viewProducers(Model model) {
    List<Customer> listCustomers = customerService.getAllProducers();
    model.addAttribute("listCustomers", listCustomers.toArray());
    return "viewproducers";
  }

  @PostMapping("/searchproducers")
  public String searchProducerByKeyword(
      @RequestParam(name = "keyword") String keyword, Model model) {
    List<Customer> listCustomers = customerService.getBySearch(keyword.toUpperCase());
    if (listCustomers != null) {
      model.addAttribute("listCustomers", listCustomers.toArray());
      model.addAttribute("error", "ok");
    } else {
      model.addAttribute("error", null);
    }
    return "viewproducers";
  }

  @PostMapping("/filterproducers")
  public String filterProducerByKeyword(
      @RequestParam(name = "distance") int distance,
      @RequestParam(name = "ipAddress") String ipAdrress,
      HttpServletRequest request,
      Model model)
      throws IOException, GeoIp2Exception {
    List<Customer> listCustomers =
        customerService.getByDistance(
            distance,
            (Customer) request.getSession().getAttribute("customer"),
            new GeolocationService().getLocation(ipAdrress));
    model.addAttribute("listCustomers", listCustomers.toArray());
    return "viewproducers";
  }

  @GetMapping("/producer")
  public String producer(@RequestParam(name = "producerId") Long producerId, Model model) {
    Customer customer = customerService.getById(producerId);
    List<Product> listProducts = productService.getAllProductByCustomerId(customer.getId());

    StringBuilder sb = new StringBuilder();
    if (customer.getDescription() != null) {
      for (int i = 0; i < customer.getDescription().length(); i++) {
        if (customer.getDescription().charAt(i) == ' ') {
          if (i > 0 && (i % 36 == 0)) {
            sb.append("\n");
          }
        }
        sb.append(customer.getDescription().charAt(i));
      }
    }
    model.addAttribute("descriptionFormat", sb);
    model.addAttribute("listProducts", listProducts.toArray());
    model.addAttribute(customer);
    return "producer";
  }

  @GetMapping("/deleteprofile")
  public String deleteProfile(Model model) {
    return "deleteprofile";
  }

  @PostMapping("/deleteprofileconfirm")
  public String deleteProfile(Model model, HttpServletRequest request) {
    Customer customer = (Customer) request.getSession().getAttribute("customer");
    if (customer.getType().equals("producer")) {
      productService.removeAllProductByCustomerId(customer.getId());
      transactionService.removeAllTransactionByCustomer(customer.getId());
    }
    customerService.delete(customer);
    request.getSession().removeAttribute("customer");
    request.getSession().invalidate();
    return "/index";
  }

  @PostMapping("/editprofile")
  public String editProfile(
      @RequestParam(name = "lastName") String lastName,
      @RequestParam(name = "firstName") String firstName,
      @RequestParam(name = "email") String email,
      @RequestParam(name = "password") String password,
      @RequestParam(name = "confirmPassword") String confirmPassword,
      @RequestParam(name = "address") String address,
      @RequestParam(name = "postalCode") String postalCode,
      @RequestParam(name = "city") String city,
      @RequestParam(name = "phoneNumber") String phoneNumber,
      @RequestParam(name = "company", required = false) String company,
      @RequestParam(name = "description") String description,
      Model model,
      HttpServletRequest request) {
    model.addAttribute("error", null);
    if (password != null && !password.equals(confirmPassword)) {
      model.addAttribute("error", "Les mots de passe ne correspondent pas !");
      return "profile";
    }
    Customer customer = (Customer) request.getSession().getAttribute("customer");
    List<Double> coordinates = customerService.addressToCoordinates(address, postalCode, city);
    customer.setAddress(address);
    customer.setCity(city);
    customer.setFirstName(firstName);
    customer.setEmail(email);
    customer.setLastName(lastName);
    if (password != null && !password.equals("")) {
      customer.setPassword(bCryptPasswordEncoder.encode(password));
    }
    customer.setPhoneNumber(phoneNumber);
    customer.setPostalCode(postalCode);
    customer.setDescription(description);
    if (customer.getType().equals("producer")) {
      customer.setCompany(company);
    }
    customer.setLatitude(coordinates.get(0));
    customer.setLongitude(coordinates.get(1));
    customerService.save(customer);
    List<Product> listProducts = productService.getAllProductByCustomerId(customer.getId());
    model.addAttribute("listProducts", listProducts.toArray());
    model.addAttribute("success", "Les modifications ont été enregistrées !");
    return "profile";
  }
}
