package com.descartes.qlf.repository;

import com.descartes.qlf.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

  Customer findByEmail(String email);

  @Query("SELECT c FROM Customer c WHERE c.type LIKE %?1%")
  List<Customer> findByType(String type);

  Optional<Customer> findById(Long id);

  @Query("SELECT c FROM Customer c WHERE CONCAT(UPPER(c.firstName), UPPER(c.lastName)) LIKE %?1%")
  List<Customer> search(String keyword);

  @Override
  List<Customer> findAll();
}
