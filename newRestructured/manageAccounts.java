package newRestructured;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            JPanel signUpScreen = buildSignUpScreen(cards);

            // Add screens to the deck with names
            cards.add(menuScreen, "CARD_MENU");
            cards.add(loginScreen, "CARD_LOGIN");
            cards.add(signUpScreen, "CARD_SIGNUP");

            // Put the deck in the frame
            frame.setContentPane(cards);
            frame.setVisible(true);
        });
    }

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
        signUp.addActionListener(e -> showCard(cards, "CARD_SIGNUP"));

        return panel;
    }

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
            String[] hash = null;
            try {
                hash = hashPassword(pass.getText());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("Login submitted for user: " + user.getText() + " with hashed password: " + hash[1]);

        });

        // send username to server--> either get salt back, or display user doesn't
        // exist
        // hash the salt with the password, and send hashed password along with username
        // to server
        // if server sends api key, go to main screen else say password is wrong

        // Make labels visible on black background
        for (Component c : panel.getComponents()) {
            if (c instanceof JLabel)
                ((JLabel) c).setForeground(Color.WHITE);
        }

        return panel;
    }

    private static JPanel buildSignUpScreen(JPanel cards) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

        JLabel label = new JLabel("Sign Up Screen");
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
            System.out.println("Signup submitted for user: " + user.getText());

            try {
                Path accountFile = resolveAccountDataPath();
                String[] hash = hashPassword(new String(pass.getPassword()));
                String salt = hash[0];
                String passwordHash = hash[1];
                String content = new String(Files.readAllBytes(accountFile), StandardCharsets.UTF_8);

                String username = user.getText();
                String createdAt = java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC).toString();
                String newAccount = String.format(
                        "    {\n" +
                                "      \"id\": %d,\n" +
                                "      \"username\": \"%s\",\n" +
                                "      \"password_hash\": \"%s\",\n" +
                                "      \"salt\": \"%s\",\n" +
                                "      \"created_at\": \"%s\",\n" +
                                "      \"is_admin\": false\n" +
                                "    },",
                        generateNextId(content), // we'll define this
                        username,
                        passwordHash,
                        salt,
                        createdAt);
                int insertIndex = content.lastIndexOf("]");
                boolean hasExisting = content.contains("{\"id\":");

                String newContent;
                if (hasExisting)
                    newContent = content.substring(0, insertIndex) + ",\n" + newAccount + "\n"
                            + content.substring(insertIndex);
                else
                    newContent = content.substring(0, insertIndex) + "\n" + newAccount + "\n"
                            + content.substring(insertIndex);
                System.out.println("Working dir: " + System.getProperty("user.dir"));
                System.out.println("Using account data file: " + accountFile.toAbsolutePath());
                Files.write(accountFile, newContent.getBytes(StandardCharsets.UTF_8));
                System.out.println("Account saved successfully!");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Make labels visible on black background
        for (Component c : panel.getComponents()) {
            if (c instanceof JLabel)
                ((JLabel) c).setForeground(Color.WHITE);
        }

        return panel;
    }

    private static int generateNextId(String content) {
        int lastId = 0;
        Pattern p = Pattern.compile("\"id\"\\s*:\\s*(\\d+)");
        Matcher m = p.matcher(content);
        while (m.find()) {
            lastId = Integer.parseInt(m.group(1));
        }
        return lastId + 1;
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

    private static Path resolveAccountDataPath() throws IOException {
        Path userDir = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
        Path[] candidates = new Path[] {
                userDir.resolve("accountData.txt"),
                userDir.resolve("newRestructured").resolve("accountData.txt"),
                userDir.resolve("pro").resolve("newRestructured").resolve("accountData.txt")
        };

        for (Path candidate : candidates) {
            if (Files.exists(candidate)) {
                return candidate;
            }
        }

        Path fallback = candidates[0];
        if (fallback.getParent() != null) {
            Files.createDirectories(fallback.getParent());
        }
        if (!Files.exists(fallback)) {
            Files.write(fallback, "{\n  \"accounts\": []\n}\n".getBytes(StandardCharsets.UTF_8));
        }
        return fallback;
    }

    private static String[] hashPassword(String pass) throws Exception {

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);

        byte[] hashedPassword = md.digest(pass.getBytes(StandardCharsets.UTF_8));

        System.out.println("Salt: " + Base64.getEncoder().encodeToString(salt));
        System.out.println("Hash: " + Base64.getEncoder().encodeToString(hashedPassword));

        String[] result = { Base64.getEncoder().encodeToString(salt),
                Base64.getEncoder().encodeToString(hashedPassword) };

        return result;
    }

    public static String hashPasswordWithSalt(String pass, String base64Salt) throws Exception {
        // Decode the provided salt from Base64
        byte[] salt = Base64.getDecoder().decode(base64Salt);

        // Create a MessageDigest instance for SHA-512
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        // Update the MessageDigest with the salt (prepend it to the password)
        md.update(salt);

        // Hash the password bytes
        byte[] hashedPassword = md.digest(pass.getBytes(StandardCharsets.UTF_8));

        // Return the hashed password as Base64 encoded string
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

}
