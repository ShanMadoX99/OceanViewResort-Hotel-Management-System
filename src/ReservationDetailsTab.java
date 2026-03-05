import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ReservationDetailsTab {

    public static Tab createReservationDetailsTab() {
        Tab tab = new Tab("Reservation Details");

        TextField customerIdField = new TextField();
        customerIdField.setPromptText("Enter Customer ID");

        Button searchButton = new Button("Search");

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

        searchButton.setOnAction(e -> {
            try {
                int customerId = Integer.parseInt(customerIdField.getText());
                ReservationDetailsService service = new ReservationDetailsService();
                table.setItems(service.getReservationsByCustomerId(customerId));
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "❌ Please enter a valid Customer ID.");
                alert.showAndWait();
            }
        });

        VBox vbox = new VBox(10, customerIdField, searchButton, table);
        vbox.setStyle("-fx-padding: 20;");

        BorderPane pane = new BorderPane();
        pane.setCenter(vbox);

        tab.setContent(pane);
        return tab;
    }
}