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
  public String signUp() {
    return "signup";
  }

  @PostMapping("/signupconfirm")
  public String signUp(
      @RequestParam(name = "lastName") String lastName,
      @RequestParam(name = "firstName") String firstName,
      @RequestParam(name = "email") String email,
      @RequestParam(name = "password") String password,
      @RequestParam(name = "confirmPassword") String confirmPassword,
      @RequestParam(name = "address") String address,
      @RequestParam(name = "postalCode") String postalCode,
      @RequestParam(name = "city") String city,
      @RequestParam(name = "phoneNumber") String phoneNumber,
      @RequestParam(name = "type") String type,
      @RequestParam(name = "company") String company,
      HttpServletRequest request,
      Model model) {
    model.addAttribute("Erreur", "");
    if (email.isEmpty()
        || password.isEmpty()
        || confirmPassword.isEmpty()
        || lastName.isEmpty()
        || firstName.isEmpty()
        || address.isEmpty()
        || postalCode.isEmpty()
        || city.isEmpty()
        || phoneNumber.isEmpty()
        || type.isEmpty()) {
      model.addAttribute("Erreur", "Vous devez remplir les champs avant de valider !");
      return "signup";
    }
    if (!email.matches(
        "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
      model.addAttribute("Erreur", "Vous devez rentrer une adresse email valide !");
      return "signup";
    }
    if (!password.equals(confirmPassword)) {
      model.addAttribute("Erreur", "Les mots de passe ne correspondent pas !");
      return "signup";
    }
    if (!postalCode.matches("[0-9]{5}")) {
      model.addAttribute("Erreur", "Vous devez rentrer un code postal valide !");
      return "signup";
    }
    if (!city.matches("[A-Za-zéèêëàâîïôöûüç]*")) {
      model.addAttribute("Erreur", "Vous devez rentrer un nom de ville valide !");
      return "signup";
    }
    if (!phoneNumber.matches("(0|\\+33)[1-9]( *[0-9]{2}){4}")) {
      model.addAttribute("Erreur", "Vous devez rentrer un numéro de téléphone valide !");
      return "signup";
    }
    model.addAttribute("Erreur", "");
    if (customerService.testEmail(email)) {
      List<Double> coordinates = customerService.addressToCoordinates(address, postalCode, city);
      if (type.equals("producer")) {
        Customer customer =
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
                "producer",
                company);
        customerService.save(customer);
        model.addAttribute(
            "error",
            "Veuillez-compléter votre inscription par le paiement de votre premier abonnement");
        request.getSession().setAttribute("customer", customer);
        return "billing";
      } else {
        Customer customer =
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
                "consumer",
                null);
        customerService.save(customer);
        request.getSession().setAttribute("customer", customer);
        return "index";
      }
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

    model.addAttribute("Erreur", "");
    if (email.isEmpty() || password.isEmpty()) {
      model.addAttribute("Erreur", "Vous devez remplir les champs avant de valider !");
      return "login";
    }
    Customer customer = customerService.connect(email, password);
    request.getSession().setAttribute("customer", customer);
    if (customer != null) {
      if ("producer".equals(customer.getType())
          && customer.getEndSubscription() < System.currentTimeMillis()) {
        model.addAttribute(
            "error", "Votre abonnement est expiré. Veuillez-renouveler votre abonnement");
        return "billing";
      } else {
        return "redirect:/";
      }
    } else {
      model.addAttribute("error", "L'adresse email et le mot de passe ne correspondent pas !");
      return "login";
    }
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request) {
    request.getSession().invalidate();
    return "redirect:/";
  }

  @GetMapping("/passwordforgotten")
  public String passwordforgotten() {
    return "passwordforgotten";
  }

  @PostMapping("/passwordforgotten")
  public String passwordforgotten(
      @RequestParam(name = "email", required = false) String email, Model model) {
    Customer customer = customerService.getByEmail(email);
    if (customer != null) {
      customerService.resetPassword(email);
      model.addAttribute("email", email);
      return "passwordforgottenconfirm";
    } else {
      model.addAttribute("error", "L'adresse email n'a pas été trouvé, vous n'existez pas !");
      return "error";
    }
  }
}
