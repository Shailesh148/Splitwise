package com.splitwise.service;

import java.util.*;

import com.splitwise.entity.Transaction;
import com.splitwise.entity.TransactionGroup;
import com.splitwise.entity.UserData;
import com.splitwise.model.TransactionModel;
import com.splitwise.model.UserInfo;
import com.splitwise.model.Users;
import com.splitwise.repository.TransactionGroupRespository;
import com.splitwise.repository.TransactionsRepository;
import com.splitwise.repository.UserRepository;
import org.apache.catalina.User;
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

    public UserInfo getUserData(Integer userId){
        List<UserData> userData = userRepository.findByUserFromOrUserTo(userId, userId);
        return createUsers(userData,userId);
    }


    public UserInfo createUsers(List<UserData> userData, Integer userId){
        UserInfo userInfo = new UserInfo();
        List<Users> allInfo = new ArrayList<>();
        double netBalance = 0;
        for(UserData eachUserData: userData){
            Users user = new Users();
            if(eachUserData.getUserTo() == userId){
                user.setUserName(eachUserData.getUserFrom());
                user.setAmount(eachUserData.getTransactionTotalAmount()* -1);
            }
            else{
                user.setUserName(eachUserData.getUserTo());
                user.setAmount(eachUserData.getTransactionTotalAmount());
            }
            netBalance += user.getAmount();
            allInfo.add(user);
        }
        userInfo.setNetBalance(netBalance);
        userInfo.setUsers(allInfo);
        return userInfo;
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

            if(previousUserData==null) {
                userRepository.save(new UserData(transaction.getUserFrom(), eachUser.getUserName(), updatedAmount));
            }
            else{
                userRepository.updateData(previousUserData.getUserFrom(),previousUserData.getUserTo(), updatedAmount);
            }

            Transaction eachTransaction = new Transaction(transaction.getUserFrom(),eachUser.getUserName(),uniqueId,eachUser.getAmount());
            transactions.add(eachTransaction);
        });
        transactionsRepository.saveAll(transactions);
    }

    public double getNewAmount(UserData userData, Integer userFrom, double transactionAmount){
        double total;
        if(userData!=null) {
            if (userData.getUserFrom() != userFrom) {

                return userData.getTransactionTotalAmount() + (transactionAmount * -1);
            }
            return userData.getTransactionTotalAmount() + transactionAmount;
        }
        else {
            return transactionAmount;
        }
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
