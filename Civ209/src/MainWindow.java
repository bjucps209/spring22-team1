import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;


// Fine. be that way.

public class MainWindow {

    @FXML
    void onGreetClicked(ActionEvent event) {
        var alert = new Alert(AlertType.INFORMATION, "Hello, world!");
        alert.setHeaderText(null);
        alert.show();
    }
}
