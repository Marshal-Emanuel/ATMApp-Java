import javax.swing.*;
import java.awt.*;

public class ATMScreen extends JPanel {
    private JTextArea screenText;

    public ATMScreen() {
        screenText = new JTextArea(10, 20);
        screenText.setFont(new Font("Monospaced", Font.PLAIN, 18));
        screenText.setEditable(false);
        screenText.setBackground(Color.BLACK);
        screenText.setForeground(Color.GREEN);
        add(screenText);
    }

    public String getText() {
        return screenText.getText();
    }

    public void setText(String text) {
        screenText.setText(text);
    }

    public void appendText(String text) {
        screenText.append(text);
    }
}
