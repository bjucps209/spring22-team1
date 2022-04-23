import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Levels {
    @FXML
    Label lblLevelTitle;
    GameWindow currGameWindow;

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
            gameWindow.initialize("../Civ209/Levels/savedGame.dat", this::onGameEnd);
            stage.setOnCloseRequest(e -> onGameClose());
            currGameWindow = gameWindow;
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
            gameWindow.initialize("../Civ209/Levels/DemoLevel.dat", this::onGameEnd);
            stage.setOnCloseRequest(e -> onGameClose());
            currGameWindow = gameWindow;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    public void onCampaignClicked(ActionEvent event) {
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
            gameWindow.initialize("../Civ209/Levels/Level1.dat", this::onGameEnd); // replace with link to campaign level
            stage.setOnCloseRequest(e -> onGameClose());
            currGameWindow = gameWindow;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    public void onSpringClicked(ActionEvent event) {
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
            gameWindow.initialize("../Civ209/Levels/Spring.dat", this::onGameEnd); // replace with link to spring level
            stage.setOnCloseRequest(e -> onGameClose());
            currGameWindow = gameWindow;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    public void onSummerClicked(ActionEvent event) {
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
            gameWindow.initialize("../Civ209/Levels/Summer.dat", this::onGameEnd); // replace with link to summer level
            stage.setOnCloseRequest(e -> onGameClose());
            currGameWindow = gameWindow;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    public void onFallClicked(ActionEvent event) {
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
            gameWindow.initialize("../Civ209/Levels/Fall.dat", this::onGameEnd); // replace with link to fall level
            stage.setOnCloseRequest(e -> onGameClose());
            currGameWindow = gameWindow;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    public void onWinterClicked(ActionEvent event) {
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
            gameWindow.initialize("../Civ209/Levels/Winter.dat", this::onGameEnd); // replace with link to winter level
            stage.setOnCloseRequest(e -> onGameClose());
            currGameWindow = gameWindow;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void onGameClose() {
        currGameWindow.getGame().stopTimer();
        currGameWindow.getGame().getEntityList().clear();
        currGameWindow.pane.getChildren().clear();
    }

    public void onGameEnd(String msg, int score) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.setTitle(String.valueOf(score));
        alert.show();
    }
}