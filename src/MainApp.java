import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ocean View Resort - Hotel Management System");

        // Locked menu (tabs disabled until login)
        TabPane tabPane = new TabPane();
        tabPane.setDisable(true); // locked until login

        // Add all tabs
        tabPane.getTabs().addAll(
                ReservationTab.createReservationTab(),
                ReservationDetailsTab.createReservationDetailsTab(),
                BillsTab.createBillsTab(),
                ReportTab.createReportTab(),
                HelpTab.createHelpTab()
        );

        // --- Watermark Logo ---
        // Load your logo image (adjust path if needed)
        Image logo = new Image("file:src/main/resources/logo.png");
        ImageView logoView = new ImageView(logo);

        // Make it look like a watermark
        logoView.setOpacity(0.15);       // faint transparency
        logoView.setPreserveRatio(true); // keep proportions
        logoView.setFitWidth(300);       // scale logo size

        // Layout: put logo behind everything
        BorderPane content = new BorderPane();
        content.setCenter(tabPane);

        StackPane root = new StackPane();
        // StackPane automatically centers the first child (logo) in the window
        root.getChildren().addAll(logoView, content);

        // Scene
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