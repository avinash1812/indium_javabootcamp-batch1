package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AccountService {
    boolean createAccount(Account account);
//    public boolean updateAccount(int id, Account account);

    void updateAccount(String accountNumber, String newAccountHolder);

    public boolean deleteAccount(String account);
    public Account getAccount(String str);
//    Map<String, Account> getAllAccounts();
    List<Account> getAllAccounts();
    int countAccountsAbove1Lakh();

    Map<String, Integer> countAccountsByType();
    List<Map.Entry<String, Integer>> countAndSortAccountsByType();
    Map<String, Double> calculateAverageBalanceByType();
    List<String> listAccountIdsByAccountNameContains(String name);

}