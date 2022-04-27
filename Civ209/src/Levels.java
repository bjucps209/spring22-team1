//-----------------------------------------------------------
//File:   Levels.java
//Desc:   File holds the logic for the levels screen.
// Can launch multiple versions of game window, holds logic
// for campaign series.
//-----------------------------------------------------------

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Difficulty;

import java.nio.file.Paths;

public class Levels {

    private Stage stage = null;
    static int campaignlevel = 1;
    private Difficulty difficulty = Difficulty.Easy;

    @FXML
    Label lblLevelTitle;

    @FXML
    Button btnBuilt;
    
    @FXML
    Button btnEasy;

    @FXML
    Button btnMedium;

    @FXML
    Button btnHard;

    // initializes the level screen
    public void initialize() {
        lblLevelTitle.setFont(Font.font("Impact", 30));
        btnEasy.setDisable(true);
        // https://howtodoinjava.com/java/io/how-to-check-if-file-exists-in-java/#1-using-filesexists-and-filesnotexists
        Path path = Paths.get("../Civ209/Levels/Level1.dat");

        if (!Files.exists(path)) {
            btnBuilt.setDisable(true);
        }

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
            stage.setFullScreen(true);
            stage.setResizable(false);
            GameWindow gameWindow = loader.getController();
            gameWindow.initialize("../Civ209/Levels/savedGame.dat",difficulty);
            stage.setOnCloseRequest(e -> onGameClose(gameWindow));
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
            stage.setFullScreen(true);
            stage.setResizable(false);
            stage.setFullScreen(true);

            GameWindow gameWindow = loader.getController();
            gameWindow.initialize("../Civ209/Levels/Level1.dat",difficulty);
            stage.setOnCloseRequest(e -> onGameClose(gameWindow));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the first campaign level
     */
    @FXML
    public void onCampaignClicked(ActionEvent event) {
        // setName(userName.getText().toString()); fix to get the player name to write
        // to the file
        openNextLevel(1);
    }

    /**
     * Opens the next campaign level
     */
    public void openNextLevel(int level) {
        if (level < 5) {
            try {
                var loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
                Scene scene;
                scene = new Scene(loader.load());
                stage = new Stage();
                stage.setScene(scene);
                stage.show();
                stage.setFullScreen(true);
                stage.setResizable(false);
                stage.setFullScreen(true);

                GameWindow gameWindow = loader.getController();
                gameWindow.initialize("../Civ209/Levels/CampaignLevel" + level + ".dat",difficulty);
                ++campaignlevel;
                gameWindow.getGame().setScore(600);
                stage.setOnCloseRequest(e -> onGameClose(gameWindow));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public int getCampaignLevel() {
        return campaignlevel;
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
            stage.setFullScreen(true);
            stage.setResizable(false);
            stage.setFullScreen(true);

            GameWindow gameWindow = loader.getController();
            gameWindow.initialize("../Civ209/Levels/Spring.dat",difficulty);
            stage.setOnCloseRequest(e -> onGameClose(gameWindow));

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
            stage.setFullScreen(true);
            stage.setResizable(false);
            stage.setFullScreen(true);

            GameWindow gameWindow = loader.getController();
            gameWindow.initialize("../Civ209/Levels/Summer.dat",difficulty); // replace with link to summer level
            stage.setOnCloseRequest(e -> onGameClose(gameWindow));

        } catch (IOException e) {
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
            stage.setFullScreen(true);
            stage.setResizable(false);
            stage.setFullScreen(true);

            GameWindow gameWindow = loader.getController();
            gameWindow.initialize("../Civ209/Levels/Fall.dat",difficulty); // replace with link to fall level
            stage.setOnCloseRequest(e -> onGameClose(gameWindow));

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
            stage.setFullScreen(true);
            stage.setResizable(false);
            stage.setFullScreen(true);

            GameWindow gameWindow = loader.getController();
            gameWindow.initialize("../Civ209/Levels/Winter.dat",difficulty); // replace with link to winter level
            stage.setOnCloseRequest(e -> onGameClose(gameWindow));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void onGameClose(GameWindow gameWindow) {
        gameWindow.getGame().stopTimer();
        gameWindow.getGame().getEntityList().clear();
        gameWindow.pane.getChildren().clear();
        try {
            gameWindow.music.stop();
        } catch (Exception e) {
        }
    }

    @FXML
    public void onEasyClicked(ActionEvent e) {
        difficulty = Difficulty.Easy;
        btnEasy.setDisable(true);
        btnMedium.setDisable(false);
        btnHard.setDisable(false);
    }

    @FXML
    public void onMediumClicked(ActionEvent e) {
        difficulty = Difficulty.Medium;
        btnEasy.setDisable(false);
        btnMedium.setDisable(true);
        btnHard.setDisable(false);
    }

    @FXML
    public void onHardClicked(ActionEvent e) {
        difficulty = Difficulty.Hard;
        btnEasy.setDisable(false);
        btnMedium.setDisable(false);
        btnHard.setDisable(true);
    }
}