package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountServiceArrListImpl implements AccountService {

    private List<Account> accounts = new ArrayList<>();

    @Override
    public boolean createAccount(Account account) {
        accounts.add(account);
        return false;
    }


    @Override
    public List<Account> getAllAccounts() {
        return accounts;
    }

    @Override
    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null; // Account not found
    }

    @Override
    public void updateAccount(String accountNumber, String newAccountHolder) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                account.setAccountName(newAccountHolder);
                return; // Account updated, exit loop
            }
        }
    }

    @Override
    public boolean deleteAccount(String accountNumber) {
        accounts.removeIf(account -> account.getAccountNumber().equals(accountNumber));
        return false;
    }
}