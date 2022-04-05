import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

/*
Hewwooooo 
Alpha Bravo Charlie Delta Echo Foxtrot Golf Hotel India Juliet Nove
*/
public class MainWindow {

    @FXML
    void onGreetClicked(ActionEvent event) {
        var alert = new Alert(AlertType.INFORMATION, "Hello, world!");
        alert.setHeaderText(null);
        alert.show();

    }
}
