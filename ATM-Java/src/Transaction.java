public class Transaction {
     UserAccount userAccount;
     ATMScreen screen;
    ATMBankDatabase bankDatabase;

    public Transaction(UserAccount userAccount, ATMScreen screen, ATMBankDatabase bankDatabase) {
        this.userAccount = userAccount;
        this.screen = screen;
        this.bankDatabase = bankDatabase;
    }

    public void execute() {

    }

    public double getAccountBalance() {
        return userAccount.getBalance();
    }

}
