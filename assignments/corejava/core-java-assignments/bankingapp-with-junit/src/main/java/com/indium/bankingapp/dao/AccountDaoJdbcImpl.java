package com.indium.bankingapp.dao;

import com.indium.bankingapp.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoJdbcImpl implements AccountDAO {

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public AccountDaoJdbcImpl(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
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
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE accounts SET account_name = ?, account_type = ?, roi = ?, balance = ? WHERE account_number = ?")) {

            preparedStatement.setString(1, account.getAccountName());
            preparedStatement.setString(2, account.getAccountType());
            preparedStatement.setDouble(3, account.getRoi());
            preparedStatement.setDouble(4, account.getBalance());
            preparedStatement.setInt(5, account.getAccountNumber());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
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



    private Account extractAccountFromResultSet(ResultSet resultSet) throws SQLException {
        int accountNumber = resultSet.getInt("account_number");
        String accountName = resultSet.getString("account_name");
        String accountType = resultSet.getString("account_type");
        double roi = resultSet.getDouble("roi");
        double balance = resultSet.getDouble("balance");

        return new Account(accountNumber, accountName, accountType, roi, balance);
    }
}
