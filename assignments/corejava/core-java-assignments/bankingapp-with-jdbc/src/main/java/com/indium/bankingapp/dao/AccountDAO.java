package com.indium.bankingapp.dao;

import com.indium.bankingapp.model.Account;

import java.util.List;


    public interface AccountDAO {
        boolean createAccount(Account account);
        List<Account> getAllAccounts();
        Account getAccount(int accountNumber);
        boolean updateAccount(Account account);
        boolean deleteAccount(int accountNumber);


    }


