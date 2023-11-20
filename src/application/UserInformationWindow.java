package application;

import db.DB;
import model.dao.impl.UsuarioDaoJDBC;
import model.entities.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserInformationWindow extends JFrame {

    private JList<Usuario> userList;
    private DefaultListModel<Usuario> userListModel;

    private JButton updateButton;
    private JButton deleteButton;

    private UsuarioDaoJDBC usuarioDao;

    public UserInformationWindow(DB db) {
        setTitle("User Information");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        usuarioDao = new UsuarioDaoJDBC(db.getConnection());

        // Create components
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        updateButton = new JButton("Update Account");
        deleteButton = new JButton("Delete Account");

        // Create panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(userList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add panel to frame
        add(mainPanel);

        // Load and display users
        loadUsers();

        // Add action listeners
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected user
                Usuario selectedUser = userList.getSelectedValue();

                if (selectedUser != null) {
                    // Open the update window with the selected user
                    new UpdateUserWindow(selectedUser, db, UserInformationWindow.this).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(UserInformationWindow.this,
                            "Please select a user to update.",
                            "Update User",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected user
                Usuario selectedUser = userList.getSelectedValue();

                if (selectedUser != null) {
                    // Confirm deletion
                    int choice = JOptionPane.showConfirmDialog(UserInformationWindow.this,
                            "Are you sure you want to delete your account?",
                            "Delete Account",
                            JOptionPane.YES_NO_OPTION);

                    if (choice == JOptionPane.YES_OPTION) {
                        // Perform deletion
                        usuarioDao.deleteById(selectedUser.getId());
                        // Reload and display users
                        loadUsers();
                    }
                } else {
                    JOptionPane.showMessageDialog(UserInformationWindow.this,
                            "Please select a user to delete.",
                            "Delete User",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public void loadUsers() {
        // Clear the existing list
        userListModel.clear();

        // Load users from the database
        List<Usuario> users = usuarioDao.findAll();

        // Add users to the list model
        for (Usuario user : users) {
            userListModel.addElement(user);
        }
    }
}

