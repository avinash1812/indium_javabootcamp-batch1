package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.util.*;

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
    @Override
    public int countAccountsAbove1Lakh() {
        int count = 0;
        for (Account account : accounts) {
            if (account.getBalance() > 100000) {
                count++;
            }
        }
        return count;
    }
    public Map<String, Integer> countAccountsByType() {
        Map<String, Integer> accountTypeCounts = new HashMap<>();

        for (Account account : accounts) {
            String accountType = account.getAccountType();

            // If the account type is not already in the map, add it with a count of 1.
            // If it's already in the map, increment the count.
            accountTypeCounts.put(accountType, accountTypeCounts.getOrDefault(accountType, 0) + 1);
        }

        return accountTypeCounts;
    }
    @Override
    public List<Map.Entry<String, Integer>> countAndSortAccountsByType() {
        Map<String, Integer> accountTypeCounts = new HashMap<>();

        for (Account account : accounts) {
            String accountType = account.getAccountType();
            Integer count = accountTypeCounts.get(accountType);

            if (count == null) {
                accountTypeCounts.put(accountType, 1);
            } else {
                accountTypeCounts.put(accountType, count + 1);
            }
        }

        List<Map.Entry<String, Integer>> sortedAccountTypeCounts = new ArrayList<>(accountTypeCounts.entrySet());

        Collections.sort(sortedAccountTypeCounts, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        });

        return sortedAccountTypeCounts;
    }
    @Override
    public Map<String, Double> calculateAverageBalanceByType() {
        Map<String, List<Double>> accountTypeBalances = new HashMap<>();

        for (Account account : accounts) {
            String accountType = account.getAccountType();
            double balance = account.getBalance();

            accountTypeBalances
                    .computeIfAbsent(accountType, k -> new ArrayList<>())
                    .add(balance);
        }

        Map<String, Double> averageBalances = new HashMap<>();

        for (Map.Entry<String, List<Double>> entry : accountTypeBalances.entrySet()) {
            String accountType = entry.getKey();
            List<Double> balances = entry.getValue();

            double sum = balances.stream().mapToDouble(Double::doubleValue).sum();
            double average = sum / balances.size();

            averageBalances.put(accountType, average);
        }

        return averageBalances;
    }
    @Override
    public List<String> listAccountIdsByAccountNameContains(String name) {
        List<String> matchingAccountIds = new ArrayList<>();

        for (Account account : accounts) {
            String accountName = account.getAccountName();

            if (accountName != null && accountName.contains(name)) {
                matchingAccountIds.add(account.getAccountNumber());
            }
        }

        return matchingAccountIds;
    }
}