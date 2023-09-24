package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
    public Account getAccount(int accountNumber) {

        return accounts.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null); // Account not found
    }

    @Override
    public void updateAccount(String accountNumber, String newAccountHolder) {

        accounts.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .ifPresent(account -> account.setAccountName(newAccountHolder));
    }

    @Override
    public boolean deleteAccount(int accountNumber) {

        accounts.removeIf(account -> account.getAccountNumber() == accountNumber);
        return false;
    }

    @Override
    public int countAccountsAbove1Lakh() {

        return (int) accounts.stream()
                .filter(account -> account.getBalance() > 100000)
                .count();
    }

    @Override
    public Map<String, Long> countAccountsByType() {
        // Using Java Stream to count accounts by account type
        return accounts.stream()
                .collect(Collectors.groupingBy(Account::getAccountType, Collectors.counting()));
    }

    @Override
    public List<Map.Entry<String, Long>> countAndSortAccountsByType() {

        return accounts.stream()
                .collect(Collectors.groupingBy(Account::getAccountType, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Double> calculateAverageBalanceByType() {

        return accounts.stream()
                .collect(Collectors.groupingBy(Account::getAccountType,
                        Collectors.averagingDouble(Account::getBalance)));
    }

    @Override
    public List<Integer> listAccountIdsByAccountNameContains(String name) {

        return accounts.stream()
                .filter(account -> account.getAccountName().contains(name))
                .map(Account::getAccountNumber)
                .collect(Collectors.toList());
    }

    @Override
    public void importProducts(String filePath, String delimiter) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(delimiter);
                if (fields.length == 5) {
                    int accNumber = Integer.parseInt(fields[0]);
                    String accName = fields[1];
                    String accType = fields[2];
                    double roi = Double.parseDouble(fields[3]);
                    double balance = Double.parseDouble(fields[4]);
                    Account product = new Account(accNumber, accName, accType, roi, balance);
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
