package com.descartes.qlf.controller;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.model.Product;
import com.descartes.qlf.model.ProductCategory;
import com.descartes.qlf.service.CustomerService;
import com.descartes.qlf.service.FileSystemStorageService;
import com.descartes.qlf.service.ProductCategoryService;
import com.descartes.qlf.service.ProductService;
import com.google.re2j.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
      @RequestParam(name = "name") String name,
      @RequestParam(name = "description") String description,
      @RequestParam(name = "price") String price,
      @RequestParam(name = "typePrice") String typePrice,
      @RequestParam(name = "productCategory") Long productCategory,
      @RequestParam(name = "picture") MultipartFile file,
      HttpServletRequest request,
      Model model) {
    Customer customer = (Customer) request.getSession().getAttribute("customer");
    if (customer.getType().equals("producer")) {
      if (name.isEmpty()
          || price.isEmpty()
          || productCategory.toString().isEmpty()
          || description.isEmpty()
          || typePrice.isEmpty()) {
        model.addAttribute("error", "Vous devez remplir les champs avant de valider !");
        return "addproduct";
      } else if (Objects.equals(file.getOriginalFilename(), "")) {
        model.addAttribute("error", "Vous devez choisir un fichier avant de valider !");
        return "addproduct";
      } else if (!Pattern.compile("[0-9 ]{1,}[,.]{0,1}[0-9]{0,2}").matcher(price).find()) {
        model.addAttribute("error", "Vous devez rentrer un prix en euro !");
        return "addproduct";
      } else {
        model.addAttribute("error", "");
        ProductCategory productCategoryRef = productCategoryService.getById(productCategory);
        String filename = fileSystemStorageService.store(file);
        Product product =
            new Product(
                name,
                description,
                price,
                "/files/" + filename,
                typePrice,
                productCategoryRef,
                customerService.getById(customer.getId()));
        productService.save(product);
        model.addAttribute("file", filename);
        List<Product> listProducts = productService.getAllProductByCustomerId(customer.getId());
        model.addAttribute("listProducts", listProducts.toArray());
      }
      return "profile";
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
      List<Product> listProducts = productService.getAllProductByCustomerId(customer.getId());
      model.addAttribute("listProducts", listProducts.toArray());
      return "profile";
    } else {
      model.addAttribute("error", "Vous n'avez pas l'autorisation !");
      return "error";
    }
  }

  @PostMapping("/searchproducts")
  public String searchProductByKeyword(
      @RequestParam(name = "keyword") String keyword, Model model) {
    List<Product> listProducts = productService.getBySearch(keyword.toUpperCase());
    return sortListProducts(model, listProducts);
  }

  @GetMapping("/viewproducts")
  public String viewProducts(Model model) {
    List<Product> listProducts = productService.getAllProduct();
    return sortListProducts(model, listProducts);
  }

  private String sortListProducts(Model model, List<Product> listProducts) {
    listProducts.sort(Comparator.comparing(Product::getId));
    List<Product> listProductsCarousel =
        listProducts.subList(Math.max(listProducts.size() - 3, 0), listProducts.size());
    model.addAttribute("listProductsCarousel", listProductsCarousel.toArray());
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
    return sortListProducts(model, listProducts);
  }

  @GetMapping("/product")
  public String product(@RequestParam(name = "productId") Long productId, Model model) {
    Product product = productService.getById(productId);
    model.addAttribute(product);
    return "product";
  }

  @GetMapping("/viewproduct")
  public String viewproduct(@RequestParam(name = "productId") Long productId, Model model) {
    Product product = productService.getById(productId);
    model.addAttribute("product", product);
    return "viewproduct";
  }

  @PostMapping("/editproductresult")
  public String editproduct(
      @RequestParam(name = "name") String name,
      @RequestParam(name = "description") String description,
      @RequestParam(name = "price") String price,
      @RequestParam(name = "typePrice") String typePrice,
      @RequestParam(name = "productCategory") Long productCategory,
      @RequestParam(name = "id") Long id,
      Model model,
      HttpServletRequest request) {
    model.addAttribute("error", null);
    Customer customer = (Customer) request.getSession().getAttribute("customer");
    List<Product> listProducts = productService.getAllProductByCustomerId(customer.getId());
    model.addAttribute("listProducts", listProducts.toArray());
    model.addAttribute("CustomerInformation", customerService.getById(customer.getId()));
    if (customer.getType().equals("producer")) {
      if (name.isEmpty()
          || price.isEmpty()
          || productCategory.toString().isEmpty()
          || description.isEmpty()
          || typePrice.isEmpty()) {
        model.addAttribute("error", "Vous devez remplir les champs avant de valider !");
      } else if (!Pattern.compile("[0-9 ]{1,}[,.]{0,1}[0-9]{0,2}").matcher(price).find()) {
        model.addAttribute("error", "Vous devez rentrer un prix en euro !");
      } else {
        Product product = productService.getById(id);
        ProductCategory productCategoryRef = productCategoryService.getById(productCategory);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setType(typePrice);
        product.setCategory(productCategoryRef);
        productService.save(product);
      }
      return "profile";
    }
    return "error";
  }
}
