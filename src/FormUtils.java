import javafx.scene.control.*;

public class FormUtils {

    // Clear text fields
    public static void clearTextFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    // Clear date pickers
    public static void clearDatePickers(DatePicker... pickers) {
        for (DatePicker picker : pickers) {
            picker.setValue(null);
        }
    }

    // Clear combo boxes
    public static void clearComboBoxes(ComboBox<?>... boxes) {
        for (ComboBox<?> box : boxes) {
            box.setValue(null);
        }
    }
}