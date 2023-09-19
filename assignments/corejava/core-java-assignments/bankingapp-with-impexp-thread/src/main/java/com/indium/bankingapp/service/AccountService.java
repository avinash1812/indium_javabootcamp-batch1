package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AccountService {
    boolean createAccount(Account account);
//    public boolean updateAccount(int id, com.indium.bankingapp.model.Account account);

    void updateAccount(String accountNumber, String newAccountHolder);

    public boolean deleteAccount(Integer account);
    public Account getAccount(Integer str);
//    Map<String, com.indium.bankingapp.model.Account> getAllAccounts();
    List<Account> getAllAccounts();
    int countAccountsAbove1Lakh();

    Map<String, Integer> countAccountsByType();
    List<Map.Entry<String, Integer>> countAndSortAccountsByType();
    Map<String, Double> calculateAverageBalanceByType();
    List<Integer> listAccountIdsByAccountNameContains(String name);
    void importProducts(String filePath, String delimiter);
    void exportProducts(String filePath, String delimiter);
    Collection<Account> getProducts();
    public void bulkExport();
    public void bulkImport();
    List<Account> getAll();
}