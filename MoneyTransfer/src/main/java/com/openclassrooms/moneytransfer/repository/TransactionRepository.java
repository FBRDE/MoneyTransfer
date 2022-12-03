package com.openclassrooms.moneytransfer.repository;

import com.openclassrooms.moneytransfer.model.Transaction;
import com.openclassrooms.moneytransfer.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    public Page<Transaction> findBySender(User sender, Pageable pageable);

}