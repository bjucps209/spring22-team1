//---------------------------------------------------------------
//File:   MainWindow.java
//Desc:   This file holds the logic for main window (home screen).
//---------------------------------------------------------------

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Constants;

public class MainWindow {
    // ImageView for the castle on the title.
    ImageView imgView = new ImageView(Constants.cityImage);

    // ImageView for the castle on the title.
    ImageView imgView2 = new ImageView(Constants.cityImage);

    // Label of the left image.
    @FXML
    Label lblLeftImg;

    // Label of the right image.
    @FXML
    Label lblRightImg;

    // Label for the game title itself.
    @FXML
    Label lblGameTitle;

    // Main VBox
    @FXML
    VBox VBoxMain;

    @FXML
    /**
     * Initialize the main screen (plus the easter egg level if title is clicked)
     */
    public void initialize() throws IOException {
        lblGameTitle.setFont(Font.font("Impact", 40)); // https://www.codegrepper.com/code-examples/java/how+to+change+font+in+javafx
        // Add in the castle images
        imgView.setFitHeight(40);
        imgView2.setFitHeight(40);
        imgView.setPreserveRatio(true);
        imgView2.setPreserveRatio(true);
        lblLeftImg.setGraphic(imgView2);
        lblRightImg.setGraphic(imgView);
        lblGameTitle.setOnMouseClicked(e -> {
            Constants.switchImages();
            if (VBoxMain.getChildren().size() < 7)
                VBoxMain.getChildren().add(new Label("Easter Egg Activated."));
        });
    }

    @FXML
    /**
     * Open up the Levels screen
     */
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
    /**
     * Load the HighScores screen
     */
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
    /**
     * Load the About Screen
     */
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
    /**
     * Load the Help Screen
     */
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

}
