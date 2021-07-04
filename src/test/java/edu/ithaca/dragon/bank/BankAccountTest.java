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

        // Testing invalid amount exception
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(-100));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(1.023));

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

        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -100));
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", 100.002));
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

    @Test
    void depositTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 100);

        bankAccount.deposit(100);

        // Normal use test
        assertEquals(200, bankAccount.getBalance());

        // Exception test
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-100));

        // The rest of the tests will be redundant if there is a working isAmountValid method.

    }

    @Test
    void transferTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 100);
        BankAccount bankAccount2 = new BankAccount("b@a.com", 200);

        try{
        bankAccount.transfer(bankAccount2, 50);
        }
        catch(Exception e){

        }

        // Normal use case
        assertEquals(50, bankAccount.getBalance());
        assertEquals(250, bankAccount2.getBalance());

        // With invalid amount
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(bankAccount2, -100));

        // Amounts should have remained the same
        assertEquals(50, bankAccount.getBalance());
        assertEquals(250, bankAccount2.getBalance());

        // With insufficient funds
        assertThrows(InsufficientFundsException.class, () -> bankAccount.transfer(bankAccount2, 100));

        // Amounts stay the same
        assertEquals(50, bankAccount.getBalance());
        assertEquals(250, bankAccount2.getBalance());

        // Empty an account
        try{
        bankAccount2.transfer(bankAccount, 250);
        }
        catch(Exception e){}

        assertEquals(300, bankAccount.getBalance());
        assertEquals(0, bankAccount2.getBalance());

        // The remaining cases should be covered by the isAmountValid method.
    }

}