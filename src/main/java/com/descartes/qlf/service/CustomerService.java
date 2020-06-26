package com.descartes.qlf.service;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.repository.CustomerRepository;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

  @Autowired private CustomerRepository customerRepository;

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired private JavaMailSender emailSender;

  public void save(Customer customer) {
    customerRepository.save(customer);
  }

  public Customer getById(Long id) {
    Optional<Customer> customer = customerRepository.findById(id);
    return customer.orElse(null);
  }

  public Customer getByEmail(String email) {
    Optional<Customer> customer = Optional.ofNullable(customerRepository.findByEmail(email));
    return customer.orElse(null);
  }

  public List<Customer> getAllCustomers() {
    return customerRepository.findAll();
  }

  public Customer connect(String email, String password) {
    Customer customer = customerRepository.findByEmail(email);
    if (customer != null && bCryptPasswordEncoder.matches(password, customer.getPassword())) {
      return customer;
    } else {
      return null;
    }
  }

  public List<Customer> getBySearch(String keyword) {
    if (keyword != null) {
      return customerRepository.search(keyword);
    } else {
      return customerRepository.findAll();
    }
  }

  public boolean testEmail(String email) {
    Customer customer = customerRepository.findByEmail(email);
    return customer == null;
  }

  public List<Double> addressToCoordinates(String address, String postalCode, String city) {
    address = address.replace(" ", "+").replace("'", "");
    city = city.replace(" ", "+").replace("'", "");
    List<Double> coordinates = new ArrayList<>();
    RestTemplate restTemplate = new RestTemplate();
    String url = "https://geocode.localfocus.nl/geocode.php?q=";
    String urlCompleted = url + address + "+" + postalCode + "+" + city + "&boundary=FRA";
    String body = restTemplate.getForObject(urlCompleted, String.class);
    if (body != null) {
      coordinates.add(
          Double.parseDouble(body.substring(body.indexOf("lat") + 5, body.indexOf("lng") - 2)));
      coordinates.add(
          Double.parseDouble(body.substring(body.indexOf("lng") + 5, body.indexOf("zoom") - 2)));
    } else {
      return null;
    }
    return coordinates;
  }

  private int distance(double lat1, double lon1, double lat2, double lon2) {
    if ((lat1 == lat2) && (lon1 == lon2)) {
      return 0;
    } else {
      double theta = lon1 - lon2;
      double distance =
          Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
              + Math.cos(Math.toRadians(lat1))
                  * Math.cos(Math.toRadians(lat2))
                  * Math.cos(Math.toRadians(theta));
      distance = Math.acos(distance);
      distance = Math.toDegrees(distance);
      distance = distance * 60 * 1.1515 * 1.609344;
      return (int) Math.round(distance);
    }
  }

  public String resetPassword(String email) {
    String newPassword = RandomStringUtils.random(10, true, true);
    Customer customer = customerRepository.findByEmail(email);
    customer.setPassword(bCryptPasswordEncoder.encode(newPassword));
    customerRepository.save(customer);
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("quelaferme@gmail.com");
    message.setTo(email);
    message.setSubject("Votre nouveau mot de passe");
    message.setText(
        "Votre nouveau mot de passe : "
            + newPassword
            + "\n"
            + "https://prag-qlf.herokuapp.com/login");
    emailSender.send(message);
    return null;
  }
}
