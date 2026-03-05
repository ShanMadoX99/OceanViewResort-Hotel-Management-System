import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReservationDetailsService {

    public ObservableList<ReservationRecord> getReservationsByCustomerId(int customerId) {
        ObservableList<ReservationRecord> list = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT r.reservation_id, r.customer_id, c.name, c.contact_no, " +
                    "r.room_type, r.check_in, r.check_out " +
                    "FROM reservations r JOIN customers c ON r.customer_id = c.customer_id " +
                    "WHERE r.customer_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new ReservationRecord(
                        rs.getInt("reservation_id"),
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("contact_no"),
                        rs.getString("room_type"),
                        rs.getDate("check_in").toString(),
                        rs.getDate("check_out").toString()
                ));
            }
        } catch (Exception e) {
            System.err.println("❌ Error fetching reservations: " + e.getMessage());
        }
        return list;
    }
}