package com.indium.bankingapp;

import com.indium.bankingapp.model.Account;
import com.indium.bankingapp.service.AccountService;
//import com.indium.bankingapp.service.AccountServiceArrListImpl;
//import com.indium.bankingapp.service.AccountServiceHashSetImpl;
//import com.indium.bankingapp.service.AccountServiceLinkedListImpl;
import com.indium.bankingapp.service.AccountServiceHashMapImpl;
//import com.indium.bankingapp.service.AccountServiceTreeMapImpl;
//import com.indium.bankingapp.service.AccountServiceTreeSetImpl;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class BankingAppMain {

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);


//        AccountService accountService = new AccountService();
//        AccountService accountService = new AccountServiceArrListImpl();
//          AccountService accountService = new AccountServiceLinkedListImpl();
//        AccountService accountService = new AccountServiceHashSetImpl();
//        AccountService accountService = new AccountServiceTreeSetImpl();
        AccountService accountService = new AccountServiceHashMapImpl();
//        AccountService accountService = new AccountServiceTreeMapImpl();

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
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createAndAddAccount(scanner, accountService);
                    break;
                case 2:
                    accountService.getAllAccounts();
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


        scanner.close();
    }
    private static void displayAllAccounts(AccountService accountService) {
        if(!accountService.getAllAccounts().isEmpty()) {
            Map<String, Account> allAccounts = accountService.getAllAccounts();
            for (Account acc : allAccounts.values()) {
                System.out.println("Account Number: " + acc.getAccountNumber());
                System.out.println("Account Holder name: "+acc.getAccountName());
                System.out.println("Account Type: " + acc.getAccountType());
                System.out.println("Balance: " + acc.getBalance());
                System.out.println("Rate of Interest: "+acc.getRoi());
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
        System.out.print("Enter Rate of Interest: ");
        double roi = scanner.nextDouble();
        scanner.nextLine();

        Account account = new Account(accountNumber, accountName,accountType, roi,balance);
        accountService.createAccount(account);
        System.out.println("Account added successfully.");
    }

    private static void viewAccount(Scanner scanner, AccountService accountService) {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        Account account = accountService.getAccount(accountNumber);
        if (account != null) {
            displayAccount(account);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void updateAccount(Scanner scanner, AccountService accountService) {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        Account account = accountService.getAccount(accountNumber);

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
            System.out.print("Enter New Account Rate of Interest: ");
            double newRoi = scanner.nextDouble();
            account.setRoi(newRoi);
            System.out.println("Account updated successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void deleteAccount(Scanner scanner, AccountService accountService) {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        boolean deleted = accountService.deleteAccount(accountNumber);

        if (!deleted) {
            System.out.println("Account deleted successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void displayAccount(Account account) {
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Account Holder: " + account.getAccountName());
        System.out.println("Account Type: " + account.getAccountType());
        System.out.println("Balance: " + account.getBalance());
        System.out.println("Rate of Interest: "+account.getRoi());
    }
    }
