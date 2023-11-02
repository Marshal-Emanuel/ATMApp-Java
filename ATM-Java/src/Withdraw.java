public class Withdraw extends Transaction {
    private ATMKeypad keypad;
    private CashDispenser cashDispenser;

    public Withdraw(UserAccount userAccount, ATMScreen atmScreen, ATMBankDatabase atmBankDatabase,
                    ATMKeypad keypad, CashDispenser cashDispenser) {
        super(userAccount, atmScreen, atmBankDatabase);
        this.keypad = keypad;
        this.cashDispenser = cashDispenser;
    }

    @Override
    public void execute() {
        // Implement the specific steps of a withdrawal using the attributes and methods inherited from Transaction.
        // Interact with keypad, cash dispenser, and update the bank database as needed.
    }
}
