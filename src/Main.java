import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame{

    private JButton PHPButton;
    private JPanel MainPanel;
    private JButton button_cadastrar_usuario;
    private JButton button_login;
    private JButton JAVAButton;

    public Main () {
        setContentPane(MainPanel);
        setTitle("Keep Inventory [BETA]");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        button_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main.this, "Realizando Login...");
            }
        });
        button_cadastrar_usuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main.this, "Criando novo cadastro");
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
