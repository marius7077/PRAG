package com.descartes.qlf.controller;

import com.descartes.qlf.model.Adresse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @GetMapping("/registerProducteur")
    public void registerProducteur(
            @RequestParam(name = "nom", required = true) String nom,
            @RequestParam(name = "prenom", required = true) String prenom,
            @RequestParam(name = "email", required = true) String email,
            @RequestParam(name = "motDePasse", required = true) String motDePasse,
            @RequestParam(name = "rue", required = true) String rue,
            @RequestParam(name = "num", required = true) int num,
            @RequestParam(name = "codePostal", required = true) String codePostal,
            @RequestParam(name = "ville", required = true) String ville,
            @RequestParam(name = "pays", required = true) String pays,
            @RequestParam(name = "telephone", required = true) int telephone,
            @RequestParam(name = "rib", required = true) String rib,
            Model model) {
        model.addAttribute("nom", nom);
        model.addAttribute("prenom", prenom);
        model.addAttribute("email", email);
        model.addAttribute("motDePasse", motDePasse);
        model.addAttribute("rue", rue);
        model.addAttribute("num", num);
        model.addAttribute("codePostal", codePostal);
        model.addAttribute("ville", ville);
        model.addAttribute("telephone", telephone);
        model.addAttribute("rib", rib);
    }
}
