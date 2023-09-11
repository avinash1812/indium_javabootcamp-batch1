package com.indium.bankingapp;

import com.indium.bankingapp.model.Account;
import com.indium.bankingapp.service.AccountService;
import com.indium.bankingapp.service.AccountServiceArrListImpl;
//import com.indium.bankingapp.service.AccountServiceHashSetImpl;
//import com.indium.bankingapp.service.AccountServiceLinkedListImpl;
//import com.indium.bankingapp.service.AccountServiceHashMapImpl;
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
        AccountService accountService = new AccountServiceArrListImpl();
//          AccountService accountService = new AccountServiceLinkedListImpl();
//        AccountService accountService = new AccountServiceHashSetImpl();
//        AccountService accountService = new AccountServiceTreeSetImpl();
//        AccountService accountService = new AccountServiceHashMapImpl();
//        AccountService accountService = new AccountServiceTreeMapImpl();

        boolean exit = false;

        while (!exit) {
            System.out.println("\nBanking App Menu:");
            System.out.println("1. Add Account");
            System.out.println("2. View All Accounts");
            System.out.println("3. View Account");
            System.out.println("4. Update Account");
            System.out.println("5. Delete Account");
            System.out.println("6. No. of accounts which has balance more than 1 Lakh");
            System.out.println("7. Show no of accounts by account type");
            System.out.println("8. Show no of accounts by account type with sorting");
            System.out.println("9. Show average balance by account type");
            System.out.println("10. List account ids whose account name contains given name");
            System.out.println("11. Exit");
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
                    int count = accountService.countAccountsAbove1Lakh();
                    System.out.println("Number of accounts with balance greater than 1 lakh: " + count);
                    break;

                case 7: // New option to count accounts by type
                    Map<String, Integer> accountTypeCounts = accountService.countAccountsByType();
                    System.out.println("Account Counts by Type:");
                    if(!accountTypeCounts.entrySet().isEmpty()) {
                        for (Map.Entry<String, Integer> entry : accountTypeCounts.entrySet()) {
                            System.out.println("Account Type: " + entry.getKey() + ", Count: " + entry.getValue());
                        }
                    }else{
                        System.out.println("No Account available..Please add the account");
                    }
                    break;

                case 8: // New option to count and sort accounts by type
                    List<Map.Entry<String, Integer>> sortedAccountTypeCounts = accountService.countAndSortAccountsByType();
                    System.out.println("Account Counts by Type (Sorted):");
                    for (Map.Entry<String, Integer> entry : sortedAccountTypeCounts) {
                        System.out.println("Account Type: " + entry.getKey() + ", Count: " + entry.getValue());
                    }
                    break;

                case 9: // New option to calculate and display average balances by type
                    Map<String, Double> averageBalances = accountService.calculateAverageBalanceByType();
                    System.out.println("Average Balances by Account Type:");
                    for (Map.Entry<String, Double> entry : averageBalances.entrySet()) {
                        System.out.println("Account Type: " + entry.getKey() + ", Average Balance: " + entry.getValue());
                    }
                    break;
                case 10: // New option to list account IDs by account name containing a given name
                    System.out.print("Enter a name to search for: ");
                    String searchName = scanner.nextLine();
                    List<String> matchingAccountIds = accountService.listAccountIdsByAccountNameContains(searchName);
                    if (matchingAccountIds.isEmpty()) {
                        System.out.println("No matching accounts found.");
                    } else {
                        System.out.println("Matching Account Number:");
                        for (String accountId : matchingAccountIds) {
                            System.out.println(accountId);
                        }
                    }
                    break;
                case 11:
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
            System.out.println("All Accounts:");
            List<Account> allAccounts =accountService.getAllAccounts();
            for (Account account : allAccounts) {
                displayAccount(account);
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
