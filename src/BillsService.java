import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BillsService {

    public String calculateBillForCustomer(int customerId) {
        try {
            Connection conn = DBConnection.getConnection();

            // Step 1: Fetch customer info
            String customerSql = "SELECT name, contact_no FROM customers WHERE customer_id=?";
            PreparedStatement psCust = conn.prepareStatement(customerSql);
            psCust.setInt(1, customerId);
            ResultSet rsCust = psCust.executeQuery();

            String customerName = "Unknown";
            String contactNo = "Unknown";

            if (rsCust.next()) {
                customerName = rsCust.getString("name");
                contactNo = rsCust.getString("contact_no");
            }

            // Step 2: Fetch reservations for this customer
            String sql = "SELECT room_type, check_in, check_out FROM reservations WHERE customer_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            long total = 0;
            while (rs.next()) {
                String roomType = rs.getString("room_type");
                LocalDate checkIn = rs.getDate("check_in").toLocalDate();
                LocalDate checkOut = rs.getDate("check_out").toLocalDate();

                long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
                int rate = roomType.equalsIgnoreCase("Deluxe") ? 8000 : 5000;
                total += nights * rate;
            }

            return "✅ Bill for Customer ID " + customerId +
                    "\nName: " + customerName +
                    "\nContact: " + contactNo +
                    "\nTotal: " + total + " LKR";
        } catch (Exception e) {
            return "❌ Error calculating bill: " + e.getMessage();
        }
    }
}