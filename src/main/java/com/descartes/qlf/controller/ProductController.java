package com.descartes.qlf.controller;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.model.Product;
import com.descartes.qlf.model.ProductCategory;
import com.descartes.qlf.service.CustomerService;
import com.descartes.qlf.service.FileSystemStorageService;
import com.descartes.qlf.service.ProductCategoryService;
import com.descartes.qlf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

  @Autowired private ProductService productService;

  @Autowired private ProductCategoryService productCategoryService;

  @Autowired private CustomerService customerService;

  private final FileSystemStorageService fileSystemStorageService;

  @Autowired
  public ProductController(FileSystemStorageService fileSystemStorageService) {
    this.fileSystemStorageService = fileSystemStorageService;
  }

  @GetMapping("/addproduct")
  public String addProduct(Model model, HttpServletRequest request) {
    Customer customer = (Customer) request.getSession().getAttribute("customer");
    if (customer.getType().equals("producer")) {
      return "addproduct";
    } else {
      model.addAttribute("error", "Vous n'avez pas l'autorisation !");
      return "error";
    }
  }

  @PostMapping("/addproductconfirm")
  public String addProduct(
      @RequestParam(name = "name", required = true) String name,
      @RequestParam(name = "description", required = true) String description,
      @RequestParam(name = "price", required = true) String price,
      @RequestParam(name = "productCategory", required = true) Long productCategory,
      @RequestParam(name = "picture", required = true) MultipartFile file,
      HttpServletRequest request,
      Model model) {
    Customer customer = (Customer) request.getSession().getAttribute("customer");
    if (customer.getType().equals("producer")) {
      if (name.isEmpty()
          || price.isEmpty()
          || productCategory.toString().isEmpty()
          || description.isEmpty()) {
        model.addAttribute("Erreur", "Vous devez remplir les champs avant de valider !");
      } else if (file.getOriginalFilename().equals("")) {
        model.addAttribute("Erreur", "Vous devez choisir un fichier avant de valider !");
      } else if (!price.matches("[0-9 ]{1,}[,.]{0,1}[0-9]{0,2}")) {
        model.addAttribute("Erreur", "Vous devez rentrer un prix en euro !");
      } else {
        model.addAttribute("Erreur", "");
        ProductCategory productCategoryRef = productCategoryService.getById(productCategory);
        String filename = fileSystemStorageService.store(file);
        Product product =
            new Product(
                name,
                description,
                price,
                "/files/" + filename,
                productCategoryRef,
                customerService.getById(customer.getId()));
        productService.save(product);
        model.addAttribute("file", filename);
      }
      return "addproduct";
    } else {
      model.addAttribute("error", "Vous n'avez pas l'autorisation !");
      return "error";
    }
  }

  @GetMapping("/removeproduct")
  public String removeProduct(Model model, HttpServletRequest request) {
    Customer customer = (Customer) request.getSession().getAttribute("customer");
    if (customer.getType().equals("producer")) {
      return "removeproduct";
    } else {
      model.addAttribute("error", "Vous n'avez pas l'autorisation !");
      return "error";
    }
  }

  @PostMapping("/removeproductconfirm")
  public String removeProduct(
      @RequestParam(name = "id") Long id, Model model, HttpServletRequest request) {
    Customer customer = (Customer) request.getSession().getAttribute("customer");
    if (customer.getType().equals("producer")) {
      model.addAttribute("id", id);
      productService.removeById(id);
      return "removeproduct";
    } else {
      model.addAttribute("error", "Vous n'avez pas l'autorisation !");
      return "error";
    }
  }

  @GetMapping("/search")
  public String SearchProductByKeyword() {
    return "search";
  }

  @PostMapping("/searchproducts")
  public String SearchProductByKeyword(
      @RequestParam(name = "keyword") String keyword, Model model) {
    List<Product> listProducts = productService.getBySearch(keyword.toUpperCase());
    model.addAttribute("listProducts", listProducts.toArray());
    return "viewproducts";
  }

  @GetMapping("/viewproducts")
  public String viewProducts(Model model) {
    List<Product> listProducts = productService.getAllProduct();
    model.addAttribute("listProducts", listProducts.toArray());
    return "viewproducts";
  }

  @PostMapping("/productcategory")
  public String productCategory(
      @RequestParam(name = "fruitLegume", required = false) String fruitLegume,
      @RequestParam(name = "viande", required = false) String viande,
      @RequestParam(name = "laitier", required = false) String laitier,
      Model model) {
    List<Product> listProducts = new ArrayList<>();
    if (fruitLegume != null) {
      listProducts.addAll(productService.getAllProductByCategoryId(1L));
    }
    if (viande != null) {
      listProducts.addAll(productService.getAllProductByCategoryId(2L));
    }
    if (laitier != null) {
      listProducts.addAll(productService.getAllProductByCategoryId(3L));
    }
    model.addAttribute("listProducts", listProducts.toArray());
    return "viewproducts";
  }

  @GetMapping("/product")
  public String product(@RequestParam(name = "productId") Long productId, Model model) {
    Product product = productService.getById(productId);
    model.addAttribute(product);
    return "product";
  }
}
