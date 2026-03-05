import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class BillsTab {

    public static Tab createBillsTab() {
        Tab tab = new Tab("Bills");

        TextField customerIdField = new TextField();
        customerIdField.setPromptText("Enter Customer ID");

        Button calcButton = new Button("Calculate Bill");
        Label resultLabel = new Label();

        calcButton.setOnAction(e -> {
            try {
                int customerId = Integer.parseInt(customerIdField.getText());
                BillsService service = new BillsService();
                resultLabel.setText(service.calculateBillForCustomer(customerId));
            } catch (NumberFormatException ex) {
                resultLabel.setText("❌ Please enter a valid Customer ID.");
            }
        });

        VBox vbox = new VBox(10, customerIdField, calcButton, resultLabel);
        vbox.setStyle("-fx-padding: 20;");

        BorderPane pane = new BorderPane();
        pane.setCenter(vbox);

        tab.setContent(pane);
        return tab;
    }
}