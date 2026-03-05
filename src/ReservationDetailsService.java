import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

public class ReservationDetailsService {

    // In-memory list of reservations (shared across tabs)
    private static final List<ReservationRecord> reservations = new ArrayList<>();

    // ✅ Called from ReservationTab when adding a new reservation
    public static void addReservationRecord(ReservationRecord record) {
        reservations.add(record);
    }

    // ✅ Read: Get all reservations for a customer
    public ObservableList<ReservationRecord> getReservationsByCustomerId(int customerId) {
        List<ReservationRecord> result = new ArrayList<>();
        for (ReservationRecord r : reservations) {
            if (r.getCustomerId() == customerId) {
                result.add(r);
            }
        }
        return FXCollections.observableArrayList(result);
    }

    // ✅ Helper: Find a single reservation by Customer ID
    public ReservationRecord findReservationById(int customerId) {
        for (ReservationRecord r : reservations) {
            if (r.getCustomerId() == customerId) {
                return r;
            }
        }
        return null;
    }

    // ❌ Update restricted: must use Reservation ID
    public String updateReservation(int customerId, String name, String address,
                                    String contact, String email, String roomType,
                                    String checkIn, String checkOut) {
        return "❌ Update failed: Updates can only be done using Reservation ID (Customer ID cannot be used).";
    }

    // ✅ Delete: Remove reservation
    public String deleteReservation(int customerId) {
        ReservationRecord r = findReservationById(customerId);
        if (r != null) {
            reservations.remove(r);
            return "✅ Reservation deleted successfully!";
        }
        return "❌ Reservation not found.";
    }
}