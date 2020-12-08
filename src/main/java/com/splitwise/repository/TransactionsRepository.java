package com.splitwise.repository;

import com.splitwise.entity.Transaction;
import com.splitwise.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Integer>{
    List<Transaction> findByUserFromOrUserTo(Integer userId, Integer userId1);
}
