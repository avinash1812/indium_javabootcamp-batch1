package com.indium.bankingapp.model;

public class Account implements Comparable<Account>{
    public String getAccountNumber() {
        return accountNumber;
    }

    private String accountNumber;
    private String accountName;
    private String accountType;

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    private double roi;

    public Account(String accountNumber, String accountName, String accountType, double roi, double balance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.accountType = accountType;
        this.roi = roi;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful. New balance: " + balance);
        } else {
            System.out.println("Invalid amount for deposit.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful. New balance: " + balance);
        } else {
            System.out.println("Invalid amount for withdrawal or insufficient balance.");
        }
    }
//    public Integer getAccountNumber() {
//        return accountNumber;
//    }
    @Override
    public int compareTo(Account o) {//going to compare current and given obj
//            return this.id - o.id;//ascending
//        return o.id - this.id; //descending
//        return this.name.compareTo(o.name);//compare names
        return o.accountName.compareTo(this.accountName);
        //0-both objects are equal
        //+ve -current obj is greater than given obj
        //-ve means current obj is smaller than given obj

    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    private double balance;
}
