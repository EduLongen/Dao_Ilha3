package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main extends JFrame {
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton signUpButton;

    public Main() {
        // Set up the frame
        setTitle("Login or Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
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
                // Open the login form
                new LoginForm().setVisible(true);
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the signup form
                new SignUpForm().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}

class LoginForm extends JFrame {
    public LoginForm() {
        // Set up the login form
        setTitle("Login Form");
        setSize(300, 150);
        setLocationRelativeTo(null);

        // Create components for login form
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        // Create panel for login form
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(usernameLabel);
        panel.add(usernameField);
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
                // Add your login logic here
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();

                // Simulate successful login
                if (isValidLogin(username, new String(password))) {
                    // After successful login, open the InventoryWindow
                    new InventoryWindow().setVisible(true);
                    // Close the login form
                    dispose();
                } else {
                    // Show an error message for invalid login
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Invalid username or password",
                            "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean isValidLogin(String username, String password) {
        // Perform actual login validation logic here
        // For demonstration purposes, always return true
        return true;
    }
}

class SignUpForm extends JFrame {
    public SignUpForm() {
        // Set up the signup form
        setTitle("Sign Up Form");
        setSize(300, 150);
        setLocationRelativeTo(null);

        // Create components for signup form
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton signUpButton = new JButton("Sign Up");

        // Create panel for signup form
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(signUpButton);

        // Add panel to signup form
        add(panel);

        // Add action listener to signup button
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your signup logic here
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                // Process the signup information
                // For demonstration purposes, show a message dialog
                JOptionPane.showMessageDialog(SignUpForm.this,
                        "Username: " + username + "\nPassword: " + new String(password),
                        "Sign Up Successful", JOptionPane.INFORMATION_MESSAGE);
                // You should replace the message dialog with your actual signup logic
            }
        });

        // Set default close operation to hide the form
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}
