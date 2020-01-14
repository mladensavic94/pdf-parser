package io.github.mladensavic94.parsing;

import java.util.Date;

public class Transaction {

    private Date dateOfTransaction;
    private int cardNumber;
    private String desc;
    private double refValue;
    private double originalValue;
    private double amountCredited;
    private double amountDebited;
    private double balance;

    public Date getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(Date dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getRefValue() {
        return refValue;
    }

    public void setRefValue(double refValue) {
        this.refValue = refValue;
    }

    public double getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(double originalValue) {
        this.originalValue = originalValue;
    }

    public double getAmountCredited() {
        return amountCredited;
    }

    public void setAmountCredited(double amountCredited) {
        this.amountCredited = amountCredited;
    }

    public double getAmountDebited() {
        return amountDebited;
    }

    public void setAmountDebited(double amountDebited) {
        this.amountDebited = amountDebited;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
