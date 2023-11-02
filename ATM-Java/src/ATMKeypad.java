import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ATMKeypad extends JPanel {
    private JButton[] numericButtons;
    private JButton buttonOkay;
    private JButton buttonClear;
    private JButton buttonCancel;
    private JButton buttonWithdraw;

    public ATMKeypad() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;

        numericButtons = new JButton[10]; // Buttons 0-9
        for (int i = 0; i < 10; i++) {
            numericButtons[i] = new JButton(String.valueOf(i));
            numericButtons[i].setFont(new Font("SansSerif", Font.PLAIN, 18));
            numericButtons[i].setBackground(Color.LIGHT_GRAY);
            numericButtons[i].setForeground(Color.BLACK);
            gbc.gridx = i % 3;
            gbc.gridy = i / 3;
            add(numericButtons[i], gbc);
        }

        buttonOkay = new JButton("Okay");
        buttonOkay.setFont(new Font("SansSerif", Font.PLAIN, 16));
        buttonOkay.setBackground(Color.BLUE);
        buttonOkay.setForeground(Color.WHITE);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(buttonOkay, gbc);

        buttonWithdraw = new JButton("Withdraw");
        buttonWithdraw.setFont(new Font("SansSerif", Font.PLAIN, 16));
        buttonWithdraw.setBackground(Color.PINK);
        buttonWithdraw.setForeground(Color.black);
        gbc.gridy = 1;
        add(buttonWithdraw, gbc);

        buttonClear = new JButton("Clear");
        buttonClear.setFont(new Font("SansSerif", Font.PLAIN, 16));
        buttonClear.setBackground(Color.RED);
        buttonClear.setForeground(Color.WHITE);
        gbc.gridy = 2;
        add(buttonClear, gbc);

        buttonCancel = new JButton("Cancel");
        buttonCancel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        buttonCancel.setBackground(Color.RED);
        buttonCancel.setForeground(Color.WHITE);
        gbc.gridy = 3;
        add(buttonCancel, gbc);
    }

    public void addButtonActionListener(ActionListener listener) {
        for (int i = 0; i < 10; i++) {
            numericButtons[i].addActionListener(listener);
        }
        buttonOkay.addActionListener(listener);
        buttonClear.addActionListener(listener);
        buttonCancel.addActionListener(listener);
        buttonWithdraw.addActionListener(listener);
    }
}
