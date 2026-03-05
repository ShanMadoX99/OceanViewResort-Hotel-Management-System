import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginWindow {

    public static void showLogin(Stage parentStage, TabPane tabPane) {
        Stage loginStage = new Stage();
        loginStage.setTitle("Login - Ocean View Resort");
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.initOwner(parentStage);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        Label messageLabel = new Label();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (authenticate(username, password)) {
                messageLabel.setText("✅ Login successful!");
                tabPane.setDisable(false); // unlock menu
                loginStage.close();
            } else {
                messageLabel.setText("❌ Invalid credentials.");
            }
        });

        VBox vbox = new VBox(10, usernameField, passwordField, loginButton, messageLabel);
        vbox.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(vbox, 300, 200);
        loginStage.setScene(scene);
        loginStage.showAndWait();
    }

    private static boolean authenticate(String username, String password) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }
}