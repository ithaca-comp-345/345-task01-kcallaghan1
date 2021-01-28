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
        assertEquals(100, bankAccount.getBalance());

        // Amount > balance, should throw exception.
        assertThrows(InsufficientFundsException.class, () ->  bankAccount.withdraw(100));


    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        assertFalse( BankAccount.isEmailValid(""));
        assertTrue(BankAccount.isEmailValid("a@b.org"));
        assertFalse(BankAccount.isEmailValid("a@b"));
        assertFalse(BankAccount.isEmailValid("@b.com"));
        assertFalse(BankAccount.isEmailValid("a@"));

        //Add a boundary case of whether '@' and '.' are right next to each other
        assertTrue(BankAccount.isEmailValid(".@example.com")); // This is OK.
        assertFalse(BankAccount.isEmailValid("example@.com")); // This is NOT.

        //Add a boundary case if '@' is missing in a nonempty email string 
        assertFalse(BankAccount.isEmailValid("ab.com"));
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}