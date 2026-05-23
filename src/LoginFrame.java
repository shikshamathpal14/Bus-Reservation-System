import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginFrame extends Frame implements ActionListener {


    Image bgImage;
    Panel loginPanel, registerPanel;
    Label titleLabel;

    // Login fields
    TextField loginUser, loginPass;
    Button loginBtn, goRegisterBtn;
    Label loginMsg;

    // Register fields
    TextField regUser, regPass, regConfirm;
    Button registerBtn, goLoginBtn;
    Label registerMsg;

    public LoginFrame() {
        setTitle("Bus Reservation System");
        setExtendedState(Frame.MAXIMIZED_BOTH);  // ✅ ADD THIS - Maximize
        setLayout(null);
        bgImage = Toolkit.getDefaultToolkit()
                .getImage("C:/Users/guest1/IdeaProjects/bus reservation/src/images/download (1).jpeg");

        MediaTracker mt = new MediaTracker(this);
        mt.addImage(bgImage, 0);
        try {
            mt.waitForAll();
        } catch (Exception ex) {
        }
        setBackground(new Color(255, 182, 193)); // ✅ CHANGE THIS - Pink
        setLocationRelativeTo(null);
        setResizable(true);                      // ✅ CHANGE false to true

        titleLabel = new Label("BUS RESERVATION SYSTEM", Label.CENTER);
        titleLabel.setBounds(0, 60, 1366, 50);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 35));
        titleLabel.setForeground(new Color(139, 0, 79));
        add(titleLabel);

        buildLoginPanel();
        buildRegisterPanel();

        showLogin();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
    }

    void buildLoginPanel() {
        loginPanel = new Panel(null);
        loginPanel.setBounds(60, 65, 330, 255);
        loginPanel.setBackground(new Color(255, 220, 230));

        Label title = new Label("LOGIN", Label.CENTER);
        title.setBounds(0, 15, 330, 25);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(new Color(30, 60, 114));
        loginPanel.add(title);

        Label ul = new Label("Username:");
        ul.setBounds(30, 58, 85, 20);
        ul.setFont(new Font("Arial", Font.PLAIN, 13));
        loginPanel.add(ul);

        loginUser = new TextField();
        loginUser.setBounds(120, 56, 180, 25);
        loginUser.setFont(new Font("Arial", Font.PLAIN, 13));
        loginPanel.add(loginUser);

        Label pl = new Label("Password:");
        pl.setBounds(30, 95, 85, 20);
        pl.setFont(new Font("Arial", Font.PLAIN, 13));
        loginPanel.add(pl);

        loginPass = new TextField();
        loginPass.setBounds(120, 93, 180, 25);
        loginPass.setEchoChar('*');
        loginPass.setFont(new Font("Arial", Font.PLAIN, 13));
        loginPanel.add(loginPass);

        loginBtn = new Button("LOGIN");
        loginBtn.setBounds(45, 135, 105, 32);
        loginBtn.setBackground(new Color(255, 20, 147));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 13));
        loginBtn.addActionListener(this);
        loginPanel.add(loginBtn);

        goRegisterBtn = new Button("REGISTER");
        goRegisterBtn.setBounds(170, 135, 115, 32);
        goRegisterBtn.setBackground(new Color(220, 20, 100));
        goRegisterBtn.setForeground(Color.WHITE);
        goRegisterBtn.setFont(new Font("Arial", Font.BOLD, 13));
        goRegisterBtn.addActionListener(this);
        loginPanel.add(goRegisterBtn);

        loginMsg = new Label("", Label.CENTER);
        loginMsg.setBounds(0, 185, 330, 20);
        loginMsg.setFont(new Font("Arial", Font.PLAIN, 12));
        loginMsg.setForeground(Color.RED);
        loginPanel.add(loginMsg);

        add(loginPanel);
    }

    void buildRegisterPanel() {
        registerPanel = new Panel(null);
        registerPanel.setBounds(60, 65, 330, 275);
        registerPanel.setBackground(Color.WHITE);

        Label title = new Label("CREATE ACCOUNT", Label.CENTER);
        title.setBounds(0, 15, 330, 25);
        title.setFont(new Font("Arial", Font.BOLD, 15));
        titleLabel.setForeground(new Color(255, 220, 230));
        registerPanel.add(title);

        Label ul = new Label("Username:");
        ul.setBounds(30, 55, 90, 20);
        ul.setFont(new Font("Arial", Font.PLAIN, 13));
        registerPanel.add(ul);

        regUser = new TextField();
        regUser.setBounds(125, 53, 170, 25);
        regUser.setFont(new Font("Arial", Font.PLAIN, 13));
        registerPanel.add(regUser);

        Label pl = new Label("Password:");
        pl.setBounds(30, 90, 90, 20);
        pl.setFont(new Font("Arial", Font.PLAIN, 13));
        registerPanel.add(pl);

        regPass = new TextField();
        regPass.setBounds(125, 88, 170, 25);
        regPass.setEchoChar('*');
        regPass.setFont(new Font("Arial", Font.PLAIN, 13));
        registerPanel.add(regPass);

        Label cl = new Label("Confirm:");
        cl.setBounds(30, 125, 90, 20);
        cl.setFont(new Font("Arial", Font.PLAIN, 13));
        registerPanel.add(cl);

        regConfirm = new TextField();
        regConfirm.setBounds(125, 123, 170, 25);
        regConfirm.setEchoChar('*');
        regConfirm.setFont(new Font("Arial", Font.PLAIN, 13));
        registerPanel.add(regConfirm);

        registerBtn = new Button("REGISTER");
        registerBtn.setBounds(45, 165, 110, 32);
        registerBtn.setBackground(new Color(0, 150, 136));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 13));
        registerBtn.addActionListener(this);
        registerPanel.add(registerBtn);

        goLoginBtn = new Button("BACK TO LOGIN");
        goLoginBtn.setBounds(170, 165, 125, 32);
        goLoginBtn.setBackground(new Color(120, 120, 120));
        goLoginBtn.setForeground(Color.WHITE);
        goLoginBtn.setFont(new Font("Arial", Font.BOLD, 13));
        goLoginBtn.addActionListener(this);
        registerPanel.add(goLoginBtn);

        registerMsg = new Label("", Label.CENTER);
        registerMsg.setBounds(0, 213, 330, 20);
        registerMsg.setFont(new Font("Arial", Font.PLAIN, 12));
        registerMsg.setForeground(Color.RED);
        registerPanel.add(registerMsg);

        add(registerPanel);
    }

    void showLogin() {
        loginPanel.setVisible(true);
        registerPanel.setVisible(false);
        setExtendedState(Frame.MAXIMIZED_BOTH); // ✅ Add this
    }

    void showRegister() {
        loginPanel.setVisible(false);
        registerPanel.setVisible(true);
        setExtendedState(Frame.MAXIMIZED_BOTH); // ✅ Add this
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            doLogin();
        } else if (e.getSource() == goRegisterBtn) {
            loginMsg.setText("");
            showRegister();
        } else if (e.getSource() == registerBtn) {
            doRegister();
        } else if (e.getSource() == goLoginBtn) {
            registerMsg.setText("");
            showLogin();
        }
    }

    void doLogin() {
        String user = loginUser.getText().trim();
        String pass = loginPass.getText().trim();
        if (user.isEmpty() || pass.isEmpty()) {
            loginMsg.setText("Please enter username and password!");
            return;
        }
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int uid = rs.getInt("id");
                String uname = rs.getString("username");
                con.close();
                dispose();
                new DashboardFrame(uid, uname);
            } else {
                loginMsg.setText("Invalid username or password!");
                con.close();
            }
        } catch (Exception ex) {
            loginMsg.setText("DB Error: " + ex.getMessage());
        }
    }

    void doRegister() {
        String user = regUser.getText().trim();
        String pass = regPass.getText().trim();
        String confirm = regConfirm.getText().trim();
        if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            registerMsg.setForeground(Color.RED);
            registerMsg.setText("All fields are required!");
            return;
        }
        if (!pass.equals(confirm)) {
            registerMsg.setForeground(Color.RED);
            registerMsg.setText("Passwords do not match!");
            return;
        }
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement check = con.prepareStatement(
                    "SELECT * FROM users WHERE username=?");
            check.setString(1, user);
            if (check.executeQuery().next()) {
                registerMsg.setForeground(Color.RED);
                registerMsg.setText("Username already exists!");
                con.close();
                return;
            }
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO users (username, password) VALUES (?, ?)");
            ps.setString(1, user);
            ps.setString(2, pass);
            ps.executeUpdate();
            con.close();
            registerMsg.setForeground(new Color(0, 140, 0));
            registerMsg.setText("Registered! Please login.");
            regUser.setText("");
            regPass.setText("");
            regConfirm.setText("");
        } catch (Exception ex) {
            registerMsg.setForeground(Color.RED);
            registerMsg.setText("Error: " + ex.getMessage());
        }
    }

    public void paint(Graphics g) {
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        super.paint(g);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
