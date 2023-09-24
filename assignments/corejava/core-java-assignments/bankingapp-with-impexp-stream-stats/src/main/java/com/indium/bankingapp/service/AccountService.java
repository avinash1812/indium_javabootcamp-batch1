package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AccountService {
    boolean createAccount(Account account);
//    public boolean updateAccount(int id, Account account);

    void updateAccount(String accountNumber, String newAccountHolder);

    public boolean deleteAccount(int account);
    public Account getAccount(int acc_num);
    //    Map<String, Account> getAllAccounts();
    List<Account> getAllAccounts();
    int countAccountsAbove1Lakh();

    Map<String, Long> countAccountsByType();
    List<Map.Entry<String, Long>> countAndSortAccountsByType();
    Map<String, Double> calculateAverageBalanceByType();
    List<Integer> listAccountIdsByAccountNameContains(String name);
    void importProducts(String filePath, String delimiter);
    void exportProducts(String filePath, String delimiter);
    Collection<Account> getProducts();

}