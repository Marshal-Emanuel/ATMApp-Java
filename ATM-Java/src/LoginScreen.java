import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    private ATMBankDatabase bankDatabase;
    private JTextField accountNumberField;
    private JPasswordField pinField;
    
    public LoginScreen(ATMBankDatabase bankDatabase) {
        this.bankDatabase = bankDatabase;
        setTitle("ATM Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        accountNumberField = new JTextField(20);
        pinField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));
        loginPanel.add(new JLabel("Account Number:"));
        loginPanel.add(accountNumberField);
        loginPanel.add(new JLabel("PIN:"));
        loginPanel.add(pinField);
        loginPanel.add(new JLabel());  // Placeholder for layout
        loginPanel.add(loginButton);

        add(loginPanel, BorderLayout.CENTER);

        // Add action listener to the login button
        loginButton.addActionListener(new LoginButtonListener());

        // Set the frame size
        setSize(300, 150);

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Retrieve entered account number and PIN
            String accountNumber = accountNumberField.getText();
            String pin = new String(pinField.getPassword());

            // Implement login logic here:
            // 1. Check if the account exists in the database
            // 2. Verify the entered PIN
            // 3. If successful, proceed to the next screen; otherwise, display an error message
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ATMBankDatabase bankDatabase = new ATMBankDatabase(); // Create your database object here
            LoginScreen loginScreen = new LoginScreen(bankDatabase);
            loginScreen.setVisible(true);
        });
    }
}
