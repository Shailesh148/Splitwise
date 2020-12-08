package com.splitwise.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@Table(name="Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name="user1")
    private int userFrom;

    @NotNull
    @Column(name="user2")
    private int userTo;

    @Column(name="transactionId")
    private int transactionId;

    @Column(name="transactionAmount")
    private double transactionAmount;

//    @Column(name="transactionInfo")
//    private String transactionInfo;

    public Transaction(int userFrom, int userTo, int transactionId, double transactionAmount){
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.transactionId = transactionId;
        this.transactionAmount = transactionAmount;
//        this.transactionInfo = transactionInfo;
    }

}
