package com.indium.bankingapp.service;

import com.indium.bankingapp.model.AccountDetails;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private List<AccountDetails> accounts;

    public AccountService() {
        accounts = new ArrayList<>();
    }


    public void addAccount(AccountDetails account) {
        accounts.add(account);
    }


    public List<AccountDetails> viewAllAccounts() {
        return accounts;
    }


    public AccountDetails findAccountByNumber(String accountNumber) {
        for (AccountDetails account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }



    // Delete an account by account number
    public boolean deleteAccount(String accountNumber) {
        AccountDetails accountToRemove = null;
        for (AccountDetails account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                accountToRemove = account;
                break;
            }
        }
        if (accountToRemove != null) {
            accounts.remove(accountToRemove);
            return true;
        }
        return false;
    }
}
