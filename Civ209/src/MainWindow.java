import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

/*
<<<<<<< HEAD
<<<<<<< HEAD
To do or not to do, that is the question.
=======
Hey Izzo how you doing
>>>>>>> 2a9f573f2c6f725cbdd227da5f62233d6d8478fb
=======
I control the timeline now
>>>>>>> 73055127bd80fc2a6c5c2bd45fd9c2b7fe00c63b
*/
public class MainWindow {

    @FXML
    void onGreetClicked(ActionEvent event) {
        var alert = new Alert(AlertType.INFORMATION, "Hello, world!");
        alert.setHeaderText(null);
        alert.show();

    }
}
