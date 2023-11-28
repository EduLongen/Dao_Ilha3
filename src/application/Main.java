package application;

import db.DB;
import model.dao.impl.UsuarioDaoJDBC;
import model.entities.CurrentUser;
import model.entities.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private static DB db;
    private UsuarioDaoJDBC usuarioDao;
    private JPanel mainPanel;
    private static Main currentMainInstance; // Keep a reference to the current Main instance
    private JButton loginButton;
    private JButton signUpButton;
    private LoginForm loginForm;

    public Main() {

        // Initialize your Database instance
        db = new DB();

        // Initialize UsuarioDaoJDBC
        usuarioDao = new UsuarioDaoJDBC(db.getConnection());

        loginForm = new LoginForm(db, usuarioDao); // Initialize loginForm here

        // Set up the frame
        setTitle("KEEP INVENTORY");
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
                loginForm.setVisible(true);

                // Dispose Main only if login is successful
                if (loginForm.isLoginSuccessful()) {
                    dispose();
                }
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

    public static Main getCurrentMainInstance() {
        return currentMainInstance;
    }

    public static void exibirTela() {
        SwingUtilities.invokeLater(() -> {
            Main tela = new Main();
            currentMainInstance = tela; // Set the current Main instance
            tela.setExtendedState(JFrame.MAXIMIZED_BOTH);
            tela.setVisible(true);
        });
    }

    public static void main(String[] args) {
        Main.exibirTela();
    }

    // Add a method to check if the login was successful
    public boolean isLoginSuccessful() {
        return loginForm.isLoginSuccessful();
    }

}

class LoginForm extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private DB db;
    private UsuarioDaoJDBC usuarioDao;
    private boolean loginSuccessful = false;

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public LoginForm(DB db, UsuarioDaoJDBC usuarioDao) {
        // Set up the login form
        setTitle("Login Form");
        setSize(400, 200);
        setLocationRelativeTo(null);

        // Initialize the database and UsuarioDaoJDBC
        this.db = db;
        this.usuarioDao = usuarioDao;

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
                    // After successful login, set the current user
                    CurrentUser.setUserId(String.valueOf(usuario.getId()));
                    // After successful login, open the DashboardWindow
                    new DashboardWindow().setVisible(true);
                    // Set the login status to true
                    loginSuccessful = true;
                    // Close the login form
                    dispose();
                    // Close the Main frame using the reference to the current instance
                    Main.getCurrentMainInstance().dispose();

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

                // Automatically log in the user after signup
                Usuario usuario = usuarioDao.findByEmail(email, new String(senha));
                if (usuario != null) {
                    // Open the DashboardWindow after successful login
                    new DashboardWindow().setVisible(true);
                }
            }
        });

        // Set default close operation to hide the form
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}

