import javafx.stage.FileChooser;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.io.File;

public class ReportTab {

    public static Tab createReportTab() {
        Tab tab = new Tab("Reports");

        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Start Date");

        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPromptText("End Date");

        TextField customerIdField = new TextField();
        customerIdField.setPromptText("Customer ID (optional)");

        ComboBox<String> roomTypeBox = new ComboBox<>();
        roomTypeBox.getItems().addAll("", "Deluxe", "Standard");
        roomTypeBox.setPromptText("Room Type (optional)");

        Button generateButton = new Button("Generate Report");
        Button exportButton = new Button("Export to CSV");

        TableView<ReportRecord> table = new TableView<>();

        TableColumn<ReportRecord, Number> resIdCol = new TableColumn<>("Reservation ID");
        resIdCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getReservationId()));

        TableColumn<ReportRecord, Number> custIdCol = new TableColumn<>("Customer ID");
        custIdCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getCustomerId()));

        TableColumn<ReportRecord, String> nameCol = new TableColumn<>("Customer Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCustomerName()));

        TableColumn<ReportRecord, String> contactCol = new TableColumn<>("Contact No");
        contactCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getContactNo()));

        TableColumn<ReportRecord, String> roomCol = new TableColumn<>("Room Type");
        roomCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getRoomType()));

        TableColumn<ReportRecord, String> checkInCol = new TableColumn<>("Check-In");
        checkInCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCheckIn()));

        TableColumn<ReportRecord, String> checkOutCol = new TableColumn<>("Check-Out");
        checkOutCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCheckOut()));

        table.getColumns().addAll(resIdCol, custIdCol, nameCol, contactCol, roomCol, checkInCol, checkOutCol);

        Label summaryLabel = new Label();
        ReportResult[] lastResult = new ReportResult[1]; // store last generated result

        generateButton.setOnAction(e -> {
            try {
                LocalDate startDate = startDatePicker.getValue();
                LocalDate endDate = endDatePicker.getValue();
                String roomType = roomTypeBox.getValue();
                Integer customerId = customerIdField.getText().isEmpty() ? null : Integer.parseInt(customerIdField.getText());

                if (startDate == null || endDate == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "❌ Please select both start and end dates.");
                    alert.showAndWait();
                    return;
                }

                ReportService service = new ReportService();
                ReportResult result = service.generateReport(startDate, endDate, roomType, customerId);

                table.setItems(result.getRecords());
                summaryLabel.setText("📊 Total Reservations: " + result.getTotalReservations() +
                        " | 💰 Total Revenue: " + result.getTotalRevenue() + " LKR");

                lastResult[0] = result;
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "❌ Customer ID must be a number.");
                alert.showAndWait();
            }
        });

        exportButton.setOnAction(e -> {
            if (lastResult[0] == null || lastResult[0].getRecords().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "❌ Please generate a report before exporting.");
                alert.showAndWait();
                return;
            }
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Report");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
                fileChooser.setInitialFileName("report.csv");

                File file = fileChooser.showSaveDialog(null);
                if (file == null) return; // user cancelled

                try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                    writer.println("Reservation ID,Customer ID,Customer Name,Contact No,Room Type,Check-In,Check-Out");
                    for (ReportRecord record : lastResult[0].getRecords()) {
                        writer.println(record.getReservationId() + "," +
                                record.getCustomerId() + "," +
                                record.getCustomerName() + "," +
                                record.getContactNo() + "," +
                                record.getRoomType() + "," +
                                record.getCheckIn() + "," +
                                record.getCheckOut());
                    }
                    writer.println();
                    writer.println("Total Reservations," + lastResult[0].getTotalReservations());
                    writer.println("Total Revenue," + lastResult[0].getTotalRevenue() + " LKR");
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "✅ Report exported successfully to: " + file.getAbsolutePath());
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "❌ Error exporting report: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        VBox vbox = new VBox(10, startDatePicker, endDatePicker, roomTypeBox, customerIdField,
                generateButton, exportButton, table, summaryLabel);
        vbox.setStyle("-fx-padding: 20;");

        BorderPane pane = new BorderPane();
        pane.setCenter(vbox);

        tab.setContent(pane);
        return tab;
    }
}