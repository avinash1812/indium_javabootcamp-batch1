package com.indium.bankingapp;

import com.indium.bankingapp.model.Account;
import com.indium.bankingapp.service.AccountService;
import com.indium.bankingapp.service.AccountServiceImpl;

import javax.security.auth.login.AccountException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BankingAppMain {

    public static void main(String[] args) {
//        com.indium.bankingapp.service.AccountService service = new com.indium.bankingapp.service.AccountServiceImpl();

        Scanner scanner = new Scanner(System.in);
        ExecutorService executor = Executors.newCachedThreadPool();


//        com.indium.bankingapp.service.AccountService accountService = new com.indium.bankingapp.service.AccountService();
        AccountService accountService = new AccountServiceImpl();
//          com.indium.bankingapp.service.AccountService accountService = new AccountServiceLinkedListImpl();
//        com.indium.bankingapp.service.AccountService accountService = new AccountServiceHashSetImpl();
//        com.indium.bankingapp.service.AccountService accountService = new AccountServiceTreeSetImpl();
//        com.indium.bankingapp.service.AccountService accountService = new AccountServiceHashMapImpl();
//        com.indium.bankingapp.service.AccountService accountService = new AccountServiceTreeMapImpl();

        boolean exit = false;

        while (!exit) {
            System.out.println("\nBanking App Menu:");
            System.out.println("1. Add Account");
            System.out.println("2. View All Accounts");
            System.out.println("3. View Account");
            System.out.println("4. Update Account");
            System.out.println("5. Delete Account");
            System.out.println("6. Show Statistics");
            System.out.println("7. Import");
            System.out.println("8. Export");
            System.out.println("9. Exit");
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
                    showStatisticsMenu(accountService, scanner);
                    break;
                case 7:
                    Callable<Boolean> importThread = new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            System.out.println(Thread.currentThread() + " Waiting for 5s to start import process.");
                            Thread.sleep(5000);
                            accountService.bulkImport();
                            fileImport(accountService);
                            return true;
                        }
                    };

                    Future<Boolean> importFuture = executor.submit(importThread);
                    System.out.println(Thread.currentThread().getName() + " Import process triggered");

                    break;
                case 8:
                    Callable<Boolean> exportThread = new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            System.out.println(Thread.currentThread() + " Waiting for 5s to start export process.");
                            Thread.sleep(5000);
                            accountService.bulkExport();
                            fileExport(accountService);
                            return true;
                        }
                    };

                    Future<Boolean> exportFuture = executor.submit(exportThread);
                    System.out.println(Thread.currentThread().getName() + " Export process triggered");


                    break;
                case 9:
                    exit = true;
                    System.out.println("Thanks for visiting the app..");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        scanner.close();
    }
    private static void fileExport(AccountService service) {
        String exportFilePath = "D:\\Training\\indium_javabootcamp-batch1\\assignments\\corejava\\core-java-assignments\\bankingapp-with-impexp\\input\\product-output.txt";

        String delimiter = ",";

        try (FileWriter fileWriter = new FileWriter(new File(exportFilePath), false)) {
            Collection<Account> accounts = service.getAllAccounts();

            if (accounts.isEmpty()) {
                System.out.println("No accounts to export.");
            } else {
                for (Account account : accounts) {

                    String line = account.getAccountNumber() + delimiter +
                            account.getAccountName() + delimiter +
                            account.getAccountType() + delimiter +
                            account.getRoi() + delimiter +
                            account.getBalance();


                    fileWriter.write(line + "\n");
                }

                System.out.println("Exported");
            }
        } catch (IOException e) {
            System.err.println("Error exporting data to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void fileImport(AccountService service) {

        String importFilePath = "D:\\Training\\indium_javabootcamp-batch1\\assignments\\corejava\\core-java-assignments\\bankingapp-with-impexp\\input\\product-input.txt";


        String delimiter = ",";


        try (Scanner fileScanner = new Scanner(new File(importFilePath))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(delimiter);
                if (parts.length == 5) {
                    int accountNumber = Integer.parseInt(parts[0]);
                    String accountName = parts[1];
                    String accountType = parts[2];
                    double roi = Double.parseDouble(parts[3]);
                    double balance = Double.parseDouble(parts[4]);


                    Account account = new Account(accountNumber, accountName, accountType, roi, balance);
                    service.createAccount(account);
                } else {
                    System.out.println("Invalid data format in import file: " + line);
                }
            }
            System.out.println("Imported");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + importFilePath);
        }
    }
    private static void showStatisticsMenu(AccountService accountService, Scanner scanner) {
        while (true) {
            System.out.println("\nStatistics Menu:");
            System.out.println("1. No. of accounts with balance more than 1 Lakh");
            System.out.println("2. No. of accounts by account type");
            System.out.println("3. No. of accounts by account type with sorting");
            System.out.println("4. Average balance by account type");
            System.out.println("5. List account IDs whose account name contains a given name");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    int count = accountService.countAccountsAbove1Lakh();
                    System.out.println("Number of accounts with balance greater than 1 lakh: " + count);
                    break;

                case 2:
                    numOfAccountsByAccType(accountService);
                    break;

                case 3:
                    numOfAccountsWithAccTypeBySorting(accountService);
                    break;

                case 4:
                    averageBalByAccType(accountService);
                    break;
                case 5:
                    listAccIdByName(scanner,accountService);
                    break;
                case 6:
                    return;
            }
        }
    }
    private static void listAccIdByName(Scanner scanner, AccountService accountService){
        System.out.print("Enter a name to search for: ");
        String searchName = scanner.nextLine();
        List<Integer> matchingAccountIds = accountService.listAccountIdsByAccountNameContains(searchName);
        if (matchingAccountIds.isEmpty()) {
            System.out.println("No matching accounts found.");
        } else {
            System.out.println("Matching Account Number:");
            for (Integer accountId : matchingAccountIds) {
                System.out.println(accountId);
            }
        }
    }
    private static void averageBalByAccType(AccountService accountService){
        Map<String, Double> averageBalances = accountService.calculateAverageBalanceByType();
        System.out.println("Average Balances by Account Type:");
        for (Map.Entry<String, Double> entry : averageBalances.entrySet()) {
            System.out.println("Account Type: " + entry.getKey() + ", Average Balance: " + entry.getValue());
        }
    }
    private static void numOfAccountsWithAccTypeBySorting(AccountService accountService){
        List<Map.Entry<String, Integer>> sortedAccountTypeCounts = accountService.countAndSortAccountsByType();
        System.out.println("Account Counts by Type (Sorted):");
        for (Map.Entry<String, Integer> entry : sortedAccountTypeCounts) {
            System.out.println("Account Type: " + entry.getKey() + ", Count: " + entry.getValue());
        }
    }
    private static void numOfAccountsByAccType(AccountService accountService){
        Map<String, Integer> accountTypeCounts = accountService.countAccountsByType();
        System.out.println("Account Counts by Type:");
        if (!accountTypeCounts.entrySet().isEmpty()) {
            for (Map.Entry<String, Integer> entry : accountTypeCounts.entrySet()) {
                System.out.println("Account Type: " + entry.getKey() + ", Count: " + entry.getValue());
            }
        } else {
            System.out.println("No Account available..Please add the account");
        }
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

    private static void createAndAddAccount(Scanner scanner, AccountService accountService) throws NumberFormatException  {
        System.out.print("Enter Account Number: ");
        Integer accountNumber = scanner.nextInt();
        scanner.nextLine();
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

    private static void viewAccount(Scanner scanner, AccountService accountService) throws InputMismatchException {
        System.out.print("Enter Account Number: ");
        Integer accountNumber = scanner.nextInt();
        Account account = accountService.getAccount(accountNumber);
        if (account != null) {
            displayAccount(account);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void updateAccount(Scanner scanner, AccountService accountService) {
        System.out.print("Enter Account Number: ");
        Integer accountNumber = scanner.nextInt();
        scanner.nextLine();
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
        Integer accountNumber = scanner.nextInt();
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
