package application;

import db.DB;
import model.dao.impl.UsuarioDaoJDBC;
import model.entities.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateUserWindow extends JFrame {

    private JTextField nomeField;
    private JTextField sobrenomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JButton updateButton;

    private UsuarioDaoJDBC usuarioDao;
    private UserInformationWindow parentWindow;
    private Usuario currentUser;

    public UpdateUserWindow(Usuario user, DB db, UserInformationWindow parentWindow) {
        this.currentUser = user;
        this.parentWindow = parentWindow;

        setTitle("Update User Information");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        usuarioDao = new UsuarioDaoJDBC(db.getConnection());

        // Create components for update form
        JLabel nomeLabel = new JLabel("Nome:");
        JLabel sobrenomeLabel = new JLabel("Sobrenome:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel senhaLabel = new JLabel("Senha:");
        nomeField = new JTextField(user.getNome());
        sobrenomeField = new JTextField(user.getSobrenome());
        emailField = new JTextField(user.getEmail());
        senhaField = new JPasswordField();
        updateButton = new JButton("Update");

        // Create panel for update form
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
        panel.add(updateButton);

        // Add panel to update form
        add(panel);

        // Add action listener to update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update user information
                currentUser.setNome(nomeField.getText());
                currentUser.setSobrenome(sobrenomeField.getText());
                currentUser.setEmail(emailField.getText());

                // Update password only if a new password is provided
                if (senhaField.getPassword().length > 0) {
                    currentUser.setSenha(new String(senhaField.getPassword()));
                }

                // Update user in the database
                usuarioDao.update(currentUser);

                // Show a success message (replace with actual logic)
                JOptionPane.showMessageDialog(UpdateUserWindow.this,
                        "User information updated successfully",
                        "Update Successful", JOptionPane.INFORMATION_MESSAGE);

                // Refresh the parent window
                parentWindow.loadUsers();

                // Close the update form
                dispose();
            }
        });
    }
}
