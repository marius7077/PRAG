package com.descartes.qlf.controller;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ConnectionController {

  @Autowired private CustomerService customerService;

  @GetMapping("/signup")
  public String signUp() {
    return "signup";
  }

  @PostMapping("/signupconfirm")
  public String signUp(
      @RequestParam(name = "lastName") String lastName,
      @RequestParam(name = "firstName") String firstName,
      @RequestParam(name = "email") String email,
      @RequestParam(name = "password") String password,
      @RequestParam(name = "confirm_password") String confirm_password,
      @RequestParam(name = "address") String address,
      @RequestParam(name = "postalCode") String postalCode,
      @RequestParam(name = "city") String city,
      @RequestParam(name = "phoneNumber") String phoneNumber,
      @RequestParam(name = "type") String type,
      Model model) {
    model.addAttribute("Erreur", "");
    if (email.isEmpty() || password.isEmpty() || confirm_password.isEmpty() || lastName.isEmpty() || firstName.isEmpty() || address.isEmpty() || postalCode.isEmpty() || city.isEmpty() || phoneNumber.isEmpty() || type.isEmpty()){
      model.addAttribute("Erreur", "Vous devez remplir les champs avant de valider !");
      return "signup";
    }
    if (!email.matches(
        "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
      model.addAttribute("Erreur", "Vous devez rentrer une adresse email valide !");
      return "signup";
    }
    if (!password.equals(confirm_password)){
      model.addAttribute("Erreur", "Les mots de passe ne correspondent pas !");
      return "signup";
    }
    if (!postalCode.matches("[0-9]{5}")){
      model.addAttribute("Erreur", "Vous devez rentrer un code postal valide !");
      return "signup";
    }
    if (!city.matches("[A-Za-zéèêëàâîïôöûüç]*")){
      model.addAttribute("Erreur", "Vous devez rentrer un nom de ville valide !");
      return "signup";
    }
    if (!phoneNumber.matches("(0|\\+33)[1-9]( *[0-9]{2}){4}")) {
      model.addAttribute("Erreur", "Vous devez rentrer un numéro de téléphone valide !");
      return "signup";
    }
    model.addAttribute("Erreur", "");
    if (customerService.testEmail(email)) {
      if (type.equals("producer")) {
        customerService.save(
            new Customer(
                firstName,
                lastName,
                email,
                password,
                address,
                postalCode,
                city,
                phoneNumber,
                "producer"));
      } else {
        customerService.save(
            new Customer(
                firstName,
                lastName,
                email,
                password,
                address,
                postalCode,
                city,
                phoneNumber,
                "consumer"));
      }
      model.addAttribute("firstName", firstName);
      model.addAttribute("lastName", lastName);
      return "signupconfirm";
    }else {
      model.addAttribute("Erreur", "L'adresse email est déjà utilisée !");
      return "signup";
    }
  }

  @GetMapping("/login")
  public String logIn() {
    return "login";
  }

  @PostMapping("/loginconfirm")
  public String logIn(
      @RequestParam(name = "email") String email,
      @RequestParam(name = "password") String password,
      HttpServletRequest request,
      Model model) {

    model.addAttribute("Erreur", "");
    if (email.isEmpty() || password.isEmpty()){
      model.addAttribute("Erreur", "Vous devez remplir les champs avant de valider !");
      return "login";
    }
    Customer customer = customerService.connect(email, password);
    if(customer != null){
      request.getSession().setAttribute("customer", customer);
      model.addAttribute("firstName", customer.getFirstName());
      model.addAttribute("lastName", customer.getLastName());
      return "loginconfirm";
    }else{
      model.addAttribute("Erreur", "L'adresse email et le mot de passe ne correspondent pas !");
      return "login";
    }
  }

  @GetMapping("/logout")
  public String logIn(HttpServletRequest request) {
    request.getSession().invalidate();
    return "index";
  }
}
