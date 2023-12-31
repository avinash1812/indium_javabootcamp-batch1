package com.indium.bankingapp;

import com.indium.bankingapp.model.AccountDetails;
import com.indium.bankingapp.service.AccountService;

import java.util.List;
import java.util.Scanner;

public class BankingAppMain {
    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // Create instances of accountService
    AccountService accountService = new AccountService();

    boolean exit = false;

        while (!exit) {
        System.out.println("\nBanking App Menu:");
        System.out.println("1. Add Account");
        System.out.println("2. View All Accounts");
        System.out.println("3. View Account");
        System.out.println("4. Update Account");
        System.out.println("5. Delete Account");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                createAndAddAccount(scanner, accountService);
                break;
            case 2:
                accountService.viewAllAccounts();
                displayAllAccounts(accountService);
                break;
            case 3:
                viewAccount(scanner, accountService);
                break;
            case 4:
                updateAccount(scanner, accountService);
                break;
            case 5:
                deleteAccount(scanner, accountService);
                break;
            case 6:
                exit = true;
                System.out.println("Thanks for visiting the app..");
                break;
            default:
                System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    // Close the scanner
        scanner.close();
}
    private static void displayAllAccounts(AccountService accountService) {
        if(!accountService.viewAllAccounts().isEmpty()) {
            System.out.println("All Accounts:");
            List<AccountDetails> allAccounts = accountService.viewAllAccounts();
            for (AccountDetails account : allAccounts) {
                displayAccountDetails(account);
            }
        }
        else {
            System.out.println("All Accounts: 0");
        }
    }

    private static void createAndAddAccount(Scanner scanner, AccountService accountService) {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter Account Holder: ");
        String accountName = scanner.nextLine();
        System.out.print("Enter Account Type: ");
        String accountType = scanner.nextLine();
        System.out.print("Enter Initial Balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        AccountDetails account = new AccountDetails(accountNumber, accountName,accountType, balance);
        accountService.addAccount(account);
        System.out.println("Account added successfully.");
    }

    private static void viewAccount(Scanner scanner, AccountService accountService) {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        AccountDetails account = accountService.findAccountByNumber(accountNumber);
        if (account != null) {
            displayAccountDetails(account);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void updateAccount(Scanner scanner, AccountService accountService) {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        AccountDetails account = accountService.findAccountByNumber(accountNumber);

        if (account != null) {
            System.out.print("Enter New Account Holder Name: ");
            String newAccountHolder = scanner.nextLine();
            account.setAccountName(newAccountHolder);
            System.out.print("Enter New Account Type: ");
            String newAccountType = scanner.nextLine();
            account.setAccountType(newAccountType);
            System.out.print("Enter New Account Balance: ");
            double newAccountBalance = scanner.nextDouble();
            account.setBalance(newAccountBalance);
            System.out.println("Account updated successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void deleteAccount(Scanner scanner, AccountService accountService) {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        boolean deleted = accountService.deleteAccount(accountNumber);

        if (deleted) {
            System.out.println("Account deleted successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void displayAccountDetails(AccountDetails account) {
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Account Holder: " + account.getAccountName());
        System.out.println("Account Type: " + account.getAccountType());
        System.out.println("Balance: " + account.getBalance());
    }
    }
