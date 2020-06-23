package com.descartes.qlf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QlfController {

  @GetMapping("/greeting")
  public String greeting(
      @RequestParam(name = "name", required = false, defaultValue = "World") String name,
      Model model) {
    model.addAttribute("name", name);
    System.out.println("controllergreeting");
    return "greeting";
  }
  @RequestMapping(value = "login", method = RequestMethod.POST)
  public String login(
          @RequestParam(name = "txtLogin", required = true, defaultValue = "World") String login,
          @RequestParam(name = "txtPassword", required = true, defaultValue = "World") String password,

          Model model) {
    model.addAttribute("txtLogin", login);
    model.addAttribute("txtPassword", password);
    System.out.println("controllerlogin," + login);
    return "greeting";
  }
  }


