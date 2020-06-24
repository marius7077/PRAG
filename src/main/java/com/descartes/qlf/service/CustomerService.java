package com.descartes.qlf.service;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public Customer getById(Long id) {
    Optional<Customer> customer = customerRepository.findById(id);
    if (customer.isPresent()) {
      return customer.get();
    } else {
      return null;
    }
  }


}
