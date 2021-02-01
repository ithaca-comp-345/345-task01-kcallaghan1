package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        // Initial test
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance());

        // Different amount
        bankAccount = new BankAccount("a@b.com", 100);
        assertEquals(100, bankAccount.getBalance());

        // Zero amount
        bankAccount = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount.getBalance());

        // Negative amount
        bankAccount = new BankAccount("a@b.com", -100);
        assertEquals(-100, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{

        // Normal use, returns, balance is now 100
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());

        // Testing negative amount, shouldn't do anything
        bankAccount.withdraw(-100);
        assertEquals(100, bankAccount.getBalance());

        // Testing exception
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        // Withdrawing balance down to 0, after throwing exception
        bankAccount.withdraw(100);
        assertEquals(0, bankAccount.getBalance());

        // Amount > balance, should throw exception.
        assertThrows(InsufficientFundsException.class, () ->  bankAccount.withdraw(100));


    }

    @Test
    void isEmailValidTest(){
        
        // Normal use, OK
        assertTrue(BankAccount.isEmailValid( "a@b.com"));

        // Empty string test
        assertFalse( BankAccount.isEmailValid(""));

        // Different top-level domain test
        assertTrue(BankAccount.isEmailValid("a@b.tv"));

        // Missing top-level domain test
        assertFalse(BankAccount.isEmailValid("a@b"));

        // Missing name test
        assertFalse(BankAccount.isEmailValid("@b.com"));

        // Missing domain and top-level domain test
        assertFalse(BankAccount.isEmailValid("a@"));

        // Missing domain test
        assertFalse(BankAccount.isEmailValid("a@.com"));

        // First character is special character test
        assertFalse(BankAccount.isEmailValid(".@example.com"));

        // '@' is missing from email test
        assertFalse(BankAccount.isEmailValid("ab.com"));

        // Last character is special character test
        assertFalse(BankAccount.isEmailValid("abc-@mail.com"));

        // Two special characters together
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com"));

        // Containing illegal character test
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com"));

        // Containing special character surrounded by letters
        assertTrue(BankAccount.isEmailValid("abc-d@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc_def2@mail.com"));

        // Too-short domain test
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c"));

        // Illegal character in domain
        assertFalse(BankAccount.isEmailValid("abc.def@mail#archive.com"));

        // Too many '.' in domain
        assertFalse(BankAccount.isEmailValid("abc.def@mail..com"));

        // Domain with special character test
        assertTrue(BankAccount.isEmailValid("abc.def@mail-archive.com"));
        
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }
    
    @Test
    void isAmountValidTest() {

        // Valid amount with no decimals
        assertTrue(BankAccount.isAmountValid(100));

        // Valid amount with 1 decimal
        assertTrue(BankAccount.isAmountValid(100.1));

        // Valid amount with 2 decimals
        assertTrue(BankAccount.isAmountValid(100.01));

        // Invalid amount with 3 decimals
        assertFalse(BankAccount.isAmountValid(100.001));

        // Invalid negative amount
        assertFalse(BankAccount.isAmountValid(-100));

        // Invalid 0 amount
        assertFalse(BankAccount.isAmountValid(0));

        // Valid decimal amount
        assertTrue(BankAccount.isAmountValid(0.01));

        // Additional middle tests
        assertTrue(BankAccount.isAmountValid(1.1));
        assertTrue(BankAccount.isAmountValid(2.03));
        assertTrue(BankAccount.isAmountValid(4000.25));
    }

}