package com.descartes.qlf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Configuration
public class QlfController {

  @Autowired
  private DataSource dataSource;

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


