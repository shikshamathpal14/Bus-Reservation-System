import java.awt.*;
import java.awt.event.*;

public class DashboardFrame extends Frame implements ActionListener {

    int userId;
    String username;
    Button searchBusBtn, myBookingsBtn, logoutBtn;

    public DashboardFrame(int userId, String username) {
        this.userId = userId;
        this.username = username;

        setTitle("Bus Reservation - Dashboard");
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setLayout(null);
        setBackground(new Color(255, 182, 193));
        setLocationRelativeTo(null);
        setResizable(false);

        Label titleLabel = new Label("BUS RESERVATION SYSTEM", Label.CENTER);
        titleLabel.setBounds(0, 50, 1366, 50);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 35));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel);

        Label welcomeLabel = new Label("Welcome, " + username + "!", Label.CENTER);
        welcomeLabel.setBounds(0, 120, 1366, 40);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 25));
        welcomeLabel.setForeground(new Color(139, 0, 79));
        add(welcomeLabel);

        Panel menuPanel = new Panel(null);
        menuPanel.setBounds(580, 250, 300, 225);
        menuPanel.setBackground(Color.WHITE);
        add(menuPanel);

        Label menuTitle = new Label("MAIN MENU", Label.CENTER);
        menuTitle.setBounds(0, 15, 300, 25);
        menuTitle.setFont(new Font("Arial", Font.BOLD, 15));
        menuTitle.setForeground(new Color(255, 20, 147));
        menuPanel.add(menuTitle);

        searchBusBtn = new Button("Search & Book Bus");
        searchBusBtn.setBounds(40, 58, 220, 40);
        searchBusBtn.setBackground(new Color(255, 20, 147));
        searchBusBtn.setForeground(Color.WHITE);
        searchBusBtn.setFont(new Font("Arial", Font.BOLD, 13));
        searchBusBtn.addActionListener(this);
        menuPanel.add(searchBusBtn);

        myBookingsBtn = new Button("My Bookings");
        myBookingsBtn.setBounds(40, 113, 220, 40);
        myBookingsBtn.setBackground(new Color(220, 20, 100));
        myBookingsBtn.setForeground(Color.WHITE);
        myBookingsBtn.setFont(new Font("Arial", Font.BOLD, 13));
        myBookingsBtn.addActionListener(this);
        menuPanel.add(myBookingsBtn);

        logoutBtn = new Button("Logout");
        logoutBtn.setBounds(40, 168, 220, 40);
        logoutBtn.setBackground(new Color(200, 50, 50));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 13));
        logoutBtn.addActionListener(this);
        menuPanel.add(logoutBtn);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBusBtn) {
            dispose();
            new SearchBookFrame(userId, username);
        } else if (e.getSource() == myBookingsBtn) {
            dispose();
            new MyBookingsFrame(userId, username);
        } else if (e.getSource() == logoutBtn) {
            dispose();
            new LoginFrame();
        }
    }
}
