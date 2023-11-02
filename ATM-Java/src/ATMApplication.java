import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ATMApplication extends JFrame {
    private ATMScreen screen;
    private ATMKeypad keypad;
    private int state;
    private StringBuilder userInput;
    private ATMBankDatabase bankDatabase;
    private CashDispenser cashDispenser;
    private UserAccount userAccount;

    static double balance; // Store the current balance

    public ATMApplication() {
        setTitle("ATM Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        screen = new ATMScreen();
        keypad = new ATMKeypad();
        userInput = new StringBuilder();
        bankDatabase = new ATMBankDatabase();

        add(screen, BorderLayout.NORTH);

        JPanel keypadPanel = new JPanel(new BorderLayout());
        keypadPanel.add(keypad, BorderLayout.CENTER);
        add(keypadPanel, BorderLayout.CENTER);

        keypad.addButtonActionListener(new ButtonListener());

        setSize(400, 600);
        setLocationRelativeTo(null);
        screen.setText(" <><><><><> Welcome! <><><><><>\n-->Enter Account Number:" + "\n->");
        state = 0; // Set the initial state
        balance = 0.0; // Initialize balance to 0.0
    }

    class ButtonListener implements ActionListener {
        private String accountNumberInput;

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String buttonText = button.getText();

            if (buttonText.equals("Okay")) {
                screen.appendText("\n");
                switch (state) {
                    case 0:
                        accountNumberInput = userInput.toString();
                        String actualAccountNo = bankDatabase.isAccountNumberAvailable(accountNumberInput);
                        if (accountNumberInput.equals(actualAccountNo)) {
                            screen.appendText("\n");
                            screen.appendText("-->Enter PIN: \n->");
                            state = 1;
                            userInput.setLength(0);
                        } else {
                            screen.appendText("\n");
                            screen.appendText("404 :( Account not found..");
                            screen.appendText("\nPress Clear to Retry");
                            userInput.setLength(0);
                        }
                        break;
                    case 1:
                        try {
                            String actualPin = bankDatabase.isPINValid(accountNumberInput);
                            balance = bankDatabase.getBalance(accountNumberInput);

                            String pin = userInput.toString();
                            if (pin.equals(actualPin)) {
                                screen.setText("");
                                screen.appendText("\n<><><><>PIN verified<><><><>\nWelcome Acc.No " + accountNumberInput + "");
                                screen.appendText("\nBalance: Ksh " + balance);
                                userInput.setLength(0);
                                screen.appendText("\n\n-->Enter Amount to withdraw:\n->");
                                state = 2; // Move to withdrawal state
                            } else {
                                screen.appendText("\nIncorrect PIN. To try again, Press \"Clear\".");
                                userInput.setLength(0);
                            }
                        } catch (SQLException exception) {
                            exception.printStackTrace();
                        }
                        break;
                    case 2:
                        if (buttonText.equals("Clear")) {
                            screen.setText("           Welcome!              \n     Enter Account Number" + "\n");
                            userInput.setLength(0);
                            state = 0;
                        } else if (buttonText.equals("Cancel")) {
                            System.exit(0);
                        } else {
                            if (buttonText.equals("Okay")) {
                                String withdrawAmount = userInput.toString();
                                if (withdrawAmount.length() > 0) {
                                    screen.appendText("\n");
                                    double toWithdraw;
                                    toWithdraw = Double.parseDouble(withdrawAmount);
                                    System.out.println("Withdraw Ammount entered: "+toWithdraw);
                                    //object of the cashDispenser
                                    cashDispenser = new CashDispenser(bankDatabase);

                                    //calling the cash dispenser method for handling cash
                                    if(balance>toWithdraw) {
                                        try {
                                            balance = cashDispenser.dispenseCashAndUpdateBalance(accountNumberInput, toWithdraw);
                                            screen.setText("");
                                            screen.appendText("\n Confirmed you have withdrawn:\n  -Ksh " + toWithdraw);
                                            screen.appendText("\n\n *New ATM balance is:\n  Ksh " + balance);
                                            screen.appendText("\n\n{~_~}It was a pleasure serving you");
                                        } catch (Exception f) {
                                            screen.appendText("Invalid withdrawal amount.");
                                        }
                                    }
                                    else{
                                        screen.appendText(" \n\nAccount balance too Low :(\n Press \"clear\" to continue");
                                    }

                                    userInput.setLength(0); // Clear the withdrawal amount input
                                    state = 0; // Return to the main menu
                                }
                            } else {
                                // Check if the button text is a numeric character
                                if (buttonText.matches("[0-9]")) {
                                    userInput.append(buttonText);
                                    userInput.append(buttonText);
                                    screen.appendText("*"); // Display an asterisk for each digit of the withdrawal amount
                                }
                            }
                        }
                        break;

                }
            } else if (buttonText.equals("Clear")) {
                screen.setText("           Welcome!              \n     Enter Account Number" + "\n");
                userInput.setLength(0);
                state = 0;
            } else if (buttonText.equals("Cancel")) {
                System.exit(0);
            } else {
                userInput.append(buttonText);
                screen.appendText(buttonText);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ATMApplication atmApp = new ATMApplication();
            atmApp.setVisible(true);
        });
    }
}
