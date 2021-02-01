package edu.ithaca.dragon.bank;

import javax.lang.model.util.ElementScanner14;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * If amount is less than 0, do nothing
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if(amount < 0){
            balance-=0; 
        }
        else if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1 || email.indexOf('@')== 0){
            return false;
            
        }
        else if(email.endsWith("@") || email.endsWith(".")){
            return false;
        }
        else if(email.contains(".")==false){
            return false;
        }
        else if(email.contains("@")==false){
            return false;
        }
        else if(email.contains("@.")){
            return false;
        }
        
        else {
            return true;
        }
    }

    /**
     * @return true if amount is positive and has two or fewer decimal places, otherwise false
     */
    public static boolean isAmountValid(double amount){
        // TODO: implement function
        return false;
    }
}
