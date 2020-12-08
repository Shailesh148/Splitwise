package com.splitwise.entity;


import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@Table(name="UserData")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name="user1")
    private int userFrom;

    @NotNull
    @Column(name="user2")
    private int userTo;

    public int getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(int userFrom) {
        this.userFrom = userFrom;
    }

    public int getUserTo() {
        return userTo;
    }

    public void setUserTo(int userTo) {
        this.userTo = userTo;
    }

    @NotNull
    @Column(name="transactionTotalAmount")
    private double transactionTotalAmount;

    public double getTransactionTotalAmount() {
        return transactionTotalAmount;
    }

    public void setTransactionTotalAmount(double transactionTotalAmount) {
        this.transactionTotalAmount = transactionTotalAmount;
    }

    public UserData(int userFrom, int userTo, double transactionTotalAmount) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.transactionTotalAmount = transactionTotalAmount;
    }

}
