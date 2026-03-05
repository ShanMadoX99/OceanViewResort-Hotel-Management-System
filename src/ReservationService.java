import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReservationService {

    // ✅ Create
    public String addReservation(String name, String address, String contact, String email,
                                 String roomType, String checkIn, String checkOut) {
        try {
            Connection conn = DBConnection.getConnection();

            // Step 1: Insert customer if not exists
            String customerSql = "INSERT INTO customers (name, address, contact_no, email) VALUES (?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE name=VALUES(name), address=VALUES(address), contact_no=VALUES(contact_no)";
            PreparedStatement psCustomer = conn.prepareStatement(customerSql, PreparedStatement.RETURN_GENERATED_KEYS);
            psCustomer.setString(1, name);
            psCustomer.setString(2, address);
            psCustomer.setString(3, contact);
            psCustomer.setString(4, email);
            psCustomer.executeUpdate();

            int customerId;
            ResultSet keys = psCustomer.getGeneratedKeys();
            if (keys.next()) {
                customerId = keys.getInt(1);
            } else {
                PreparedStatement psFind = conn.prepareStatement("SELECT customer_id FROM customers WHERE email=?");
                psFind.setString(1, email);
                ResultSet rs = psFind.executeQuery();
                rs.next();
                customerId = rs.getInt("customer_id");
            }

            // Step 2: Insert reservation linked to customer
            String reservationSql = "INSERT INTO reservations (customer_id, room_type, check_in, check_out) VALUES (?, ?, ?, ?)";
            PreparedStatement psRes = conn.prepareStatement(reservationSql, PreparedStatement.RETURN_GENERATED_KEYS);
            psRes.setInt(1, customerId);
            psRes.setString(2, roomType);
            psRes.setString(3, checkIn);
            psRes.setString(4, checkOut);
            psRes.executeUpdate();

            // ✅ Get generated reservation ID
            int reservationId;
            ResultSet resKeys = psRes.getGeneratedKeys();
            if (resKeys.next()) {
                reservationId = resKeys.getInt(1);
            } else {
                // Fallback: query last inserted reservation for this customer
                PreparedStatement psFindRes = conn.prepareStatement(
                        "SELECT reservation_id FROM reservations WHERE customer_id=? ORDER BY reservation_id DESC LIMIT 1");
                psFindRes.setInt(1, customerId);
                ResultSet rsRes = psFindRes.executeQuery();
                rsRes.next();
                reservationId = rsRes.getInt("reservation_id");
            }

            // Step 3: Confirmation message with customer + reservation details
            return "✅ Reservation added successfully!" +
                    "\nCustomer ID: " + customerId +
                    "\nReservation ID: " + reservationId +
                    "\nName: " + name +
                    "\nContact: " + contact;
        } catch (Exception e) {
            return "❌ Error adding reservation: " + e.getMessage();
        }
    }

    // ✅ Read: Get reservation by Reservation ID
    public ObservableList<ReservationRecord> getReservationById(int reservationId) {
        ObservableList<ReservationRecord> result = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT r.reservation_id, r.customer_id, c.name, c.address, c.contact_no, c.email, " +
                    "r.room_type, r.check_in, r.check_out " +
                    "FROM reservations r JOIN customers c ON r.customer_id = c.customer_id " +
                    "WHERE r.reservation_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, reservationId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReservationRecord record = new ReservationRecord(
                        rs.getInt("reservation_id"),
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("contact_no"),
                        rs.getString("email"),
                        rs.getString("room_type"),
                        rs.getString("check_in"),
                        rs.getString("check_out")
                );
                result.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // ✅ Read: Get reservations by Customer ID (added back)
    public ObservableList<ReservationRecord> getReservationsByCustomerId(int customerId) {
        ObservableList<ReservationRecord> result = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT r.reservation_id, r.customer_id, c.name, c.address, c.contact_no, c.email, " +
                    "r.room_type, r.check_in, r.check_out " +
                    "FROM reservations r JOIN customers c ON r.customer_id = c.customer_id " +
                    "WHERE r.customer_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReservationRecord record = new ReservationRecord(
                        rs.getInt("reservation_id"),
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("contact_no"),
                        rs.getString("email"),
                        rs.getString("room_type"),
                        rs.getString("check_in"),
                        rs.getString("check_out")
                );
                result.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // ✅ Update
    public String updateReservation(int reservationId, String name, String address,
                                    String contact, String email, String roomType,
                                    String checkIn, String checkOut) {
        try {
            Connection conn = DBConnection.getConnection();

            // Update customer details
            String customerSql = "UPDATE customers SET name=?, address=?, contact_no=?, email=? " +
                    "WHERE customer_id=(SELECT customer_id FROM reservations WHERE reservation_id=?)";
            PreparedStatement psCustomer = conn.prepareStatement(customerSql);
            psCustomer.setString(1, name);
            psCustomer.setString(2, address);
            psCustomer.setString(3, contact);
            psCustomer.setString(4, email);
            psCustomer.setInt(5, reservationId);
            psCustomer.executeUpdate();

            // Update reservation details
            String reservationSql = "UPDATE reservations SET room_type=?, check_in=?, check_out=? WHERE reservation_id=?";
            PreparedStatement psRes = conn.prepareStatement(reservationSql);
            psRes.setString(1, roomType);
            psRes.setString(2, checkIn);
            psRes.setString(3, checkOut);
            psRes.setInt(4, reservationId);
            psRes.executeUpdate();

            return "✅ Reservation updated successfully!";
        } catch (Exception e) {
            return "❌ Error updating reservation: " + e.getMessage();
        }
    }

    // ✅ Delete
    public String deleteReservation(int reservationId) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE FROM reservations WHERE reservation_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, reservationId);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                return "✅ Reservation deleted successfully!";
            } else {
                return "❌ Reservation not found.";
            }
        } catch (Exception e) {
            return "❌ Error deleting reservation: " + e.getMessage();
        }
    }
}