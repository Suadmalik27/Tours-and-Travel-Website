package com.mycompany.tours_and_travel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ContactUsPanel extends JPanel {
    private JTextField nameField;
    private JTextField emailField;
    private JTextArea messageArea;
    private JButton submitButton;
    private JButton backButton;
    private ActionListener navigationListener;

    public ContactUsPanel(ActionListener navigationListener) {
        this.navigationListener = navigationListener;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        backButton = new JButton("Back");
        backButton.addActionListener(navigationListener);
        backButton.setActionCommand("Home");
        add(backButton, gbc);

        gbc.gridx = 1;
        add(new JLabel("Name:"), gbc);

        gbc.gridx = 2;
        nameField = new JTextField(20);
        add(nameField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(new JLabel("Email:"), gbc);

        gbc.gridx = 2;
        emailField = new JTextField(20);
        add(emailField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        add(new JLabel("Message:"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        messageArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        add(scrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        submitButton = new JButton("Submit");
        add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String message = messageArea.getText();

            if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            // For now, just show a confirmation dialog
            JOptionPane.showMessageDialog(this, "Thank you for contacting us, " + name + "!");
            nameField.setText("");
            emailField.setText("");
            messageArea.setText("");
        });
    }
}
