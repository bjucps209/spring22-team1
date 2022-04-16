import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Constants;

public class MainWindow {

    @FXML
    Label lblGameTitle;

    @FXML
    public void initialize() throws IOException {
        lblGameTitle.setFont(Font.font("Times New Roman", 40)); // https://www.codegrepper.com/code-examples/java/how+to+change+font+in+javafx

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

        try {
            var loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
            Scene scene;
            scene = new Scene(loader.load());
            var stage = new Stage();
            stage.setScene(scene);
            stage.show();
            GameWindow gameWindow = loader.getController();
            gameWindow.initialize("");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    public void onScoresClicked(ActionEvent event) {
        try {
            var loader = new FXMLLoader(getClass().getResource("HighScores.fxml"));
            Scene scene;
            scene = new Scene(loader.load());

            var stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
