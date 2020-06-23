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
}
