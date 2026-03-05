import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ocean View Resort - Hotel Management System");

        // Locked menu (tabs disabled until login)
        TabPane tabPane = new TabPane();
        tabPane.setDisable(true); // locked until login
        // After tabPane is created

        tabPane.getTabs().addAll(
                ReservationTab.createReservationTab(),
                ReservationDetailsTab.createReservationDetailsTab(),
                BillsTab.createBillsTab(),
                HelpTab.createHelpTab(),
                ReportTab.createReportTab()
        );



        // Layout
        BorderPane root = new BorderPane();
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Show login window first
        LoginWindow.showLogin(primaryStage, tabPane);
    }

    public static void main(String[] args) {
        launch(args);
    }
}