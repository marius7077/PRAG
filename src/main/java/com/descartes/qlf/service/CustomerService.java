package com.descartes.qlf.service;

import com.descartes.qlf.model.Customer;
import com.descartes.qlf.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  @Autowired private CustomerRepository customerRepository;

  public void save(Customer customer) {
    customerRepository.save(customer);
  }

  public Customer connect(String email, String password) {
    Customer customer = customerRepository.findByEmail(email);
    if (customer != null && customer.getPassword().equals(password)) {
      return customer;
    } else {
      return null;
    }
  }

  public boolean testEmail(String email) {
    Customer customer = customerRepository.findByEmail(email);
    if (customer == null) {
      return true;
    } else {
      return false;
    }
  }
}
