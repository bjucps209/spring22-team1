import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;


<<<<<<< HEAD
// who dares challenge my rule
=======
// Fine. be that way.
>>>>>>> 29495b22da3dcfc326e5f94e5eeb690d2c4f0a6e

public class MainWindow {

    @FXML
    void onGreetClicked(ActionEvent event) {
        var alert = new Alert(AlertType.INFORMATION, "Hello, world!");
        alert.setHeaderText(null);
        alert.show();
    }
}
