package com.mycompany.tours_and_travel;

import java.awt.CardLayout;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AuthDialog extends JDialog {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private LoginPanel loginPanel;
    private SignupPanel signupPanel;

    private LoginSuccessListener loginSuccessListener;

    public AuthDialog(JFrame parent, ActionListener actionListener) {
        super(parent, "Login / Signup", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if ("LoginSubmit".equals(e.getActionCommand())) {
                    // Perform login logic here or delegate
                    String username = loginPanel.getUsername();
                    String password = loginPanel.getPassword();
                    // Notify listener on login success
                    if (loginSuccessListener != null) {
                        loginSuccessListener.onLoginSuccess(username, password);
                    }
                }
            }
        });
        signupPanel = new SignupPanel(actionListener);

        cardPanel.add(loginPanel, "Login");
        cardPanel.add(signupPanel, "Signup");

        add(cardPanel);

        // Show login panel by default
        cardLayout.show(cardPanel, "Login");
    }

    public void setLoginSuccessListener(LoginSuccessListener listener) {
        this.loginSuccessListener = listener;
    }

    public void showLogin() {
        cardLayout.show(cardPanel, "Login");
        setVisible(true);
    }

    public void showSignup() {
        cardLayout.show(cardPanel, "Signup");
        setVisible(true);
    }

    public interface LoginSuccessListener {
        void onLoginSuccess(String username, String password);
    }
}
