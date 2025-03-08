

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Car Rental System - Login");
        setSize(600, 400); // Larger window for better visuals
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Use a modern font with larger size
        Font customFont = new Font("Segoe UI", Font.PLAIN, 18);

        // Create a custom panel with a solid background
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add a logo or header image
        ImageIcon logoIcon = new ImageIcon("logo.png"); // Replace with your logo path
        JLabel logoLabel = new JLabel(logoIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        backgroundPanel.add(logoLabel, gbc);

        // Title label with blue color
        JLabel titleLabel = new JLabel("Car Rental System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 102, 204)); // Blue color
        gbc.gridy = 1;
        backgroundPanel.add(titleLabel, gbc);

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(customFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        backgroundPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(customFont);
        gbc.gridx = 1;
        backgroundPanel.add(usernameField, gbc);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(customFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        backgroundPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(customFont);
        gbc.gridx = 1;
        backgroundPanel.add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(customFont);
        loginButton.setBackground(new Color(0, 102, 204)); // Blue background
        loginButton.setForeground(Color.WHITE); // White text
        loginButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        backgroundPanel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if ("admin".equals(username) && "admin123".equals(password)) {
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Invalid username or password", "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(backgroundPanel);
    }
}