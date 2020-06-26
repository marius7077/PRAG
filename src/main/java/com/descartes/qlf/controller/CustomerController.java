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
      @RequestParam(name = "distance") int distance, HttpServletRequest request, Model model) {
    List<Customer> listCustomers =
        customerService.getByDistance(
            distance, (Customer) request.getSession().getAttribute("customer"));
    if (listCustomers != null) {
      model.addAttribute("listCustomers", listCustomers.toArray());
      model.addAttribute("error", "ok");
    } else {
      model.addAttribute("error", null);
    }
    return "viewproducers";
  }

  @GetMapping("/producer")
  public String producer(@RequestParam(name = "producerId") Long producerId, Model model) {
    Customer customer = customerService.getById(producerId);
    List<Product> listProducts = productService.getAllProductByCustomerId(customer.getId());
    model.addAttribute("listProducts", listProducts.toArray());
    model.addAttribute(customer);
    return "producer";
  }

    @GetMapping("/editprofil")
    public String editprofil(HttpServletRequest request, Model model) {
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        model.addAttribute("CustomerInformation", customerService.getById(customer.getId()));
        return "editprofil";
    }

    @PostMapping("/editprofilresult")
    public String editprofil(
            @RequestParam(name = "lastName") String lastName,
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "confirm_password") String confirm_password,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "postalCode")  String postalCode,
            @RequestParam(name = "city") String city,
            @RequestParam(name = "phoneNumber") String phoneNumber,
            Model model,
            HttpServletRequest request) {
        model.addAttribute("Erreur", null);
        if (!password.equals(confirm_password)) {
            model.addAttribute("Erreur", "Les mots de passe ne correspondent pas !");
            return "editprofilresult";
        }
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        List<Double> coordinates = customerService.addressToCoordinates(address, postalCode, city);
        customer.setAddress(address);
        customer.setCity(city);
        customer.setFirstName(firstName);
        customer.setEmail(email);
        customer.setLastName(lastName);
        customer.setPassword(password);
        customer.setPhoneNumber(phoneNumber);
        customer.setPostalCode(postalCode);
        customerService.save(customer);
        return "editprofilresult";
    }
}
