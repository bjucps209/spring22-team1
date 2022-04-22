import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainWindow {
    public static final Image cityImage = new Image(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7d/BSicon_Castle.svg/32px-BSicon_Castle.svg.png");

    @FXML
    Label lblGameTitle;
    @FXML
    VBox VBoxMain;
    @FXML
    TextField userName;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @FXML
    public void initialize() throws IOException {

        lblGameTitle.setFont(Font.font("Impact", 40)); // https://www.codegrepper.com/code-examples/java/how+to+change+font+in+javafx
    }

    @FXML
    public void onLevelsClicked(ActionEvent event) {
        try {
            var loader = new FXMLLoader(getClass().getResource("Levels.fxml"));
            Scene scene;
            scene = new Scene(loader.load());
            var stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
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

    @FXML
    public void onAboutClicked(ActionEvent event) {
        try {
            var loader = new FXMLLoader(getClass().getResource("AboutScreen.fxml"));
            Scene scene;
            scene = new Scene(loader.load());

            var stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onHelpClicked(ActionEvent event) {
        try {
            var loader = new FXMLLoader(getClass().getResource("HelpScreen.fxml"));
            Scene scene;
            scene = new Scene(loader.load());

            var stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
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
            gameWindow.initialize("../Civ209/Levels/Level1.dat");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
