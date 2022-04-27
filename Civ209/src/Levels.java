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

    // Stage instance for launching next level
    private Stage stage = null;

    // Keeping track of current campaign level.
    static int campaignlevel = 1;

    // Currently selected difficulty level.
    private Difficulty difficulty = Difficulty.Easy;

    // Label for the title of the level screen.
    @FXML
    Label lblLevelTitle;

    // Button to launch the built level from the level builder.
    @FXML
    Button btnBuilt;

    // Button to set difficulty to easy.
    @FXML
    Button btnEasy;

    // Button to set difficulty to medium.
    @FXML
    Button btnMedium;

    // Button to set difficulty to hard.
    @FXML
    Button btnHard;

    /**
     * initializes the level screen
     */
    public void initialize() {
        lblLevelTitle.setFont(Font.font("Impact", 30));
        btnEasy.setDisable(true);
        // https://howtodoinjava.com/java/io/how-to-check-if-file-exists-in-java/#1-using-filesexists-and-filesnotexists
        Path path = Paths.get("../Civ209/Levels/Level1.dat");

        if (!Files.exists(path)) {
            btnBuilt.setDisable(true);
        }

    }

    /**
     * loads the game and opens game window
     */
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
            gameWindow.initialize("../Civ209/Levels/savedGame.dat", difficulty);
            stage.setOnCloseRequest(e -> onGameClose(gameWindow));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * loads the game made from level builder and opens game windoq
     */
    @FXML
    public void onPlayClicked(ActionEvent event) {
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
            gameWindow.initialize("../Civ209/Levels/Level1.dat", difficulty);
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
                gameWindow.initialize("../Civ209/Levels/CampaignLevel" + level + ".dat", difficulty);
                ++campaignlevel;
                gameWindow.getGame().setScore(600);
                stage.setOnCloseRequest(e -> onGameClose(gameWindow));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    /**
     * Opens the spring level
     */
    public void onSpringClicked(ActionEvent event) {
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
            gameWindow.initialize("../Civ209/Levels/Spring.dat", difficulty);
            stage.setOnCloseRequest(e -> onGameClose(gameWindow));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * Opens the summer level
     */
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
            gameWindow.initialize("../Civ209/Levels/Summer.dat", difficulty); // replace with link to summer level
            stage.setOnCloseRequest(e -> onGameClose(gameWindow));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * Opens the fall level
     */
    public void onFallClicked(ActionEvent event) {
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
            gameWindow.initialize("../Civ209/Levels/Fall.dat", difficulty); // replace with link to fall level
            stage.setOnCloseRequest(e -> onGameClose(gameWindow));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * Opens the winter level
     */
    public void onWinterClicked(ActionEvent event) {
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
            gameWindow.initialize("../Civ209/Levels/Winter.dat", difficulty); // replace with link to winter level
            stage.setOnCloseRequest(e -> onGameClose(gameWindow));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * clears the game window
     */
    public void onGameClose(GameWindow gameWindow) {
        gameWindow.getGame().stopTimer();
        gameWindow.getGame().getEntityList().clear();
        gameWindow.pane.getChildren().clear();
        try {
            gameWindow.music.stop();
        } catch (Exception e) {
        }
    }

    /**
     * sets difficulty to easy
     */
    @FXML
    public void onEasyClicked(ActionEvent e) {
        difficulty = Difficulty.Easy;
        btnEasy.setDisable(true);
        btnMedium.setDisable(false);
        btnHard.setDisable(false);
    }

    /**
     * sets difficulty to medium
     */
    @FXML
    public void onMediumClicked(ActionEvent e) {
        difficulty = Difficulty.Medium;
        btnEasy.setDisable(false);
        btnMedium.setDisable(true);
        btnHard.setDisable(false);
    }

    /**
     * sets difficulty to hard
     */
    @FXML
    public void onHardClicked(ActionEvent e) {
        difficulty = Difficulty.Hard;
        btnEasy.setDisable(false);
        btnMedium.setDisable(false);
        btnHard.setDisable(true);
    }

    /*************************************************************************/
    // Getters and setters
    public int getCampaignLevel() {
        return campaignlevel;
    }
}