package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardWindow extends JFrame {
    private JButton usuariosButton;
    private JButton itensButton;
    private JButton emprestimosButton;
    private JButton historicoButton;
    private JButton anexosButton;

    public DashboardWindow() {
        // Set up the dashboard window
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Create buttons for accessing different features
        usuariosButton = new JButton("Usuarios");
        itensButton = new JButton("Itens");
        emprestimosButton = new JButton("Emprestimos");
        historicoButton = new JButton("Histórico");
        anexosButton = new JButton("Anexos");

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));
        buttonPanel.add(usuariosButton);
        buttonPanel.add(itensButton);
        buttonPanel.add(emprestimosButton);
        buttonPanel.add(historicoButton);
        buttonPanel.add(anexosButton);

        // Add panel to the dashboard window
        add(buttonPanel, BorderLayout.CENTER);

        // Add action listeners to buttons
        usuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Usuarios window (replace with your logic)
                JOptionPane.showMessageDialog(DashboardWindow.this, "Accessing Usuarios");
            }
        });

        itensButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Itens window (replace with your logic)
                JOptionPane.showMessageDialog(DashboardWindow.this, "Accessing Itens");
            }
        });

        emprestimosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Emprestimos window (replace with your logic)
                JOptionPane.showMessageDialog(DashboardWindow.this, "Accessing Emprestimos");
            }
        });

        historicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Histórico window (replace with your logic)
                JOptionPane.showMessageDialog(DashboardWindow.this, "Accessing Histórico");
            }
        });

        anexosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Anexos window (replace with your logic)
                JOptionPane.showMessageDialog(DashboardWindow.this, "Accessing Anexos");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DashboardWindow().setVisible(true);
            }
        });
    }
}
