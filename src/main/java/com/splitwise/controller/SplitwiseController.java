package com.splitwise.controller;


import com.splitwise.entity.Transaction;
import com.splitwise.entity.UserData;
import com.splitwise.model.TransactionModel;
import com.splitwise.model.Users;
import com.splitwise.service.SplitwiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/splitwise")
public class SplitwiseController {

    @Autowired
    SplitwiseService splitwiseService;

    @GetMapping(value="/getUser")
    public ResponseEntity getUserDetails(@RequestParam Integer user_id){
        List<Users> allInfo = splitwiseService.getUserData(user_id);
        return new ResponseEntity<>(allInfo, HttpStatus.ACCEPTED);
    }

    @PostMapping(value="/transact")
    public ResponseEntity transact(@RequestBody TransactionModel transactions){
        splitwiseService.transact(transactions);
        return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
    }

    @GetMapping(value="/transactions")
    public ResponseEntity getTransactions(@RequestParam Integer user_id){
        List<Transaction> allTransactions =  splitwiseService.getAllTransactions(user_id);
        return new ResponseEntity<>(allTransactions, HttpStatus.ACCEPTED);
    }

    @GetMapping(value="/transaction_info")
    public ResponseEntity getTransactionInfo(@RequestParam Integer transactionId){
        String transactionInfo = splitwiseService.getTransactionInfo(transactionId);
        return new ResponseEntity<>(transactionInfo, HttpStatus.ACCEPTED);
    }
}
