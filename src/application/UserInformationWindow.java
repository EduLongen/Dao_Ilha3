package application;

import db.DB;
import model.dao.impl.UsuarioDaoJDBC;
import model.entities.CurrentUser;
import model.entities.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserInformationWindow extends JFrame {

    private JTable userTable;
    private DefaultTableModel tableModel;

    private JButton updateButton;
    private JButton deleteButton;

    private UsuarioDaoJDBC usuarioDao;

    public UserInformationWindow(DB db) {
        setTitle("User Information");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        usuarioDao = new UsuarioDaoJDBC(db.getConnection());

        // Create components
        tableModel = new DefaultTableModel();
        userTable = new JTable(tableModel);
        updateButton = new JButton("Update Account");
        deleteButton = new JButton("Delete Account");

        // Create panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(userTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add panel to frame
        add(mainPanel);

        // Set up table columns
        String[] columns = {"ID", "Name", "Sobrenome", "Email", "Senha"}; // Add additional columns as needed
        tableModel.setColumnIdentifiers(columns);

        // Load and display users
        loadUsers();

        // Add action listeners
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row
                int selectedRow = userTable.getSelectedRow();

                if (selectedRow != -1) {
                    // Get the user ID from the selected row
                    Integer userId = (Integer) tableModel.getValueAt(selectedRow, 0);

                    // Retrieve the Usuario object using the ID
                    Usuario selectedUser = usuarioDao.findById(userId);

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
                // Get the selected row
                int selectedRow = userTable.getSelectedRow();

                if (selectedRow != -1) {
                    // Get the user ID from the selected row
                    Integer userId = (Integer) tableModel.getValueAt(selectedRow, 0);

                    // Retrieve the Usuario object using the ID
                    Usuario selectedUser = usuarioDao.findById(userId);

                    // Confirm deletion
                    int choice = JOptionPane.showConfirmDialog(UserInformationWindow.this,
                            "Are you sure you want to delete your account?",
                            "Delete Account",
                            JOptionPane.YES_NO_OPTION);

                    if (choice == JOptionPane.YES_OPTION) {
                        // Perform deletion
                        // Set the current user ID before deletion
                        CurrentUser.setUserId(String.valueOf(selectedUser.getId()));
                        usuarioDao.deleteById(CurrentUser.getUserId());
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
        // Clear the existing table data
        tableModel.setRowCount(0);

        // Load users from the database
        List<Usuario> users = usuarioDao.findAll();

        // Add users to the table model
        for (Usuario user : users) {
            Object[] rowData = {user.getId(), user.getNome(), user.getSobrenome(), user.getEmail(), user.getSenha()};
            // Add additional data as needed
            tableModel.addRow(rowData);
        }
    }
}
