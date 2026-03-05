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

            if (!rsCust.next()) {
                return null; // ❌ No customer found
            }

            String customerName = rsCust.getString("name");
            String contactNo = rsCust.getString("contact_no");

            // Step 2: Fetch reservations
            String sql = "SELECT room_type, check_in, check_out FROM reservations WHERE customer_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            long grandTotal = 0;
            StringBuilder breakdown = new StringBuilder();
            breakdown.append(String.format("%-10s %-7s %-7s %-7s\n", "Item", "Nights", "Rate", "Total"));
            breakdown.append("----------------------------------------\n");

            boolean hasReservation = false;

            while (rs.next()) {
                hasReservation = true;
                String roomType = rs.getString("room_type");
                LocalDate checkIn = rs.getDate("check_in").toLocalDate();
                LocalDate checkOut = rs.getDate("check_out").toLocalDate();

                long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
                int rate = roomType.equalsIgnoreCase("Deluxe") ? 8000 : 5000;
                long total = nights * rate;
                grandTotal += total;

                breakdown.append(String.format("%-10s %-7d %-7d %-7d\n", roomType, nights, rate, total));
            }

            if (!hasReservation) {
                return null; // ❌ No reservations found
            }

            breakdown.append("----------------------------------------\n");
            breakdown.append(String.format("%-10s %-7s %-7s %-7d\n", "Grand", "", "", grandTotal));

            return "Customer ID: " + customerId +
                    "\nName: " + customerName +
                    "\nContact: " + contactNo +
                    "\n\n" + breakdown.toString();

        } catch (Exception e) {
            return "❌ Error calculating bill: " + e.getMessage();
        }
    }
}