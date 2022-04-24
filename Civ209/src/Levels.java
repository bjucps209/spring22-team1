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
import java.nio.file.Paths;

public class Levels {
    @FXML
    Label lblLevelTitle;

    @FXML
    Button btnBuilt;

    public void initialize() {
        lblLevelTitle.setFont(Font.font("Impact", 30));

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
            stage.setResizable(false);
            GameWindow gameWindow = loader.getController();
            gameWindow.initialize("../Civ209/Levels/savedGame.dat");
            stage.setOnCloseRequest(e -> onGameClose(gameWindow));
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
            stage.setResizable(false);
            GameWindow gameWindow = loader.getController();
            gameWindow.initialize("../Civ209/Levels/Level1.dat");
            stage.setOnCloseRequest(e -> onGameClose(gameWindow));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static int campaignlevel = 0; 

    @FXML
    public void onCampaignClicked(ActionEvent event) {
        // setName(userName.getText().toString()); fix to get the player name to write
        // to the file

        try {
            //TODO make this not open all 4 levels at the same time
            for (int l = 1; l < 5; ++l) {
                var loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
                Scene scene;
                scene = new Scene(loader.load());
                var stage = new Stage();
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);
                GameWindow gameWindow = loader.getController();
                gameWindow.initialize("../Civ209/Levels/CampaignLevel" + l + ".dat");
                ++campaignlevel; 
                gameWindow.getGame().setScore(600);
                stage.setOnCloseRequest(e -> onGameClose(gameWindow));
                stage.setOnCloseRequest(e -> onGameClose(gameWindow));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void incrementCampaignLevel() {
        ++campaignlevel;
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
            stage.setResizable(false);
            GameWindow gameWindow = loader.getController();
            gameWindow.initialize("../Civ209/Levels/Spring.dat"); // replace with link to spring level
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
            stage.setResizable(false);
            GameWindow gameWindow = loader.getController();
            gameWindow.initialize("../Civ209/Levels/Summer.dat"); // replace with link to summer level
            stage.setOnCloseRequest(e -> onGameClose(gameWindow));

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
            stage.setResizable(false);
            GameWindow gameWindow = loader.getController();
            gameWindow.initialize("../Civ209/Levels/Fall.dat"); // replace with link to fall level
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
            stage.setResizable(false);
            GameWindow gameWindow = loader.getController();
            gameWindow.initialize("../Civ209/Levels/Winter.dat"); // replace with link to winter level
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
        } catch (Exception e) {}
    }
}