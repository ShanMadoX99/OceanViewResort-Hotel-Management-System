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

    // ✅ Update: Modify reservation details
    public String updateReservation(int customerId, String name, String address,
                                    String contact, String email, String roomType,
                                    String checkIn, String checkOut) {
        ReservationRecord r = findReservationById(customerId);
        if (r != null) {
            if (name != null && !name.isEmpty()) r.setCustomerName(name);
            if (address != null && !address.isEmpty()) r.setAddress(address);
            if (contact != null && !contact.isEmpty()) r.setContactNo(contact);
            if (email != null && !email.isEmpty()) r.setEmail(email);
            if (roomType != null && !roomType.isEmpty()) r.setRoomType(roomType);
            if (checkIn != null && !checkIn.isEmpty()) r.setCheckIn(checkIn);
            if (checkOut != null && !checkOut.isEmpty()) r.setCheckOut(checkOut);

            return "✅ Reservation updated successfully!";
        }
        return "❌ Reservation not found.";
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