import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Levels {
    @FXML
    Label lblLevelTitle;

    public void initialize() {
        lblLevelTitle.setFont(Font.font("Impact", 30));
    }

    @FXML
    public void onLoadClicked(ActionEvent event) {
        try {
            var loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
            Scene scene;
            scene = new Scene(loader.load());
            var stage = new Stage();
            stage.setScene(scene);
            stage.show();
            GameWindow gameWindow = loader.getController();
            gameWindow.initialize("Levels/savedGame.dat");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    public void onPlayClicked(ActionEvent event) {
        // setName(userName.getText().toString()); fix to get the player name to write
        // to the file

        try {
            var loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
            Scene scene;
            scene = new Scene(loader.load());
            var stage = new Stage();
            stage.setScene(scene);
            stage.show();
            GameWindow gameWindow = loader.getController();
            gameWindow.initialize("Levels/Level1.dat");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}