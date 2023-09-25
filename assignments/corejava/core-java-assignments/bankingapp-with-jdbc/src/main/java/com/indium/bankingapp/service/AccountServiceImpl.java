package com.indium.bankingapp.service;

import com.indium.bankingapp.dao.AccountDAO;
import com.indium.bankingapp.model.Account;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.indium.bankingapp.database.DatabaseConnection.getConnection;

public class AccountServiceImpl implements AccountService {

    private List<Account> accounts = new ArrayList<>();
    private Map<Integer, Account> products = new HashMap<>();

//    private List<Account> accounts;

    public AccountServiceImpl(AccountDAO accountDAO) {
        // Initialize 'accounts' list by fetching data from the database
        accounts = accountDAO.getAllAccounts();
    }

    @Override
    public boolean createAccount(Account account) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO accounts (account_number, account_name, account_type, roi, balance) VALUES (?, ?, ?, ?, ?)")) {

            preparedStatement.setInt(1, account.getAccountNumber());
            preparedStatement.setString(2, account.getAccountName());
            preparedStatement.setString(3, account.getAccountType());
            preparedStatement.setDouble(4, account.getRoi());
            preparedStatement.setDouble(5, account.getBalance());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts")) {

            while (resultSet.next()) {
                Account account = extractAccountFromResultSet(resultSet);
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }
    private Account extractAccountFromResultSet(ResultSet resultSet) throws SQLException {
        int accountNumber = resultSet.getInt("account_number");
        String accountName = resultSet.getString("account_name");
        String accountType = resultSet.getString("account_type");
        double roi = resultSet.getDouble("roi");
        double balance = resultSet.getDouble("balance");

        return new Account(accountNumber, accountName, accountType, roi, balance);
    }
    @Override
    public Account getAccount(int accountNumber) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE account_number = ?")) {

            preparedStatement.setInt(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return extractAccountFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean updateAccount(Account account) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE accounts SET account_name = ?, account_type = ?, roi = ?, balance = ? WHERE account_number = ?")) {

                preparedStatement.setString(1, account.getAccountName());
                preparedStatement.setString(2, account.getAccountType());
                preparedStatement.setDouble(3, account.getRoi());
                preparedStatement.setDouble(4, account.getBalance());
                preparedStatement.setInt(5, account.getAccountNumber());

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public boolean deleteAccount(int accountNumber) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM accounts WHERE account_number = ?")) {

            preparedStatement.setInt(1, accountNumber);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int countAccountsAbove1Lakh() {

        return (int) accounts.stream()
                .filter(account -> account.getBalance() > 100000)
                .count();
    }

    @Override
    public Map<String, Long> countAccountsByType() {

        if (accounts == null) {
            return Collections.emptyMap();
        }

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
