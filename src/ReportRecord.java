public class ReportRecord {
    private int reservationId;
    private int customerId;
    private String customerName;
    private String contactNo;
    private String roomType;
    private String checkIn;
    private String checkOut;

    public ReportRecord(int reservationId, int customerId, String customerName, String contactNo,
                        String roomType, String checkIn, String checkOut) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.contactNo = contactNo;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public int getReservationId() { return reservationId; }
    public int getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getContactNo() { return contactNo; }
    public String getRoomType() { return roomType; }
    public String getCheckIn() { return checkIn; }
    public String getCheckOut() { return checkOut; }
}