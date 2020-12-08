package com.splitwise.repository;

import com.splitwise.entity.TransactionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionGroupRespository extends JpaRepository<TransactionGroup,Integer> {
}
