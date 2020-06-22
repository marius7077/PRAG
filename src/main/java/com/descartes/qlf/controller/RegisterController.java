package com.descartes.qlf.controller;

import com.descartes.qlf.dto.ProducteurDTO;
import com.descartes.qlf.model.Adresse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegisterController {

//    @GetMapping("/registerProducteur")
//    public void registerProducteur(
//            @RequestParam(name = "nom", required = true) String nom,
//            @RequestParam(name = "prenom", required = true) String prenom,
//            @RequestParam(name = "email", required = true) String email,
//            @RequestParam(name = "motDePasse", required = true) String motDePasse,
//            @RequestParam(name = "rue", required = true) String rue,
//            @RequestParam(name = "num", required = true) int num,
//            @RequestParam(name = "codePostal", required = true) String codePostal,
//            @RequestParam(name = "ville", required = true) String ville,
//            @RequestParam(name = "pays", required = true) String pays,
//            @RequestParam(name = "telephone", required = true) int telephone,
//            @RequestParam(name = "rib", required = true) String rib,
//            Model model) {
//
//        model.addAttribute("nom", nom);
//        model.addAttribute("prenom", prenom);
//        model.addAttribute("email", email);
//        model.addAttribute("motDePasse", motDePasse);
//        model.addAttribute("rue", rue);
//        model.addAttribute("num", num);
//        model.addAttribute("codePostal", codePostal);
//        model.addAttribute("ville", ville);
//        model.addAttribute("telephone", telephone);
//        model.addAttribute("rib", rib);
//    }

    @GetMapping("/registerProducteur")
    public String showRegistrationForm(WebRequest request, Model model) {
        ProducteurDTO producteurDto = new ProducteurDTO();
        model.addAttribute("producteur", producteurDto);
        return "registration";
    }

    @PostMapping("/registerProducteur")
    public ModelAndView registerUserAccount(
            @ModelAttribute("producteur") @Valid ProducteurDTO producteurDto,
            HttpServletRequest request, Errors errors) {

        return null;
    }

}
