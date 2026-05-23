import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LoginPage extends Frame implements ActionListener {

    Label title, emailLabel, userLabel, passLabel, msgLabel;
    TextField emailField, userField, passField;
    Button loginBtn, signupBtn;

    // Store users
    static ArrayList<String> emails = new ArrayList<>();
    static ArrayList<String> usernames = new ArrayList<>();
    static ArrayList<String> passwords = new ArrayList<>();

    public LoginPage() {
        setTitle("Login / Signup");
        setSize(400, 300);
        setLayout(new GridLayout(6, 2));

        title = new Label("User Login", Label.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        emailLabel = new Label("Email:");
        userLabel = new Label("Username:");
        passLabel = new Label("Password:");

        emailField = new TextField();
        userField = new TextField();
        passField = new TextField();
        passField.setEchoChar('*');

        loginBtn = new Button("Login");
        signupBtn = new Button("Create Account");

        msgLabel = new Label("", Label.CENTER);

        loginBtn.addActionListener(this);
        signupBtn.addActionListener(this);

        add(title);
        add(new Label(""));

        add(emailLabel);
        add(emailField);

        add(userLabel);
        add(userField);

        add(passLabel);
        add(passField);

        add(loginBtn);
        add(signupBtn);

        add(msgLabel);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String email = emailField.getText();
        String username = userField.getText();
        String password = passField.getText();

        if (e.getSource() == loginBtn) {
            boolean found = false;

            for (int i = 0; i < emails.size(); i++) {
                if (emails.get(i).equals(email) &&
                        usernames.get(i).equals(username) &&
                        passwords.get(i).equals(password)) {

                    found = true;
                    break;
                }
            }

            if (found) {
                msgLabel.setText("Login Successful!");
            } else {
                msgLabel.setText("Invalid Credentials");
            }
        }

        if (e.getSource() == signupBtn) {
            new SignupPage();
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}

class SignupPage extends Frame implements ActionListener {

    Label title, emailLabel, userLabel, passLabel, msgLabel;
    TextField emailField, userField, passField;
    Button createBtn;

    public SignupPage() {
        setTitle("Create Account");
        setSize(400, 250);
        setLayout(new GridLayout(5, 2));

        title = new Label("Signup", Label.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        emailLabel = new Label("Email:");
        userLabel = new Label("Username:");
        passLabel = new Label("Password:");

        emailField = new TextField();
        userField = new TextField();
        passField = new TextField();
        passField.setEchoChar('*');

        createBtn = new Button("Create Account");
        msgLabel = new Label("", Label.CENTER);

        createBtn.addActionListener(this);

        add(title);
        add(new Label(""));

        add(emailLabel);
        add(emailField);

        add(userLabel);
        add(userField);

        add(passLabel);
        add(passField);

        add(createBtn);
        add(msgLabel);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String email = emailField.getText();
        String username = userField.getText();
        String password = passField.getText();

        if (!email.equals("") && !username.equals("") && !password.equals("")) {
            LoginPage.emails.add(email);
            LoginPage.usernames.add(username);
            LoginPage.passwords.add(password);

            msgLabel.setText("Account Created Successfully!");
        } else {
            msgLabel.setText("Please fill all fields");
        }
    }
}