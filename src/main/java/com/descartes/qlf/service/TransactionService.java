package com.descartes.qlf.service;

import com.descartes.qlf.model.Transaction;
import com.descartes.qlf.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  @Autowired private TransactionRepository transactionRepository;

  public void removeAllTransactionByCustomer(Long id) {
    transactionRepository.deleteAllByCustomer_Id(id);
  }

  public void save(Transaction transaction) {
    transactionRepository.save(transaction);
  }
}
