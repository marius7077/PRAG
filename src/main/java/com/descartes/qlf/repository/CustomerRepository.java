package com.descartes.qlf.repository;

import com.descartes.qlf.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
  Customer findByEmail(String email);
}
