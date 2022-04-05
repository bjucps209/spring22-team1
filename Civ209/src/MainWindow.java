import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

/*
<<<<<<< HEAD
To do or not to do, that is the question.
=======
Hewwooooo 
Alpha Bravo Charlie Delta Echo Foxtrot Golf Hotel India Juliet Nove
>>>>>>> 44aff69998261fe47037e2e71cc7429c6bed11ab
*/
public class MainWindow {

    @FXML
    void onGreetClicked(ActionEvent event) {
        var alert = new Alert(AlertType.INFORMATION, "Hello, world!");
        alert.setHeaderText(null);
        alert.show();

    }
}
