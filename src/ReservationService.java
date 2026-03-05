import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReservationService {

    public String addReservation(String name, String address, String contact, String email,
                                 String roomType, String checkIn, String checkOut) {
        try {
            Connection conn = DBConnection.getConnection();

            // Step 1: Insert customer if not exists (using email as unique identifier)
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
            PreparedStatement psRes = conn.prepareStatement(reservationSql);
            psRes.setInt(1, customerId);
            psRes.setString(2, roomType);
            psRes.setString(3, checkIn);
            psRes.setString(4, checkOut);
            psRes.executeUpdate();

            // Step 3: Confirmation message with customer details
            return "✅ Reservation added successfully!" +
                    "\nCustomer ID: " + customerId +
                    "\nName: " + name +
                    "\nContact: " + contact;
        } catch (Exception e) {
            return "❌ Error adding reservation: " + e.getMessage();
        }
    }
}