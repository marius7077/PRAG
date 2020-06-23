package com.descartes.qlf.controller;

import com.descartes.qlf.model.Adresse;
import com.descartes.qlf.model.Producteur;
import com.descartes.qlf.service.AdresseService;
import com.descartes.qlf.service.ProducteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

@Controller
@Configuration
public class QlfController {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private ProducteurService producteurService;

  @Autowired
  private AdresseService adresseService;

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

  @RequestMapping(value = "producteurRegistration", method = RequestMethod.POST)
  public String register(
          @RequestParam(name = "txtNom", required = true, defaultValue = "World") String nom,
          @RequestParam(name = "txtPrenom", required = true, defaultValue = "World") String prenom,
          @RequestParam(name = "txtEmail", required = true, defaultValue = "World") String email,
          @RequestParam(name = "txtMotDePasse", required = true, defaultValue = "World") String motDePasse,
          @RequestParam(name = "txtRue", required = true, defaultValue = "World") String rue,
          @RequestParam(name = "txtNum", required = true, defaultValue = "World") String num,
          @RequestParam(name = "txtCodePostal", required = true, defaultValue = "World") String codePostal,
          @RequestParam(name = "txtVille", required = true, defaultValue = "World") String ville,
          @RequestParam(name = "txtPays", required = true, defaultValue = "World") String pays,
          @RequestParam(name = "txtRib", required = true, defaultValue = "World") String rib,
          @RequestParam(name = "txtTel", required = true, defaultValue = "World") String tel,
          Model model) {
    model.addAttribute("txtNom", nom);
    model.addAttribute("txtPrenom", prenom);
    model.addAttribute("txtEmail", email);
    model.addAttribute("txtMotDePasse", motDePasse);
    model.addAttribute("txtRue", rue);
    model.addAttribute("txtNum", num);
    model.addAttribute("txtCodePostal", codePostal);
    model.addAttribute("txtVille", ville);
    model.addAttribute("txtPays", pays);
    model.addAttribute("txtRib", rib);
    model.addAttribute("txtTel", tel);

    Adresse adresse = new Adresse(rue, num, codePostal, ville, pays);
    adresseService.save(adresse);
    producteurService.save(new Producteur(nom, prenom, email, motDePasse, adresse, tel, rib));
    return "producteurRegistration";

  }

  @GetMapping("/crud")
  public String crud(Map<String, Object> model) {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt1 = connection.createStatement();
      stmt1.executeUpdate("INSERT INTO adresse(code_postal, num, pays, rue, ville) VALUES ('75116', '31', 'FRANCE', 'RUE GREUZE', 'PARIS')");
      stmt1.executeUpdate("UPDATE adresse SET pays='ALLEMAGNE' WHERE pays='FRANCE'");
      stmt1.executeUpdate("DELETE FROM adresse WHERE id = 1");

      Statement stmt2 = connection.createStatement();
      stmt2.executeUpdate("INSERT INTO consommateur(email, mot_de_passe, nom, prenom, telephone, adresse_id) VALUES ('simonweb98@gmail.com', 'mdp', 'weber', 'simon', '0674653955', 2)");
      stmt2.executeUpdate("UPDATE consommateur SET email='simonweb98@free.fr' WHERE email='simonweb98@gmail.com'");
      stmt2.executeUpdate("INSERT INTO consommateur(email, mot_de_passe, nom, prenom, telephone, adresse_id) VALUES ('thomas@gmail.com', 'mdp', 'weber', 'thomas', '0674653955', 3)");
      stmt2.executeUpdate("DELETE FROM consommateur WHERE id = 2");
      return "crud";
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return "crud";
    }
  }
}


