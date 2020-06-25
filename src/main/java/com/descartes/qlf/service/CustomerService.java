package com.descartes.qlf.service;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

  @Autowired private CustomerRepository customerRepository;

  public void save(Customer customer) {
    customerRepository.save(customer);
  }

  public Customer connect(String email, String password) {
    Customer customer = customerRepository.findByEmail(email);
    if (customer.getPassword().equals(password)) {
      return customer;
    } else {
      return null;
    }
  }

  public List<Customer> getAllCustomers() {
    return (List<Customer>) customerRepository.findAll();
  }

  public static int distance(String city1, String postalCode1, String city2, String postalCode2) {
    List<String> cityList = Arrays.asList(city1.replace(" ","-"), city2.replace(" ","-"));
    List<String> postalCodeList = Arrays.asList(postalCode1, postalCode2);
    List<Double> longitudeList = new ArrayList<>();
    List<Double> latitudeList = new ArrayList<>();
    String url = "https://geocode.localfocus.nl/geocode.php?q=";
    for (int i = 0; i < 2; i++) {
      RestTemplate restTemplate = new RestTemplate();
      String urlCompleted = url + cityList.get(i) + "+" + postalCodeList.get(i) + "&boundary=FRA";
      String body = restTemplate.getForObject(urlCompleted, String.class);
      latitudeList.add(
          Double.parseDouble(body.substring(body.indexOf("lat") + 5, body.indexOf("lng") - 2)));
      longitudeList.add(
          Double.parseDouble(body.substring(body.indexOf("lng") + 5, body.indexOf("zoom") - 2)));
    }
    double distance =
        Math.toDegrees(
                Math.acos(
                    (Math.sin(Math.toRadians(latitudeList.get(0)))
                            * Math.sin(Math.toRadians(latitudeList.get(1)))
                        + Math.cos(Math.toRadians(latitudeList.get(0)))
                            * Math.cos(Math.toRadians(latitudeList.get(1)))
                            * Math.cos(
                                Math.toRadians(longitudeList.get(0) - longitudeList.get(1))))))
            * 60
            * 1.1515
            * 1.609344;
    return (int) Math.round(distance);
  }

  public Customer getById(Long id) {
    Optional<Customer> customer = customerRepository.findById(id);
    return customer.orElse(null);
  }
}
