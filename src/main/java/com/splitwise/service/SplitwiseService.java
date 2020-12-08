package com.splitwise.service;

import java.util.*;

import com.splitwise.entity.Transaction;
import com.splitwise.entity.TransactionGroup;
import com.splitwise.entity.UserData;
import com.splitwise.model.TransactionModel;
import com.splitwise.model.Users;
import com.splitwise.repository.TransactionGroupRespository;
import com.splitwise.repository.TransactionsRepository;
import com.splitwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SplitwiseService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    TransactionGroupRespository transactionGroupRespository;

    public List<Users> getUserData(Integer userId){
        List<UserData> userData = userRepository.findByUserFromOrUserTo(userId, userId);
        return createUsers(userData,userId);
    }


    public List<Users> createUsers(List<UserData> userData, Integer userId){
        List<Users> allInfo = new ArrayList<>();
        userData.forEach(eachUserData->{
            Users user = new Users();
            if(eachUserData.getUserTo() == userId){
                user.setUserName(eachUserData.getUserFrom());
                user.setAmount(eachUserData.getTransactionTotalAmount()* -1);
            }
            else{
                user.setUserName(eachUserData.getUserTo());
                user.setAmount(eachUserData.getTransactionTotalAmount());
            }
            allInfo.add(user);
        });
        return allInfo;
    }

    @Transactional
    public void transact(TransactionModel transaction){
        List<Transaction> transactions = new ArrayList<>();
        List<UserData> userData = new ArrayList<>();
        int uniqueId = transactionGroupRespository.save(new TransactionGroup(transaction.toString())).getId();
        transaction.getUsers().forEach(eachUser->{
            double updatedAmount;
            UserData previousUserData = userRepository.findByTwoDifferentUsers(transaction.getUserFrom(),eachUser.getUserName());

            updatedAmount  = getNewAmount(previousUserData, transaction.getUserFrom(), eachUser.getAmount());
            userData.add(new UserData(transaction.getUserFrom(),eachUser.getUserName(), updatedAmount));

            Transaction eachTransaction = new Transaction(transaction.getUserFrom(),eachUser.getUserName(),uniqueId,eachUser.getAmount());
            transactions.add(eachTransaction);
        });

        userRepository.saveAll(userData);
        transactionsRepository.saveAll(transactions);
    }

    public double getNewAmount(UserData userData, Integer userFrom, double transactionAmount){
        double total;
        if(userData!=null) {
            if (userData.getUserFrom() != userFrom) {
                Integer temp;
                temp = userData.getUserFrom();
                userData.setUserFrom(userData.getUserTo());
                userData.setUserTo(temp);
//                return userData.getTransactionTotalAmount() + (transactionAmount * -1);
            }
//            else {

            total= userData.getTransactionTotalAmount() + transactionAmount;
//            }
        }
        else {
            total= transactionAmount;
        }
        return total;
    }


    public List<Transaction> getAllTransactions(Integer userId){
        List<Transaction> allTransactions = transactionsRepository.findByUserFromOrUserTo(userId, userId);
        return allTransactions;
    }

    public String getTransactionInfo(Integer transactionId){
        TransactionGroup transactionGroup = transactionGroupRespository.findById(transactionId).orElse(null);
        return transactionGroup.getTransactionInfo();
    }



}
