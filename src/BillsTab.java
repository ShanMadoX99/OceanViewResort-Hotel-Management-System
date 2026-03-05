import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.print.PrinterJob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BillsTab {

    public static Tab createBillsTab() {
        Tab tab = new Tab("Bills");

        TextField customerIdField = new TextField();
        customerIdField.setPromptText("Enter Customer ID");

        Button calcButton = new Button("Calculate Bill");
        Button printButton = new Button("Print Bill");

        Label resultLabel = new Label();

        // Calculate bill
        calcButton.setOnAction(e -> {
            try {
                int customerId = Integer.parseInt(customerIdField.getText());
                BillsService service = new BillsService();

                String billText = service.calculateBillForCustomer(customerId);

                if (billText == null) {
                    // ❌ Show message box if no customer/reservation found
                    Alert alert = new Alert(Alert.AlertType.WARNING,
                            "No customer ID aligns with a bill.");
                    alert.setHeaderText("Bill Not Found");
                    alert.showAndWait();
                    resultLabel.setText(""); // clear old bill
                } else {
                    // ✅ Add date/time stamp
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    // Format like a receipt
                    String formattedBill =
                            "============================\n" +
                                    " Ocean View Resort\n" +
                                    " Hotel Management System\n" +
                                    "============================\n" +
                                    billText + "\n" +
                                    "Generated On: " + now.format(formatter) + "\n" +
                                    "----------------------------\n" +
                                    " Thank you for your stay!\n" +
                                    "============================";

                    resultLabel.setText(formattedBill);
                }

                customerIdField.clear();
                customerIdField.requestFocus();

            } catch (NumberFormatException ex) {
                resultLabel.setText("❌ Please enter a valid Customer ID.");
            }
        });

        // Print bill
        printButton.setOnAction(e -> {
            if (resultLabel.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "No bill to print. Please calculate a bill first.");
                alert.setHeaderText("Print Error");
                alert.showAndWait();
                return;
            }

            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null && job.showPrintDialog(null)) {
                boolean success = job.printPage(resultLabel);
                if (success) {
                    job.endJob();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            "✅ Bill sent to printer.");
                    alert.setHeaderText("Print Successful");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "❌ Printing failed.");
                    alert.setHeaderText("Print Error");
                    alert.showAndWait();
                }
            }
        });

        VBox vbox = new VBox(10, customerIdField, calcButton, printButton, resultLabel);
        vbox.setStyle("-fx-padding: 20;");

        BorderPane pane = new BorderPane();
        pane.setCenter(vbox);

        tab.setContent(pane);
        return tab;
    }
}