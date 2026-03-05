import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

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