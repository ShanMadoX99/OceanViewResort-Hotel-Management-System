import javafx.collections.ObservableList;

public class ReportResult {
    private ObservableList<ReportRecord> records;
    private int totalReservations;
    private long totalRevenue;

    public ReportResult(ObservableList<ReportRecord> records, int totalReservations, long totalRevenue) {
        this.records = records;
        this.totalReservations = totalReservations;
        this.totalRevenue = totalRevenue;
    }

    public ObservableList<ReportRecord> getRecords() { return records; }
    public int getTotalReservations() { return totalReservations; }
    public long getTotalRevenue() { return totalRevenue; }
}