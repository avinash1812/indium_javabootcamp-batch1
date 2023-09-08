package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AccountServiceTreeMapImpl implements AccountService {
    private Map<String, Account> accounts = new TreeMap<>();

    @Override
    public void createAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }



    @Override
    public Map<String, Account> getAllAccounts() {
        return accounts;
    }


    @Override
    public Account getAccount(String acc) {
        return accounts.get(acc);
    }

    @Override
    public void updateAccount(String accountNumber, String newAccountHolder) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.setAccountName(newAccountHolder);
            System.out.println("Account updated successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }

    @Override
    public boolean deleteAccount(String accountNumber) {
        accounts.remove(accountNumber);
        return false;
    }
}
