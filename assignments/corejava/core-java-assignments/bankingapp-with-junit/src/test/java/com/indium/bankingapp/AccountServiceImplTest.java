package com.indium.bankingapp;

import com.indium.bankingapp.model.Account;
import com.indium.bankingapp.service.AccountService;
import com.indium.bankingapp.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceImplTest {

    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        // Initialize the accountService before each test
        accountService = new AccountServiceImpl();
    }

    @Test
    public void testCreateAccount() {
        Account account = new Account(1, "John Doe", "Savings", 5.0, 1000.0);

        // Attempt to create the account
        boolean result = accountService.createAccount(account);

        // Check if the account was created successfully
        assertTrue(result);
    }

    @Test
    public void testGetAllAccounts() {
        // Add test logic to retrieve all accounts and assert the result
        List<Account> accounts = accountService.getAllAccounts();

        // Add assertions to check the retrieved accounts
        assertNotNull(accounts);
//        assertTrue(accounts.isEmpty());
    }

    @Test
    public void testGetAccount() {
        // Add test logic to retrieve a specific account and assert the result
        Account account = accountService.getAccount(0);

        // Add assertions to check the retrieved account
        assertNull(account);
    }

    @Test
    public void testUpdateAccount() {
        // Add test logic to update an account and assert the result
        Account account = new Account(1, "John Doe", "Savings", 5.0, 1000.0);

        // Create the account
        boolean createResult = accountService.createAccount(account);
        assertTrue(createResult);

        // Update the account
        account.setAccountName("Updated Name");
        boolean updateResult = accountService.updateAccount(account);
        assertTrue(updateResult);

        // Retrieve the updated account
        Account updatedAccount = accountService.getAccount(1);

        // Add assertions to check the updated account
        assertNotNull(updatedAccount);
        assertEquals("Updated Name", updatedAccount.getAccountName());
    }

    @Test
    public void testDeleteAccount() {
        // Add test logic to delete an account and assert the result
        Account account = new Account(1, "John Doe", "Savings", 5.0, 1000.0);

        // Create the account
        boolean createResult = accountService.createAccount(account);
        assertTrue(createResult);

        // Delete the account
        boolean deleteResult = accountService.deleteAccount(1);
        assertTrue(deleteResult);

        // Attempt to retrieve the deleted account
        Account deletedAccount = accountService.getAccount(1);

        // Add assertions to check that the account is deleted
        assertNull(deletedAccount);
    }

    // Add more test methods as needed to test other AccountServiceImpl functionality
}
