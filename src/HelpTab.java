import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class HelpTab {

    public static Tab createHelpTab() {
        Tab tab = new Tab("Help");

        Label intro = new Label("📖 Reservation System Help Guide");
        intro.setStyle("-fx-font-size: 22; -fx-font-weight: bold; -fx-text-fill: #2E86C1; -fx-alignment: center;");

        Label purpose = new Label(
                "This system helps staff manage hotel reservations, customer details, bills, and reports.\n" +
                        "Follow the guidelines below to use each section correctly."
        );
        purpose.setStyle("-fx-font-size: 14; -fx-text-fill: #566573; -fx-alignment: center;");

        // 🏨 New Reservation
        Label reservationHeading = new Label("🏨 New Reservation (Reservation Tab)");
        reservationHeading.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #27AE60;");

        Label reservationHelp = new Label(
                "- Use this tab to add new reservations.\n" +
                        "- Fill in customer details, room type, and dates.\n" +
                        "- Rule: Check-Out date cannot be earlier than Check-In date.\n" +
                        "- After success, you will see a confirmation message and fields will clear."
        );
        reservationHelp.setStyle("-fx-font-size: 13; -fx-text-fill: #145A32;");

        // 📋 Reservation Details
        Label detailsHeading = new Label("📋 Reservation Details (Reservation Details Tab)");
        detailsHeading.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #D35400;");

        Label detailsHelp = new Label(
                "- Search reservations by Reservation ID or Customer ID.\n" +
                        "- Updates and deletes require Reservation ID (Customer ID is only for search).\n" +
                        "- Rule: Check-Out date cannot be earlier than Check-In date.\n" +
                        "- After delete, all fields will clear automatically."
        );
        detailsHelp.setStyle("-fx-font-size: 13; -fx-text-fill: #873600;");

        // 📊 Reports
        Label reportHeading = new Label("📊 Reports (Report Tab)");
        reportHeading.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #8E44AD;");

        Label reportHelp = new Label(
                "- Generate reports by selecting Start and End dates.\n" +
                        "- Optional filters: Customer ID or Room Type.\n" +
                        "- Export reports to CSV after generating.\n" +
                        "- Errors: Missing dates or trying to export before generating will show alerts."
        );
        reportHelp.setStyle("-fx-font-size: 13; -fx-text-fill: #512E5F;");

        // 🧾 Bills
        Label billHeading = new Label("🧾 Bills (Bill Tab)");
        billHeading.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #2980B9;");

        Label billHelp = new Label(
                "- Generate bills using Reservation ID.\n" +
                        "- Bill shows customer details, room type, stay duration, and total amount.\n" +
                        "- Invalid Reservation ID will show an error."
        );
        billHelp.setStyle("-fx-font-size: 13; -fx-text-fill: #1B4F72;");

        // 🚪 Exit
        Label exitHeading = new Label("🚪 Exit & Window Controls");
        exitHeading.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #C0392B;");

        Label exitHelp = new Label(
                "- Use Exit tab to safely close the system.\n" +
                        "- Top-right corner:\n" +
                        "   • Minimize (—)\n" +
                        "   • Maximize (□)\n" +
                        "   • Close (X)"
        );
        exitHelp.setStyle("-fx-font-size: 13; -fx-text-fill: #641E16;");

        // ✅ Key Rules
        Label rulesHeading = new Label("✅ Key Rules for Staff");
        rulesHeading.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #16A085;");

        Label rulesHelp = new Label(
                "1. Always use Reservation ID for updates and deletes.\n" +
                        "2. Customer ID is only for searching reservations.\n" +
                        "3. Check-Out date must be later than Check-In date.\n" +
                        "4. Pay attention to system messages (✅ success, ❌ error).\n" +
                        "5. Fields clear automatically after successful operations."
        );
        rulesHelp.setStyle("-fx-font-size: 13; -fx-text-fill: #145A32;");

        VBox vbox = new VBox(15, intro, purpose,
                reservationHeading, reservationHelp,
                detailsHeading, detailsHelp,
                reportHeading, reportHelp,
                billHeading, billHelp,
                exitHeading, exitHelp,
                rulesHeading, rulesHelp);
        vbox.setStyle("-fx-padding: 20; -fx-background-color: #FDFEFE; -fx-alignment: center;");

        BorderPane pane = new BorderPane();
        pane.setCenter(vbox);

        tab.setContent(pane);
        return tab;
    }
}