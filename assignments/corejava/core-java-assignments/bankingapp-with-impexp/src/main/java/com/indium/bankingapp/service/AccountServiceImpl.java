package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.io.*;
import java.util.*;

public class AccountServiceImpl implements AccountService {

    private List<Account> accounts = new ArrayList<>();
    private Map<Integer, Account> products = new HashMap<>();

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
    public List<Integer> listAccountIdsByAccountNameContains(String name) {
        List<Integer> matchingAccountIds = new ArrayList<>();

        for (Account account : accounts) {
            String accountName = account.getAccountName();

            if (accountName != null && accountName.contains(name)) {
                matchingAccountIds.add(account.getAccountNumber());
            }
        }

        return matchingAccountIds;
    }
    @Override
    public void importProducts(String filePath, String delimiter) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(delimiter);
                if (fields.length == 5) {
                    int accNumber = Integer.parseInt(fields[0]);
//                    String accountNumber = fields[0];
                    String accName =fields[1];
                    String accType = fields[2];
                    double roi = Double.parseDouble(fields[3]);
                    double balance = Double.parseDouble(fields[4]);
                    Account product = new Account(accNumber,accName,accType,roi,balance);
                    products.put(accNumber, product);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportProducts(String filePath, String delimiter) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Account product : products.values()) {
                String line = product.getAccountNumber() + delimiter + product.getAccountName() + delimiter + product.getBalance();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Account> getProducts() {
        return products.values();
    }
}