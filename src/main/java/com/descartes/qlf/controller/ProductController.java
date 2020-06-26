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
      model.addAttribute("error", "Vous êtes un consommateur, pas un producteur");
      return "error";
    }
  }

  @PostMapping("/addproductconfirm")
  public String addProduct(
      @RequestParam(name = "name", required = true) String name,
      @RequestParam(name = "description", required = true) String description,
      @RequestParam(name = "price", required = true) String price,
      @RequestParam(name = "productCategory", required = true) String productCategory,
      @RequestParam(name = "picture", required = true) MultipartFile file,
      HttpServletRequest request,
      Model model) {
    Customer customer = (Customer) request.getSession().getAttribute("customer");
    if (customer.getType().equals("producer")) {
      ProductCategory productCategoryRef = productCategoryService.getByName(productCategory);
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
      return "addproduct";
    } else {
      model.addAttribute("error", "Vous êtes un consommateur, pas un producteur");
      return "error";
    }
  }

  @GetMapping("/removeproduct")
  public String removeProduct(Model model, HttpServletRequest request) {
    Customer customer = (Customer) request.getSession().getAttribute("customer");
    if (customer.getType().equals("producer")) {
      return "removeproduct";
    } else {
      model.addAttribute("error", "Vous êtes un consommateur, pas un producteur");
      return "error";
    }
  }

  @PostMapping("/removeproductconfirm")
  public String removeProduct(
      @RequestParam(name = "id", required = true) Long id,
      Model model,
      HttpServletRequest request) {
    Customer customer = (Customer) request.getSession().getAttribute("customer");
    if (customer.getType().equals("producer")) {
      model.addAttribute("id", id);
      productService.removeById(id);
      return "removeproduct";
    } else {
      model.addAttribute("error", "Vous êtes un consommateur, pas un producteur");
      return "error";
    }
  }

  @GetMapping("/search")
  public String SearchProductByKeyword() {
    return "search";
  }

  @PostMapping("/searchresult")
  public String SearchProductByKeyword(
      @RequestParam(name = "keyword") String keyword, Model model) {
    List<Product> listProducts = productService.getBySearch(keyword);
    model.addAttribute("listProducts", listProducts.toArray());
    return "viewproducts";
  }

  @GetMapping("/viewproducts")
  public String viewProducts(Model model) {
    List<Product> listProducts = new ArrayList<>();
    listProducts = productService.getAllProduct();
    model.addAttribute("listProducts", listProducts.toArray());
    return "viewproducts";
  }

  @PostMapping("/productcategory")
  public String productcategory(
      @RequestParam(name = "fruit_legume", required = false) String fruit_legume,
      @RequestParam(name = "viande", required = false) String viande,
      @RequestParam(name = "laitiers", required = false) String laitier,
      Model model) {
    List<Product> listProducts = new ArrayList<>();
    if (fruit_legume != null) {
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
  public String product(
      @RequestParam(name = "productId", required = true) Long productId, Model model) {
    Product product = productService.getById(productId);
    model.addAttribute(product);
    return "product";
  }
}
