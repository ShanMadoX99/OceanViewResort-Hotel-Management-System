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

        TextField passwordVisibleField = new TextField();
        passwordVisibleField.setPromptText("Password");
        passwordVisibleField.setManaged(false);
        passwordVisibleField.setVisible(false);

        // ✅ Sync both fields
        passwordVisibleField.textProperty().bindBidirectional(passwordField.textProperty());

        CheckBox showPasswordCheckBox = new CheckBox("Show Password");
        showPasswordCheckBox.setOnAction(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordVisibleField.setManaged(true);
                passwordVisibleField.setVisible(true);
                passwordField.setManaged(false);
                passwordField.setVisible(false);
            } else {
                passwordVisibleField.setManaged(false);
                passwordVisibleField.setVisible(false);
                passwordField.setManaged(true);
                passwordField.setVisible(true);
            }
        });

        Button loginButton = new Button("Login");
        Label messageLabel = new Label();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.isVisible() ? passwordField.getText().trim() : passwordVisibleField.getText().trim();

            if (authenticate(username, password)) {
                messageLabel.setText("✅ Login successful!");
                tabPane.setDisable(false); // unlock menu
                loginStage.close();
            } else {
                messageLabel.setText("❌ Invalid credentials.");
            }
        });

        VBox vbox = new VBox(10, usernameField, passwordField, passwordVisibleField, showPasswordCheckBox, loginButton, messageLabel);
        vbox.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(vbox, 300, 220);
        loginStage.setScene(scene);
        loginStage.showAndWait();
    }

    private static boolean authenticate(String username, String password) {
        try {
            Connection conn = DBConnection.getConnection();

            // ✅ Case-sensitive login
            String sql = "SELECT * FROM users WHERE BINARY username=? AND BINARY password=?";
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