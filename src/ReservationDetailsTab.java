import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.time.LocalDate;

public class ReservationDetailsTab {

    public static Tab createReservationDetailsTab() {
        Tab tab = new Tab("Reservation Details");

        TextField reservationIdField = new TextField();
        reservationIdField.setPromptText("Enter Reservation ID");

        TextField customerIdField = new TextField();
        customerIdField.setPromptText("Or Enter Customer ID");

        Button searchButton = new Button("Search Reservation");

        // Editable fields for update
        TextField nameField = new TextField();
        nameField.setPromptText("Customer Name");

        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        TextField contactField = new TextField();
        contactField.setPromptText("Contact No.");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        ComboBox<String> roomTypeBox = new ComboBox<>();
        roomTypeBox.getItems().addAll("Deluxe", "Standard");

        DatePicker checkInPicker = new DatePicker();
        DatePicker checkOutPicker = new DatePicker();

        Button updateButton = new Button("Update Reservation");
        Button deleteButton = new Button("Delete Reservation");

        TableView<ReservationRecord> table = new TableView<>();

        TableColumn<ReservationRecord, Number> idCol = new TableColumn<>("Reservation ID");
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getReservationId()));

        TableColumn<ReservationRecord, Number> custIdCol = new TableColumn<>("Customer ID");
        custIdCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getCustomerId()));

        TableColumn<ReservationRecord, String> nameCol = new TableColumn<>("Customer Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCustomerName()));

        TableColumn<ReservationRecord, String> contactCol = new TableColumn<>("Contact No");
        contactCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getContactNo()));

        TableColumn<ReservationRecord, String> roomCol = new TableColumn<>("Room Type");
        roomCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getRoomType()));

        TableColumn<ReservationRecord, String> checkInCol = new TableColumn<>("Check-In");
        checkInCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCheckIn()));

        TableColumn<ReservationRecord, String> checkOutCol = new TableColumn<>("Check-Out");
        checkOutCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCheckOut()));

        table.getColumns().addAll(idCol, custIdCol, nameCol, contactCol, roomCol, checkInCol, checkOutCol);

        // 🔹 Search Action
        searchButton.setOnAction(e -> {
            ReservationService service = new ReservationService();
            ObservableList<ReservationRecord> result = FXCollections.observableArrayList();

            try {
                if (!reservationIdField.getText().isEmpty()) {
                    int reservationId = Integer.parseInt(reservationIdField.getText());
                    result = service.getReservationById(reservationId);
                } else if (!customerIdField.getText().isEmpty()) {
                    int customerId = Integer.parseInt(customerIdField.getText());
                    result = service.getReservationsByCustomerId(customerId);
                }

                if (!result.isEmpty()) {
                    ReservationRecord record = result.get(0);

                    // Fill editable fields
                    nameField.setText(record.getCustomerName());
                    addressField.setText(record.getAddress());
                    contactField.setText(record.getContactNo());
                    emailField.setText(record.getEmail());
                    roomTypeBox.setValue(record.getRoomType());
                    checkInPicker.setValue(LocalDate.parse(record.getCheckIn()));
                    checkOutPicker.setValue(LocalDate.parse(record.getCheckOut()));

                    table.setItems(result);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "❌ Reservation not found.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "❌ Please enter a valid ID.");
                alert.showAndWait();
            }
        });

        // 🔹 Update Action
        updateButton.setOnAction(e -> {
            try {
                if (reservationIdField.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "❌ Updates can only be done using Reservation ID (Customer ID cannot be used).");
                    alert.showAndWait();
                    return;
                }

                LocalDate inDate = checkInPicker.getValue();
                LocalDate outDate = checkOutPicker.getValue();
                if (inDate != null && outDate != null && outDate.isBefore(inDate)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "❌ Invalid entry: Check-Out date cannot be earlier than Check-In date.");
                    alert.showAndWait();
                    return;
                }

                int reservationId = Integer.parseInt(reservationIdField.getText());
                ReservationService service = new ReservationService();
                String result = service.updateReservation(
                        reservationId,
                        nameField.getText(),
                        addressField.getText(),
                        contactField.getText(),
                        emailField.getText(),
                        roomTypeBox.getValue(),
                        checkInPicker.getValue().toString(),
                        checkOutPicker.getValue().toString()
                );

                Alert alert = new Alert(Alert.AlertType.INFORMATION, result);
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "❌ Update failed. Please check inputs.");
                alert.showAndWait();
            }
        });

        // 🔹 Delete Action
        deleteButton.setOnAction(e -> {
            try {
                int reservationId = Integer.parseInt(reservationIdField.getText());
                ReservationService service = new ReservationService();
                String result = service.deleteReservation(reservationId);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, result);
                alert.showAndWait();

                // Clear fields after delete
                reservationIdField.clear();
                customerIdField.clear();
                nameField.clear();
                addressField.clear();
                contactField.clear();
                emailField.clear();
                roomTypeBox.setValue(null);
                checkInPicker.setValue(null);
                checkOutPicker.setValue(null);
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "❌ Please enter a valid Reservation ID.");
                alert.showAndWait();
            }
        });

        VBox vbox = new VBox(10,
                reservationIdField, customerIdField, searchButton,
                nameField, addressField, contactField, emailField,
                roomTypeBox, checkInPicker, checkOutPicker,
                updateButton, deleteButton,
                table);
        vbox.setStyle("-fx-padding: 20;");

        BorderPane pane = new BorderPane();
        pane.setCenter(vbox);

        tab.setContent(pane);
        return tab;
    }
}