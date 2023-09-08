package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AccountService {
    void createAccount(Account account);
//    public boolean updateAccount(int id, Account account);

    void updateAccount(String accountNumber, String newAccountHolder);

    public boolean deleteAccount(String account);
    public Account getAccount(String str);
    Map<String, Account> getAllAccounts();


}