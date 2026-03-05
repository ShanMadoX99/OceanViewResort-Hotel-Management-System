public class ReservationRecord {
    private int reservationId;
    private int customerId;
    private String customerName;
    private String address;
    private String contactNo;
    private String email;
    private String roomType;
    private String checkIn;
    private String checkOut;

    // ✅ Constructor
    public ReservationRecord(int reservationId, int customerId, String customerName,
                             String address, String contactNo, String email,
                             String roomType, String checkIn, String checkOut) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.contactNo = contactNo;
        this.email = email;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    // ✅ Getters
    public int getReservationId() { return reservationId; }
    public int getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getAddress() { return address; }
    public String getContactNo() { return contactNo; }
    public String getEmail() { return email; }
    public String getRoomType() { return roomType; }
    public String getCheckIn() { return checkIn; }
    public String getCheckOut() { return checkOut; }

    // ✅ Setters (needed for Update functionality)
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setAddress(String address) { this.address = address; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }
    public void setEmail(String email) { this.email = email; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    public void setCheckIn(String checkIn) { this.checkIn = checkIn; }
    public void setCheckOut(String checkOut) { this.checkOut = checkOut; }
}