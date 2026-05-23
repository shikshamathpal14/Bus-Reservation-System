import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SearchBookFrame extends Frame implements ActionListener {

    int userId;
    String username;

    Button tabSearch, tabSchedule;
    Panel searchPanel, schedulePanel;

    TextField sourceField, destField;
    Button searchBtn, bookBtn, backBtn1;
    TextArea searchResults;
    Label searchMsg;
    int lastScheduleId = -1;

    TextArea scheduleArea;
    Button refreshBtn, backBtn2;

    public SearchBookFrame(int userId, String username) {
        this.userId = userId;
        this.username = username;

        setTitle("Bus Reservation - Search & Schedules");
        setExtendedState(Frame.MAXIMIZED_BOTH);       // ✅ Maximize
        setLayout(null);
        setBackground(new Color(255, 182, 193));       // ✅ Light Pink
        setLocationRelativeTo(null);
        setResizable(true);

        Label titleLabel = new Label("BUS RESERVATION", Label.CENTER);
        titleLabel.setBounds(0, 50, 1366, 50);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 35));
        titleLabel.setForeground(new Color(139, 0, 79)); // ✅ Dark Pink
        add(titleLabel);

        // Tab buttons
        tabSearch = new Button("Search & Book");
        tabSearch.setBounds(530, 110, 150, 30);
        tabSearch.setBackground(Color.WHITE);            // ✅ Keep White
        tabSearch.setForeground(new Color(255, 20, 147)); // ✅ Hot Pink text
        tabSearch.setFont(new Font("Arial", Font.BOLD, 13));
        tabSearch.addActionListener(this);
        add(tabSearch);

        tabSchedule = new Button("All Schedules");
        tabSchedule.setBounds(700, 110, 150, 30);
        tabSchedule.setBackground(new Color(255, 20, 147)); // ✅ Hot Pink
        tabSchedule.setForeground(Color.WHITE);
        tabSchedule.setFont(new Font("Arial", Font.BOLD, 13));
        tabSchedule.addActionListener(this);
        add(tabSchedule);

        buildSearchPanel();
        buildSchedulePanel();
        showSearchTab();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                new DashboardFrame(userId, username);
            }
        });
        setVisible(true);
    }

    void buildSearchPanel() {
        searchPanel = new Panel(null);
        searchPanel.setBounds(360, 160, 640, 430);
        searchPanel.setBackground(Color.WHITE);          // ✅ Keep White

        Label fromLbl = new Label("From:");
        fromLbl.setBounds(15, 18, 45, 20);
        fromLbl.setFont(new Font("Arial", Font.BOLD, 13));
        fromLbl.setForeground(new Color(255, 20, 147)); // ✅ Hot Pink
        searchPanel.add(fromLbl);

        sourceField = new TextField();
        sourceField.setBounds(63, 16, 130, 26);
        sourceField.setFont(new Font("Arial", Font.PLAIN, 13));
        searchPanel.add(sourceField);

        Label toLbl = new Label("To:");
        toLbl.setBounds(210, 18, 30, 20);
        toLbl.setFont(new Font("Arial", Font.BOLD, 13));
        toLbl.setForeground(new Color(255, 20, 147));   // ✅ Hot Pink
        searchPanel.add(toLbl);

        destField = new TextField();
        destField.setBounds(243, 16, 130, 26);
        destField.setFont(new Font("Arial", Font.PLAIN, 13));
        searchPanel.add(destField);

        searchBtn = new Button("SEARCH");
        searchBtn.setBounds(390, 15, 85, 28);
        searchBtn.setBackground(new Color(255, 20, 147)); // ✅ Hot Pink
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFont(new Font("Arial", Font.BOLD, 12));
        searchBtn.addActionListener(this);
        searchPanel.add(searchBtn);

        searchMsg = new Label("Enter route above and click Search", Label.LEFT);
        searchMsg.setBounds(15, 52, 610, 18);
        searchMsg.setFont(new Font("Arial", Font.ITALIC, 12));
        searchMsg.setForeground(new Color(220, 20, 100)); // ✅ Deep Pink
        searchPanel.add(searchMsg);

        searchResults = new TextArea("", 10, 60, TextArea.SCROLLBARS_VERTICAL_ONLY);
        searchResults.setBounds(15, 76, 610, 240);
        searchResults.setFont(new Font("Monospaced", Font.PLAIN, 12));
        searchResults.setEditable(false);
        searchPanel.add(searchResults);

        Label sidLbl = new Label("Schedule ID:");
        sidLbl.setBounds(15, 330, 100, 22);
        sidLbl.setFont(new Font("Arial", Font.BOLD, 13));
        sidLbl.setForeground(new Color(255, 20, 147));  // ✅ Hot Pink
        searchPanel.add(sidLbl);

        TextField schedIdField = new TextField();
        schedIdField.setName("schedId");
        schedIdField.setBounds(118, 328, 80, 25);
        schedIdField.setFont(new Font("Arial", Font.PLAIN, 13));
        searchPanel.add(schedIdField);

        Label seatLbl = new Label("Seat No:");
        seatLbl.setBounds(215, 330, 70, 22);
        seatLbl.setFont(new Font("Arial", Font.BOLD, 13));
        seatLbl.setForeground(new Color(255, 20, 147)); // ✅ Hot Pink
        searchPanel.add(seatLbl);

        TextField seatField = new TextField();
        seatField.setName("seat");
        seatField.setBounds(288, 328, 70, 25);
        seatField.setFont(new Font("Arial", Font.PLAIN, 13));
        searchPanel.add(seatField);

        bookBtn = new Button("BOOK NOW");
        bookBtn.setBounds(378, 326, 110, 29);
        bookBtn.setBackground(new Color(255, 20, 147)); // ✅ Hot Pink
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setFont(new Font("Arial", Font.BOLD, 13));
        bookBtn.addActionListener(this);
        searchPanel.add(bookBtn);

        backBtn1 = new Button("DASHBOARD");
        backBtn1.setBounds(505, 326, 110, 29);
        backBtn1.setBackground(new Color(220, 20, 100)); // ✅ Deep Pink
        backBtn1.setForeground(Color.WHITE);
        backBtn1.setFont(new Font("Arial", Font.BOLD, 12));
        backBtn1.addActionListener(this);
        searchPanel.add(backBtn1);

        schedIdField.addActionListener(this);
        seatField.addActionListener(this);

        add(searchPanel);
    }

    void buildSchedulePanel() {
        schedulePanel = new Panel(null);
        schedulePanel.setBounds(360, 160, 640, 430);
        schedulePanel.setBackground(Color.WHITE);        // ✅ Keep White

        scheduleArea = new TextArea("", 15, 80, TextArea.SCROLLBARS_BOTH);
        scheduleArea.setBounds(15, 15, 610, 360);
        scheduleArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        scheduleArea.setEditable(false);
        schedulePanel.add(scheduleArea);

        refreshBtn = new Button("REFRESH");
        refreshBtn.setBounds(180, 390, 120, 30);
        refreshBtn.setBackground(new Color(255, 20, 147)); // ✅ Hot Pink
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFont(new Font("Arial", Font.BOLD, 13));
        refreshBtn.addActionListener(this);
        schedulePanel.add(refreshBtn);

        backBtn2 = new Button("DASHBOARD");
        backBtn2.setBounds(340, 390, 120, 30);
        backBtn2.setBackground(new Color(220, 20, 100)); // ✅ Deep Pink
        backBtn2.setForeground(Color.WHITE);
        backBtn2.setFont(new Font("Arial", Font.BOLD, 13));
        backBtn2.addActionListener(this);
        schedulePanel.add(backBtn2);

        add(schedulePanel);
    }

    void showSearchTab() {
        searchPanel.setVisible(true);
        schedulePanel.setVisible(false);
        tabSearch.setBackground(Color.WHITE);              // ✅ White when active
        tabSearch.setForeground(new Color(255, 20, 147));  // ✅ Hot Pink text
        tabSchedule.setBackground(new Color(255, 20, 147));// ✅ Hot Pink
        tabSchedule.setForeground(Color.WHITE);
    }

    void showScheduleTab() {
        searchPanel.setVisible(false);
        schedulePanel.setVisible(true);
        tabSchedule.setBackground(Color.WHITE);            // ✅ White when active
        tabSchedule.setForeground(new Color(255, 20, 147));// ✅ Hot Pink text
        tabSearch.setBackground(new Color(255, 20, 147));  // ✅ Hot Pink
        tabSearch.setForeground(Color.WHITE);
        loadAllSchedules();
    }

    // ✅ Keep all your existing methods unchanged below
    void searchBuses() {
        String src = sourceField.getText().trim();
        String dst = destField.getText().trim();
        if (src.isEmpty() || dst.isEmpty()) {
            searchMsg.setForeground(Color.RED);
            searchMsg.setText("Enter both source and destination!");
            return;
        }
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT s.schedule_id, b.bus_name, b.source, b.destination, " +
                            "s.journey_date, s.departure_time, s.arrival_time, s.available_seats, b.price " +
                            "FROM buses b JOIN schedules s ON b.bus_id=s.bus_id " +
                            "WHERE b.source LIKE ? AND b.destination LIKE ? AND s.available_seats>0");
            ps.setString(1, "%" + src + "%");
            ps.setString(2, "%" + dst + "%");
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-6s %-18s %-12s %-10s %-10s %-8s %-8s%n",
                    "Sch.ID", "Bus Name", "Date", "Depart", "Arrive", "Seats", "Price"));
            sb.append("-".repeat(75)).append("\n");

            int count = 0;
            while (rs.next()) {
                count++;
                sb.append(String.format("%-6d %-18s %-12s %-10s %-10s %-8d Rs%-6d%n",
                        rs.getInt("schedule_id"), rs.getString("bus_name"),
                        rs.getString("journey_date"), rs.getString("departure_time"),
                        rs.getString("arrival_time"), rs.getInt("available_seats"),
                        rs.getInt("price")));
            }
            con.close();

            if (count == 0) {
                searchResults.setText("No buses found for this route.");
                searchMsg.setForeground(Color.RED);
                searchMsg.setText("No buses available.");
            } else {
                searchResults.setText(sb.toString());
                searchMsg.setForeground(new Color(0, 120, 0));
                searchMsg.setText(count + " bus(es) found. Enter Schedule ID & Seat to book.");
            }
        } catch (Exception ex) {
            searchResults.setText("Error: " + ex.getMessage());
        }
    }

    void doBooking() {
        String schedIdStr = "", seatStr = "";
        for (Component c : searchPanel.getComponents()) {
            if (c instanceof TextField) {
                TextField tf = (TextField) c;
                if ("schedId".equals(tf.getName())) schedIdStr = tf.getText().trim();
                if ("seat".equals(tf.getName())) seatStr = tf.getText().trim();
            }
        }

        if (schedIdStr.isEmpty() || seatStr.isEmpty()) {
            searchMsg.setForeground(Color.RED);
            searchMsg.setText("Enter Schedule ID and Seat Number to book!");
            return;
        }
        try {
            int scheduleId = Integer.parseInt(schedIdStr);
            int seatNumber = Integer.parseInt(seatStr);
            Connection con = DBConnection.getConnection();

            PreparedStatement chk = con.prepareStatement(
                    "SELECT * FROM bookings WHERE schedule_id=? AND seat_number=?");
            chk.setInt(1, scheduleId); chk.setInt(2, seatNumber);
            if (chk.executeQuery().next()) {
                searchMsg.setForeground(Color.RED);
                searchMsg.setText("Seat " + seatNumber + " already booked! Choose another.");
                con.close(); return;
            }

            PreparedStatement gp = con.prepareStatement(
                    "SELECT b.price FROM buses b JOIN schedules s ON b.bus_id=s.bus_id WHERE s.schedule_id=?");
            gp.setInt(1, scheduleId);
            ResultSet pr = gp.executeQuery();
            int price = pr.next() ? pr.getInt("price") : 0;

            PreparedStatement ins = con.prepareStatement(
                    "INSERT INTO bookings (user_id,schedule_id,seat_number,booking_date,payment_status,total_amount) " +
                            "VALUES (?,?,?,CURDATE(),'Paid',?)");
            ins.setInt(1, userId); ins.setInt(2, scheduleId);
            ins.setInt(3, seatNumber); ins.setInt(4, price);
            ins.executeUpdate();

            PreparedStatement upd = con.prepareStatement(
                    "UPDATE schedules SET available_seats=available_seats-1 WHERE schedule_id=?");
            upd.setInt(1, scheduleId);
            upd.executeUpdate();
            con.close();

            searchMsg.setForeground(new Color(0, 130, 0));
            searchMsg.setText("Booked! Seat " + seatNumber + " confirmed. Amount: Rs" + price);
            searchBuses();

        } catch (NumberFormatException ex) {
            searchMsg.setForeground(Color.RED);
            searchMsg.setText("Schedule ID and Seat must be numbers!");
        } catch (Exception ex) {
            searchMsg.setForeground(Color.RED);
            searchMsg.setText("Error: " + ex.getMessage());
        }
    }

    void loadAllSchedules() {
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery(
                    "SELECT s.schedule_id, b.bus_name, b.source, b.destination, " +
                            "s.journey_date, s.departure_time, s.arrival_time, s.available_seats, b.price " +
                            "FROM buses b JOIN schedules s ON b.bus_id=s.bus_id ORDER BY s.journey_date");

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-5s %-18s %-10s %-10s %-12s %-9s %-9s %-7s %-8s%n",
                    "ID", "Bus Name", "From", "To", "Date", "Depart", "Arrive", "Seats", "Price"));
            sb.append("-".repeat(95)).append("\n");
            int count = 0;
            while (rs.next()) {
                count++;
                sb.append(String.format("%-5d %-18s %-10s %-10s %-12s %-9s %-9s %-7d Rs%-6d%n",
                        rs.getInt("schedule_id"), rs.getString("bus_name"),
                        rs.getString("source"), rs.getString("destination"),
                        rs.getString("journey_date"), rs.getString("departure_time"),
                        rs.getString("arrival_time"), rs.getInt("available_seats"),
                        rs.getInt("price")));
            }
            scheduleArea.setText(count == 0 ? "No schedules found." : sb.toString());
            con.close();
        } catch (Exception ex) {
            scheduleArea.setText("Error: " + ex.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tabSearch) showSearchTab();
        else if (e.getSource() == tabSchedule) showScheduleTab();
        else if (e.getSource() == searchBtn) searchBuses();
        else if (e.getSource() == bookBtn) doBooking();
        else if (e.getSource() == refreshBtn) loadAllSchedules();
        else if (e.getSource() == backBtn1 || e.getSource() == backBtn2) {
            dispose();
            new DashboardFrame(userId, username);
        }
    }
}