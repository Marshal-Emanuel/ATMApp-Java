import java.sql.*;
public class CashDispenser {
    private ATMBankDatabase bankDatabase;

    private ATMApplication atmApp;

    public CashDispenser(ATMBankDatabase bankDatabase) {
        this.bankDatabase = bankDatabase;
        //this.atmApp = atmApp;
    }

    public double  dispenseCashAndUpdateBalance(String accountNumber, double amount) {
        double availableCash = ATMApplication.balance;
        System.out.println("AtmApp balance: " + atmApp.balance);

        if (amount <= availableCash) {
            // Decrease the available cash balance
            availableCash -= amount;
            System.out.println("Withdraw Ammount: "+ amount + " balance: " + availableCash);

            // Update the available cash balance in the database
            boolean updated = bankDatabase.updateBalance(accountNumber, availableCash);

        return availableCash;
        }

        return 000;
    }
}
