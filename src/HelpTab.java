import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class HelpTab {

    public static Tab createHelpTab() {
        Tab tab = new Tab("Help");

        String helpText =
                "📖 Hotel Management System - Help Guide\n\n" +
                        "Reservations Tab:\n" +
                        "• Enter customer details (Name, Address, Contact, Email).\n" +
                        "• Select room type and check-in/check-out dates.\n" +
                        "• Click 'Add Reservation' to save. The system will generate a Customer ID.\n\n" +

                        "Reservation Details Tab:\n" +
                        "• Enter a Customer ID in the search field.\n" +
                        "• Click 'Search' to view all reservations linked to that customer.\n" +
                        "• The table shows Reservation ID, Customer ID, Customer Name, Contact Number, Room Type, Check-In, and Check-Out.\n\n" +

                        "Bills Tab:\n" +
                        "• Enter a Customer ID.\n" +
                        "• Click 'Calculate Bill' to see the total bill across all reservations.\n" +
                        "• The bill will display Customer Name, Contact Number, and the total amount in LKR.\n\n" +

                        "Notes:\n" +
                        "• Customer IDs are unique and automatically generated.\n" +
                        "• Use Customer ID for searching reservations and calculating bills.\n" +
                        "• Ensure correct dates are entered for accurate billing.\n\n" +

                        "For assistance, contact system administrator.";

        Text helpContent = new Text(helpText);
        helpContent.setStyle("-fx-font-size: 14; -fx-padding: 20;");

        BorderPane pane = new BorderPane();
        pane.setCenter(helpContent);

        tab.setContent(pane);
        return tab;
    }
}