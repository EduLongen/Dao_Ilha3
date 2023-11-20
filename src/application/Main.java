package application;

import db.DB;
import model.dao.impl.UsuarioDaoJDBC;
import model.entities.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private JButton loginButton;
    private JButton signUpButton;
    private JPanel mainPanel;
    private DB db;
    private UsuarioDaoJDBC usuarioDao;

    public Main() {

        // Initialize your Database instance
        db = new DB();

        // Initialize UsuarioDaoJDBC
        usuarioDao = new UsuarioDaoJDBC(db.getConnection());

        // Set up the frame
        setTitle("Login or Sign Up");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        // Create buttons
        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");

        // Create panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));
        mainPanel.add(loginButton);
        mainPanel.add(signUpButton);

        // Add panel to frame
        add(mainPanel);

        // Add action listeners to buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the login form and pass the DB instance and UsuarioDaoJDBC
                new LoginForm(db, usuarioDao).setVisible(true);
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the signup form and pass the DB instance and UsuarioDaoJDBC
                new SignUpForm(db).setVisible(true);
            }
        });
    }

    public static void exibirTela() {
        SwingUtilities.invokeLater(() -> {
            Main tela = new Main();
            tela.setExtendedState(JFrame.MAXIMIZED_BOTH);
            tela.setVisible(true);
        });
    }

    public static void main(String[] args) {
        Main.exibirTela();
    }
}

class LoginForm extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private DB db;
    private UsuarioDaoJDBC usuarioDao;

    public LoginForm(DB db, UsuarioDaoJDBC usuarioDao) {
        // Set up the login form
        setTitle("Login Form");
        setSize(300, 150);
        setLocationRelativeTo(null);

        // Initialize the database and UsuarioDaoJDBC
        this.db = db;
        this.usuarioDao = this.usuarioDao;

        // Create components for login form
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        emailField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        // Create panel for login form
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(loginButton);

        // Add panel to login form
        add(panel);

        // Add action listener to login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the email and password from the fields
                String email = emailField.getText();
                char[] password = passwordField.getPassword();

                // Perform login using the UsuarioDaoJDBC
                Usuario usuario = usuarioDao.findByEmail(email, new String(password));

                if (usuario != null) {
                    // After successful login, open the DashboardWindow
                    new DashboardWindow().setVisible(true);
                    // Close the login form
                    dispose();
                } else {
                    // Show an error message for invalid login
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Invalid email or password",
                            "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}

class SignUpForm extends JFrame {

    private JTextField nomeField;
    private JTextField sobrenomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private DB db;
    private UsuarioDaoJDBC usuarioDao;

    public SignUpForm(DB db) {
        // Set up the signup form
        setTitle("Sign Up Form");
        setSize(500, 300);
        setLocationRelativeTo(null);

        // Initialize the database and UsuarioDaoJDBC
        this.db = db;
        this.usuarioDao = new UsuarioDaoJDBC(db.getConnection()); // Initialize the UsuarioDaoJDBC here

        // Create components for signup form
        JLabel nomeLabel = new JLabel("Nome:");
        JLabel sobrenomeLabel = new JLabel("Sobrenome:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel senhaLabel = new JLabel("Senha:");
        nomeField = new JTextField();
        sobrenomeField = new JTextField();
        emailField = new JTextField();
        senhaField = new JPasswordField();
        JButton signUpButton = new JButton("Sign Up");

        // Create panel for signup form
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(nomeLabel);
        panel.add(nomeField);
        panel.add(sobrenomeLabel);
        panel.add(sobrenomeField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(senhaLabel);
        panel.add(senhaField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(signUpButton);

        // Add panel to signup form
        add(panel);

        // Add action listener to signup button
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get user registration data from the fields
                String nome = nomeField.getText();
                String sobrenome = sobrenomeField.getText();
                String email = emailField.getText();
                char[] senha = senhaField.getPassword();

                // Create a new Usuario object
                Integer id = null;
                Usuario newUser = new Usuario(id, nome, sobrenome, email, new String(senha));

                // Perform user registration using the UsuarioDaoJDBC
                SignUpForm.this.usuarioDao.insert(newUser);

                // Show a success message (replace with actual logic)
                JOptionPane.showMessageDialog(SignUpForm.this,
                        "User registered successfully",
                        "Registration Successful", JOptionPane.INFORMATION_MESSAGE);

                // Close the signup form
                dispose();
            }
        });

        // Set default close operation to hide the form
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}

