package newRestructured;

import javax.swing.*;
import java.awt.*;

public class manageAccounts {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("BlackJack");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 800);
            frame.setLocationRelativeTo(null);

            // The "deck" that holds all screens
            JPanel cards = new JPanel(new CardLayout());
            cards.setBackground(Color.BLACK); // fallback

            // Build screens
            JPanel menuScreen = buildMenuScreen(cards);
            JPanel loginScreen = buildLoginScreen(cards);

            // Add screens to the deck with names
            cards.add(menuScreen, "CARD_MENU");
            cards.add(loginScreen, "CARD_LOGIN");

            // Put the deck in the frame
            frame.setContentPane(cards);
            frame.setVisible(true);
        });
    }

    /** MENU SCREEN: your original buttons + title */
    private static JPanel buildMenuScreen(JPanel cards) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Welcome to BlackJack!");
        title.setFont(title.getFont().deriveFont(30f));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JButton login = new JButton("Login");
        JButton signUp = new JButton("Signup");
        login.setFont(login.getFont().deriveFont(30f));
        signUp.setFont(signUp.getFont().deriveFont(30f));
        Dimension btnSize = new Dimension(250, 100);
        login.setPreferredSize(btnSize);
        signUp.setPreferredSize(btnSize);
        styleButton(login);
        styleButton(signUp);

        // Title row spanning 3 columns
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(title, gbc);

        // Buttons row
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(login, gbc);
        gbc.gridx = 2;
        panel.add(signUp, gbc);

        // Actions: show different cards
        login.addActionListener(e -> showCard(cards, "CARD_LOGIN"));
        signUp.addActionListener(e -> {
            // Add your signup card later; for now just show login to demo
            showCard(cards, "CARD_LOGIN");
        });

        return panel;
    }

    /** LOGIN SCREEN: simple demo with Back button */
    private static JPanel buildLoginScreen(JPanel cards) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

        JLabel label = new JLabel("Login Screen");
        label.setFont(label.getFont().deriveFont(28f));
        label.setForeground(Color.WHITE);

        JTextField user = new JTextField(16);
        JPasswordField pass = new JPasswordField(16);
        JButton submit = new JButton("Submit");
        JButton back = new JButton("Back");

        styleField(user);
        styleField(pass);
        styleButton(submit);
        styleButton(back);

        // Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(label, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        panel.add(user, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(pass, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(back, gbc);
        gbc.gridx = 1;
        panel.add(submit, gbc);

        // Actions
        back.addActionListener(e -> showCard(cards, "CARD_MENU"));
        submit.addActionListener(e -> {
            // Do your login logic here
            System.out.println("Login submitted for user: " + user.getText());
        });

        // Make labels visible on black background
        for (Component c : panel.getComponents()) {
            if (c instanceof JLabel)
                ((JLabel) c).setForeground(Color.WHITE);
        }

        return panel;
    }

    /** Switch to a named card */
    private static void showCard(JPanel cards, String name) {
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, name);
    }

    /** Reusable styling helpers */
    private static void styleButton(AbstractButton b) {
        b.setBackground(Color.DARK_GRAY);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(b.getFont().deriveFont(18f));
    }

    private static void styleField(JTextField f) {
        f.setBackground(new Color(40, 40, 40));
        f.setForeground(Color.WHITE);
        f.setCaretColor(Color.WHITE);
        f.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 90)));
        f.setFont(f.getFont().deriveFont(16f));
    }
}
