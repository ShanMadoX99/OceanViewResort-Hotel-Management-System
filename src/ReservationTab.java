import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.time.LocalDate;

public class ReservationTab {

    public static Tab createReservationTab() {
        Tab tab = new Tab("New Reservation");

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

        Button addButton = new Button("Add Reservation");
        Label messageLabel = new Label();

        addButton.setOnAction(e -> {
            try {
                LocalDate inDate = checkInPicker.getValue();
                LocalDate outDate = checkOutPicker.getValue();

                // ✅ Date validation
                if (inDate != null && outDate != null && outDate.isBefore(inDate)) {
                    messageLabel.setText("❌ Invalid entry: Check-Out date cannot be earlier than Check-In date.");
                    return;
                }

                ReservationService service = new ReservationService();
                String result = service.addReservation(
                        nameField.getText(),
                        addressField.getText(),
                        contactField.getText(),
                        emailField.getText(),
                        roomTypeBox.getValue(),
                        checkInPicker.getValue().toString(),
                        checkOutPicker.getValue().toString()
                );
                messageLabel.setText(result);

                // ✅ Clear fields only if success
                if (result.startsWith("✅")) {
                    nameField.clear();
                    addressField.clear();
                    contactField.clear();
                    emailField.clear();
                    roomTypeBox.setValue(null);
                    checkInPicker.setValue(null);
                    checkOutPicker.setValue(null);

                    // Put cursor back to Name field
                    nameField.requestFocus();
                }
            } catch (Exception ex) {
                messageLabel.setText("❌ Error: Please check inputs.");
            }
        });

        VBox vbox = new VBox(10, nameField, addressField, contactField, emailField,
                roomTypeBox, checkInPicker, checkOutPicker, addButton, messageLabel);
        vbox.setStyle("-fx-padding: 20;");

        BorderPane pane = new BorderPane();
        pane.setCenter(vbox);

        tab.setContent(pane);
        return tab;
    }
}