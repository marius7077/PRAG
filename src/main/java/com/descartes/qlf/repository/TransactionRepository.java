package com.descartes.qlf.repository;

import com.descartes.qlf.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}