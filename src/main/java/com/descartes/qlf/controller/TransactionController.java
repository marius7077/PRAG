package com.descartes.qlf.controller;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.model.Transaction;
import com.descartes.qlf.service.CustomerService;
import com.descartes.qlf.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

@Controller
public class TransactionController {

  @Autowired private TransactionService transactionService;

  @Autowired private CustomerService customerService;

  @PostMapping("/billingconfirm")
  public String billing(
      @RequestParam(name = "creditCard") String creditCard,
      @RequestParam(name = "expirationDate") String expirationDate,
      @RequestParam(name = "cryptogram") String cryptogram,
      @RequestParam(name = "duration") String duration,
      HttpServletRequest request,
      Model model) {
    Customer customer = (Customer) request.getSession().getAttribute("customer");

    if (customer != null) {
      Date date = new Date();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(Calendar.DAY_OF_MONTH, 1);
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      calendar.add(Calendar.MONTH, Integer.parseInt(duration));

      double price;
      switch (duration) {
        case "1":
          price = 14.99;
          break;
        case "3":
          price = 39.99;
          break;
        case "12":
          price = 149.99;
          break;
        default:
          model.addAttribute("error", "Paiment invalide. Veuillez-contacter les administrateurs.");
          return "billing";
      }

      transactionService.save(
          new Transaction(
              customer, date.getTime(), "producer_subscription_" + duration + "_month", price));
      customer.setEndSubscription(calendar.getTimeInMillis());
      customerService.save(customer);
      // faire la vérif de validité
      // régler probleme affichage menu

      return "index";
    } else {
      model.addAttribute("error", "Vous n'êtes pas connectés");
      return "login";
    }
  }
}
