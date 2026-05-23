import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MyBookingsFrame extends Frame implements ActionListener {

    int userId;
    String username;
    TextArea bookingsArea;
    TextField cancelField;
    Button cancelBtn, refreshBtn, backBtn;
    Label msgLabel;

    public MyBookingsFrame(int userId, String username) {
        this.userId = userId;
        this.username = username;

        setTitle("Bus Reservation - My Bookings");
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setLayout(null);
        setBackground(new Color(255, 182, 193));
        setLocationRelativeTo(null);
        setResizable(false);

        Label titleLabel = new Label("MY BOOKINGS  -  " + username, Label.CENTER);
        titleLabel.setBounds(0, 18, 700, 28);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 17));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel);

        bookingsArea = new TextArea("", 12, 80, TextArea.SCROLLBARS_BOTH);
        bookingsArea.setBounds(25, 55, 650, 310);
        bookingsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        bookingsArea.setEditable(false);
        bookingsArea.setBackground(new Color(240, 248, 255));
        add(bookingsArea);

        Panel cancelPanel = new Panel(null);
        cancelPanel.setBounds(25, 375, 650, 60);
        cancelPanel.setBackground(Color.WHITE);
        add(cancelPanel);

        Label cancelLabel = new Label("Cancel Booking ID:");
        cancelLabel.setBounds(20, 20, 140, 20);
        cancelLabel.setFont(new Font("Arial", Font.BOLD, 13));
        cancelPanel.add(cancelLabel);

        cancelField = new TextField();
        cancelField.setBounds(165, 18, 90, 26);
        cancelField.setFont(new Font("Arial", Font.PLAIN, 13));
        cancelPanel.add(cancelField);

        cancelBtn = new Button("CANCEL");
        cancelBtn.setBounds(270, 17, 100, 29);
        cancelBtn.setBackground(new Color(200, 50, 50));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 13));
        cancelBtn.addActionListener(this);
        cancelPanel.add(cancelBtn);

        refreshBtn = new Button("REFRESH");
        refreshBtn.setBounds(385, 17, 100, 29);
        refreshBtn.setBackground(new Color(255, 20, 147));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFont(new Font("Arial", Font.BOLD, 13));
        refreshBtn.addActionListener(this);
        cancelPanel.add(refreshBtn);

        backBtn = new Button("DASHBOARD");
        backBtn.setBounds(500, 17, 120, 29);
        backBtn.setBackground(new Color(220, 20, 100));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 13));
        backBtn.addActionListener(this);
        cancelPanel.add(backBtn);

        msgLabel = new Label("", Label.CENTER);
        msgLabel.setBounds(0, 448, 700, 22);
        msgLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        msgLabel.setForeground(new Color(139, 0, 79));
        add(msgLabel);

        loadBookings();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                new DashboardFrame(userId, username);
            }
        });
        setVisible(true);
    }

    void loadBookings() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT bk.booking_id, b.bus_name, b.source, b.destination, " +
                            "s.journey_date, s.departure_time, bk.seat_number, " +
                            "bk.booking_date, bk.payment_status, bk.total_amount " +
                            "FROM bookings bk " +
                            "JOIN schedules s ON bk.schedule_id=s.schedule_id " +
                            "JOIN buses b ON s.bus_id=b.bus_id " +
                            "WHERE bk.user_id=? ORDER BY bk.booking_id DESC");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-6s %-18s %-10s %-10s %-12s %-9s %-6s %-12s %-8s%n",
                    "Bk.ID", "Bus Name", "From", "To", "Journey", "Depart", "Seat", "Status", "Amount"));
            sb.append("-".repeat(95)).append("\n");

            int count = 0;
            while (rs.next()) {
                count++;
                sb.append(String.format("%-6d %-18s %-10s %-10s %-12s %-9s %-6d %-12s Rs%-6d%n",
                        rs.getInt("booking_id"), rs.getString("bus_name"),
                        rs.getString("source"), rs.getString("destination"),
                        rs.getString("journey_date"), rs.getString("departure_time"),
                        rs.getInt("seat_number"), rs.getString("payment_status"),
                        rs.getInt("total_amount")));
            }
            bookingsArea.setText(count == 0 ? "No bookings yet." : sb.toString());
            con.close();
        } catch (Exception ex) {
            bookingsArea.setText("Error: " + ex.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelBtn) {
            String idStr = cancelField.getText().trim();
            if (idStr.isEmpty()) {
                msgLabel.setForeground(Color.RED);
                msgLabel.setText("Enter a Booking ID to cancel!");
                return;
            }
            try {
                int bookingId = Integer.parseInt(idStr);
                Connection con = DBConnection.getConnection();
                PreparedStatement chk = con.prepareStatement(
                        "SELECT * FROM bookings WHERE booking_id=? AND user_id=?");
                chk.setInt(1, bookingId); chk.setInt(2, userId);
                ResultSet rs = chk.executeQuery();
                if (!rs.next()) {
                    msgLabel.setForeground(Color.RED);
                    msgLabel.setText("Booking not found or not yours!");
                    con.close(); return;
                }
                int schedId = rs.getInt("schedule_id");
                PreparedStatement del = con.prepareStatement(
                        "DELETE FROM bookings WHERE booking_id=?");
                del.setInt(1, bookingId); del.executeUpdate();
                PreparedStatement upd = con.prepareStatement(
                        "UPDATE schedules SET available_seats=available_seats+1 WHERE schedule_id=?");
                upd.setInt(1, schedId); upd.executeUpdate();
                con.close();
                msgLabel.setForeground(Color.GREEN);
                msgLabel.setText("Booking #" + bookingId + " cancelled successfully!");
                cancelField.setText("");
                loadBookings();
            } catch (NumberFormatException ex) {
                msgLabel.setForeground(Color.RED);
                msgLabel.setText("Enter a valid numeric Booking ID!");
            } catch (Exception ex) {
                msgLabel.setForeground(Color.RED);
                msgLabel.setText("Error: " + ex.getMessage());
            }
        } else if (e.getSource() == refreshBtn) {
            loadBookings();
            msgLabel.setText("");
        } else if (e.getSource() == backBtn) {
            dispose();
            new DashboardFrame(userId, username);
        }
    }
}