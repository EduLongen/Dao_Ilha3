import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame{

    private JLabel FirstLabel;
    private JTextField TextField;
    private JButton PHPButton;
    private JPanel MainPanel;
    private JButton JAVAButton;

    public Main () {
        setContentPane(MainPanel);
        setTitle("Testing app");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        PHPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main.this, "Errou");
            }
        });
        JAVAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main.this, "Acertou. Parabéns");
            }
        });
    }

    public static void main(String[] args) {
        new Main();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
