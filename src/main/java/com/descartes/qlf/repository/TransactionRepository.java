package com.descartes.qlf.repository;

import com.descartes.qlf.model.Transaction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

  @Transactional
  @Modifying
  @Query("DELETE FROM Transaction t WHERE t.customer.id = :id")
  void deleteAllByCustomer_Id(@Param("id") long id);
}
