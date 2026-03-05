import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReportService {

    public ReportResult generateReport(LocalDate startDate, LocalDate endDate,
                                       String roomTypeFilter, Integer customerIdFilter) {
        ObservableList<ReportRecord> list = FXCollections.observableArrayList();
        int totalReservations = 0;
        long totalRevenue = 0;

        try {
            Connection conn = DBConnection.getConnection();

            StringBuilder sql = new StringBuilder(
                    "SELECT r.reservation_id, r.customer_id, c.name, c.contact_no, " +
                            "r.room_type, r.check_in, r.check_out " +
                            "FROM reservations r JOIN customers c ON r.customer_id = c.customer_id " +
                            "WHERE r.check_in >= ? AND r.check_out <= ?"
            );

            if (roomTypeFilter != null && !roomTypeFilter.isEmpty()) {
                sql.append(" AND r.room_type = ?");
            }
            if (customerIdFilter != null) {
                sql.append(" AND r.customer_id = ?");
            }

            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setDate(1, java.sql.Date.valueOf(startDate));
            ps.setDate(2, java.sql.Date.valueOf(endDate));

            int paramIndex = 3;
            if (roomTypeFilter != null && !roomTypeFilter.isEmpty()) {
                ps.setString(paramIndex++, roomTypeFilter);
            }
            if (customerIdFilter != null) {
                ps.setInt(paramIndex, customerIdFilter);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int reservationId = rs.getInt("reservation_id");
                int customerId = rs.getInt("customer_id");
                String name = rs.getString("name");
                String contact = rs.getString("contact_no");
                String roomType = rs.getString("room_type");
                LocalDate checkIn = rs.getDate("check_in").toLocalDate();
                LocalDate checkOut = rs.getDate("check_out").toLocalDate();

                list.add(new ReportRecord(
                        reservationId, customerId, name, contact,
                        roomType, checkIn.toString(), checkOut.toString()
                ));

                totalReservations++;
                long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
                int rate = roomType.equalsIgnoreCase("Deluxe") ? 8000 : 5000;
                totalRevenue += nights * rate;
            }
        } catch (Exception e) {
            System.err.println("❌ Error generating report: " + e.getMessage());
        }

        return new ReportResult(list, totalReservations, totalRevenue);
    }
}