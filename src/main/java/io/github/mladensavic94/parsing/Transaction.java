package io.github.mladensavic94.parsing;

import java.time.LocalDate;

public class Transaction {

    private LocalDate dateOfTransaction;
    private int cardNumber;
    private String desc;
    private double refValue;
    private double originalValue;
    private double amountCredited;
    private double amountDebited;
    private double balance;

    public LocalDate getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(LocalDate dateOfTransaction) {
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

    @Override
    public String toString() {
        return dateOfTransaction +", " +
                cardNumber +", " +
                refValue +", " +
                amountCredited +", " +
                amountDebited +", " +
                balance +", " +
                desc;
    }

    public static String csvHeaders(){
        return "dateOfTransaction, cardNumber, refValue, amountCredited, amountDebited, balance, desc";
    }
}
