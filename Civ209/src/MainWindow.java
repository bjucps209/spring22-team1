import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

/*
"To be or not to be, that is the question."

-Shakespeare  
*/
public class MainWindow {

    @FXML
    void onGreetClicked(ActionEvent event) {
        var alert = new Alert(AlertType.INFORMATION, "Hello, world!");
        alert.setHeaderText(null);
        alert.show();
    }
}
