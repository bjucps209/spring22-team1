//-----------------------------------------------------------
//File:   GameWindow.java
//Desc:   File holds the logic for the game screen: input from
//        Player and showing model on screen.
//-----------------------------------------------------------

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class GameWindow implements ComputerObserver, GameOverObserver, FireProjectiles {

    // Reference to model.
    private Game game;

    // List of all troops selected.
    private ArrayList<EntityImage> selectedTroops = new ArrayList<EntityImage>();

    // Coordinate that holds the upper left corner of the selection box.
    private Coordinate upperLeft = new Coordinate();

    // Coordinate that holds the lower right corner of the selection box.
    private Coordinate lowerRight = new Coordinate();

    /**
     * Coordinates used in dragging image.
     */
    private class Delta {
        public double x;
        public double y;
    }

    // Variable to hold the drag of the dragging image.
    private Delta dragDelta = new Delta();

    // True if in city, false if not.
    private boolean inCity = false;

    // Instance of Random class for random decisions.
    Random rand = new Random();

    // Music instance.
    AudioClip music;

    // Highscores instance.
    HighScores h = new HighScores();

    // Instance of Levels for passing purposes.
    Levels level = new Levels();

    // True if this level is in the campaign, false if not.
    private boolean isCampaign = false;

    // Main game pane.
    @FXML
    Pane pane;

    // Main Vbox
    @FXML
    VBox vbox;

    // HBox with all of the controls for the game.
    @FXML
    HBox controls;

    // Label that is updated with the score.
    @FXML
    Label scoreLabel = new Label();

    // Label that holds the current value of the slider
    @FXML
    Label lblSize;

    // Slider to determine what percentage of troops to send.
    @FXML
    Slider slider;

    // Play button
    @FXML
    ImageView play = new ImageView(Constants.pauseButton);

    // HBox to hold display
    @FXML
    HBox displayBox;

    // HBox to hold the cheat controls
    @FXML
    HBox cheatControls;

    // Drag VBox
    @FXML
    private VBox dragBox = new VBox();

    @FXML
    /**
     * initializes the game screen
     */
    public void initialize(String lvlname, Difficulty difficulty) {
        game = new Game();
        game.setGameOverObserver(this);
        game.setEntityManager(this::removeEntity);
        game.setOnMakeWeather(this::onMakeWeather);
        game.setOnFireProjectiles(this::onFireProjectiles);
        game.getComputer().setObs(this);
        game.getComputer().setDifficulty(difficulty);
        game.setOnMakeWeather(this::onMakeWeather);
        game.initialize(Difficulty.Easy, lvlname);

        if (game.getSeason() == SeasonType.Summer) {
            showSeason("/images/summer.png");
            try {
                music = new AudioClip(
                        getClass().getResource(Constants.summerMusic).toString());
            } catch (NullPointerException e) {
                System.out.println(
                        "Music isn't working because you're not running the program from the Civ209 folder :(");
            }
        }
        if (game.getSeason() == SeasonType.Fall) {
            showSeason("/images/fall.png");
            try {
                music = new AudioClip(
                        getClass().getResource(Constants.fallMusic).toString());
            } catch (NullPointerException e) {
                System.out.println(
                        "Music isn't working because you're not running the program from the Civ209 folder :(");
            }

        }
        if (game.getSeason() == SeasonType.Winter) {
            showSeason("/images/winter.png");
            try {
                music = new AudioClip(
                        getClass().getResource(Constants.winterMusic).toString());
            } catch (NullPointerException e) {
                System.out.println(
                        "Music isn't working because you're not running the program from the Civ209 folder :(");
            }

        }
        if (game.getSeason() == SeasonType.Spring) {
            showSeason("/images/spring.png");
            try {
                music = new AudioClip(getClass().getResource(Constants.springMusic).toExternalForm());
            } catch (NullPointerException e) {
                System.out.println(
                        "Music isn't working because you're not running the program from the Civ209 folder :(");
            }
        }
        music.play();
        music.setVolume(1);
        music.setCycleCount(AudioClip.INDEFINITE);

        for (Entity entity : game.getEntityList()) {
            new EntityImage(this, pane, entity);
            if (entity instanceof Troop) {
                ((Troop) entity).setTroopDelete(this::onTroopDelete);
            } else if (entity instanceof City) {
                if (((City) entity).getNationality() == Nationality.Player) {
                    game.setNumPlayerCitiesLeft(game.getNumPlayerCitiesLeft() + 1);
                }
            }
        }
        lblSize.textProperty().bind(
                Bindings.createStringBinding(() -> String.valueOf((int) slider.getValue()), slider.valueProperty()));
        // https://stackoverflow.com/questions/10548634/javafx-2-0-adding-border-to-label
        scoreLabel.setStyle("-fx-border-color: black;");
        scoreLabel.setPrefWidth(50);
        scoreLabel.setPrefHeight(10);
        scoreLabel.setTextAlignment(TextAlignment.CENTER);

        scoreLabel.textProperty().bind(SimpleStringProperty.stringExpression(game.scoreProperty()));
        play.setOnMousePressed(e -> {
            if (play.getUserData() == "play") {
                play.setImage(Constants.pauseButtonPressed);
            } else {
                play.setImage(Constants.playButtonPressed);
            }
        });
        play.setOnMouseReleased(e -> onPlayClicked());
        play.setUserData("play");
        play.setFitWidth(40);
        play.setPreserveRatio(true);
        displayBox.getChildren().addAll(List.of(scoreLabel, play));
        pane.setOnMousePressed(me -> {
            if (!game.checkInCity(new Coordinate(me.getX(), me.getY())) && me.getButton() == MouseButton.PRIMARY) {
                deSelect();
                inCity = false;
                dragDelta.x = me.getX();
                dragDelta.y = me.getY();
                dragBox.setLayoutX(me.getX());
                dragBox.setLayoutY(me.getY());
                dragBox.setStyle("-fx-border-color: black");
                pane.getChildren().add(dragBox);
                upperLeft.setX(me.getX());
                upperLeft.setY(me.getY());
            } else if (me.getButton() == MouseButton.PRIMARY
                    && game.checkInCity(new Coordinate(me.getX(), me.getY()))) {
                inCity = true;
            } else {
                deployTroops(me);
                inCity = false;
            }
        });
        pane.setOnMouseDragged(me -> {
            if (!inCity) {
                if (me.getX() - dragDelta.x < 0) {
                    dragBox.setPrefWidth(dragDelta.x - me.getX());
                    dragBox.setLayoutX(me.getX());
                    upperLeft.setX(me.getX());
                    lowerRight.setX(dragDelta.x);
                } else {
                    dragBox.setPrefWidth(me.getX() - dragDelta.x);
                    lowerRight.setX(me.getX());
                    upperLeft.setX(dragDelta.x);
                }
                if (me.getY() - dragDelta.y < 0) {
                    dragBox.setPrefHeight(dragDelta.y - me.getY());
                    dragBox.setLayoutY(me.getY());
                    upperLeft.setY(me.getY());
                    lowerRight.setY(dragDelta.y);
                } else {
                    dragBox.setPrefHeight(me.getY() - dragDelta.y);
                    lowerRight.setY(me.getY());
                    upperLeft.setY(dragDelta.y);
                }
            }
        });

        pane.setOnMouseReleased(me -> {
            try {
                if (me.getButton() == MouseButton.PRIMARY) {
                    deSelect();
                    pane.getChildren().remove(dragBox);
                    game.selectTroops(upperLeft, lowerRight, Nationality.Player).stream().forEach(t -> selectedTroops
                            .add((EntityImage) pane.getChildren()
                                    .filtered(e -> e instanceof EntityImage && ((EntityImage) e).getEntity() == t)
                                    .toArray()[0]));
                    for (EntityImage image : selectedTroops) {
                        image.getStyleClass().add("selected");
                    }
                }
                upperLeft = new Coordinate();
                lowerRight = new Coordinate();
                dragBox = new VBox();
                dragDelta = new Delta();
            } catch (IndexOutOfBoundsException e) {
            }

        });
        // Prevent mouse clicks on img from propagating to the pane and
        // resulting in creation of a new image
        pane.setOnMouseClicked(me -> me.consume());

        if (lvlname.equals("../Civ209/Levels/CampaignLevel1.dat") ||
                lvlname.equals("../Civ209/Levels/CampaignLevel2.dat") ||
                lvlname.equals("../Civ209/Levels/CampaignLevel3.dat") ||
                lvlname.equals("../Civ209/Levels/CampaignLevel4.dat")) {
            isCampaign = true;
        }
    }

    /**
     * Makes a weather entity image
     */
    public void onMakeWeather() {
        Weather weather = game.makeWeather();
        new EntityImage(this, pane, weather);
    }

    /**
     * Makes projectiles visible for .5 seconds
     */
    public void onFireProjectiles(Projectile proj) {
        EntityImage firedprojectile = new EntityImage(this, pane, proj);
        var keyFrame = new KeyFrame(Duration.millis(500), e -> {
            pane.getChildren().remove(firedprojectile.getProjectileLine());
        });
        var timer = new Timeline(keyFrame);
        timer.play();
    }

    /**
     * Deselects the entity
     * 
     * @param city to deselect
     * @param node to dehighlight
     */
    public void deSelect() {
        for (Node oldNodes : pane.getChildren()) {
            if (oldNodes.getStyleClass().contains("selected")) {
                oldNodes.getStyleClass().remove("selected");
            }
        }
        selectedTroops.clear();
        game.deSelect();
    }

    /**
     * Selects the entity
     * 
     * @param city to select
     * @param node to highlight
     */
    public void onSelected(Circle node, MouseEvent e, City city) {
        // do a left click, check to see if it's a player city.
        if (e.getButton() == MouseButton.PRIMARY && node.getStroke() == Paint.valueOf("blue")) {
            deSelect();
            game.setSelectedCity(city);
            node.getStyleClass().add("selected");
        }
    }

    /**
     * Sends the troops from the city to the destination
     * 
     * @param selectedCity
     * @param destination
     */
    public void sendTroopsFromCity(City selectedCity, Coordinate destination) {
        game.sendTroopsFromCity(selectedCity, destination, slider.getValue()).stream().forEach(t -> {
            EntityImage circle = new EntityImage(this, pane, t);
            circle.setUserData(t);
            t.setTroopDelete(this::onTroopDelete);
        });
    }

    /**
     * Sends the troops from the ground to the destination
     * 
     * @param troops      to send
     * @param destination
     */
    public void sendTroopsFromGround(ArrayList<Troop> troops, Coordinate destination) {
        game.sendTroopsFromGround(troops, destination, DestinationType.City).stream()
                .forEach(t -> t.setTroopDelete(this::onTroopDelete));
        ;
    }

    /**
     * Displays moving troops
     */
    public void deployTroops(MouseEvent e) {
        Coordinate destination = new Coordinate(e.getX(), e.getY());
        boolean pointInCircle = game.checkInCity(destination);
        Coordinate cityCenter = game.checkInCity(destination, true);
        if (e.getButton() == MouseButton.SECONDARY) {
            ArrayList<Troop> troops = game.deployTroops(pointInCircle, cityCenter, destination, slider.getValue());
            if (game.getSelectedCity() != null) {
                if (pointInCircle) {
                    for (Troop troop : troops) {
                        EntityImage circle = new EntityImage(this, pane, troop);
                        circle.setUserData(troop);
                        troop.setTroopDelete(this::onTroopDelete);
                    }
                } else {
                    for (Troop troop : troops) {
                        EntityImage circle = new EntityImage(this, pane, troop);
                        circle.setUserData(troop);
                    }
                }
            } else {
                troops.stream().forEach(t -> t.setTroopDelete(this::onTroopDelete));
            }
        }
    }

    /**
     * deletes the troop
     * 
     * @param troops to delete
     */
    public void onTroopDelete(Troop troop) {
        for (Node node : pane.getChildren()) {
            if (node instanceof EntityImage && ((EntityImage) node).getEntity() == troop) {
                pane.getChildren().remove(node);
                break;
            }
        }
    }

    /**
     * displays the troops
     * 
     * @param troops to render
     */
    public void renderTroops(ArrayList<Troop> troops) {
        for (Troop troop : troops) {
            EntityImage circle = new EntityImage(this, pane, troop);
            circle.setUserData(troop);
            troop.setTroopDelete(this::onTroopDelete);
        }
    }

    /**
     * Pauses and plays the game
     */
    public void onPlayClicked() {
        if (play.getUserData() == "paused") {
            play.setUserData("play");
            game.startTimer();
            play.setImage(Constants.pauseButton);
        } else {
            play.setUserData("paused");
            game.stopTimer();
            play.setImage(Constants.playButton);
        }
    }

    /**
     * When the game is over, this is called to get the player name
     * 
     * @param msg
     * @param score
     */
    public void recognizeGameOver(String msg, int score) {

        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setFullScreen(false);
        h.load();
        // https://stackoverflow.com/questions/20132239/getting-text-from-a-dialog-box
        String name = JOptionPane.showInputDialog("GAME OVER - Score: " + score, "Enter your name");

        // If there is no name given, choose one of ours :)
        if (name == null || name.equals("Enter your name") || name.equals("")) {
            int text = rand.nextInt(4); // 0, 4
            if (text == 0) {
                name = "Rhys";
            } else if (text == 1) {
                name = "Izzo";
            } else if (text == 2) {
                name = "Ryan";
            } else {
                name = "Emily";
            }

        }

        // Johnika is not allowed - Fuller
        if (name == "Johnika") {
            name = "That's a bad name.";
        }

        h.addScoreList(new ScoreEntry(name, score));
        h.sortScores(h.getScoreList());
        h.save(h.getScoreList());
        music.stop();
        stage.close();

        stage.close();

        if (isCampaign) {
            level.onGameClose(this);
            level.openNextLevel(level.getCampaignLevel());
            Stage XStage = (Stage) pane.getScene().getWindow();
            XStage.close();
        }
    }

    /**
     * Makes an instance of weather and adds it to the pane
     */
    public void makeWeather() {

        Weather newWeather = game.makeWeather();
        EntityImage weather = new EntityImage(this, pane, newWeather);
        pane.getChildren().add(weather);
    }

    /**
     * Saves the game to Levels/savedGame.dat
     */
    @FXML
    public void onSaveClicked(ActionEvent e) throws IOException {
        game.save("Levels/savedGame.dat");
    }

    /**
     * Loads the game from Levels/savedGame.dat
     */
    @FXML
    public void onLoadClicked(ActionEvent e) throws IOException {
        game.load("Levels/savedGame.dat");
    }

    /**
     * removes the given entity
     * 
     * @param entity to remove
     */
    public void removeEntity(Entity entity) {
        for (Node node : pane.getChildren()) {
            if (node instanceof EntityImage) {
                if (((EntityImage) node).getEntity() == entity) {
                    if (((EntityImage) node).getEntity() instanceof Weather) {
                        pane.getChildren().remove(((EntityImage) node).getC());
                    }
                    pane.getChildren().remove(node);
                    return;
                }
            }
        }
    }

    /**
     * ends the game
     */
    @FXML
    public void onInstantGameOverClicked(ActionEvent e) {
        Button btn = (Button) e.getSource();
        game.instantGameOver((btn.getText().equals("Instant Win")) ? true : false);
    }

    /**
     * makes weather
     */
    @FXML
    public void onMakeWeatherClicked(ActionEvent e) {
        game.instantMakeWeather();
    }

    /**
     * makes more troops
     */
    @FXML
    public void onMoreTroopsClicked(ActionEvent e) {
        game.instantAddTroops();
    }

    /**
     * shows the cheat options
     */
    @FXML
    public void onCheatClicked(ActionEvent e) {
        System.out.println("You cheater :(");
        Button cheatBtn = (Button) e.getSource();
        if (cheatBtn.getText().equals("Enable Cheat Mode")) {

            cheatBtn.setText("Disable Cheat Mode");
            Button winBtn = new Button("Instant Win");

            winBtn.setOnAction(this::onInstantGameOverClicked);
            Button loseBtn = new Button("Instant Loss");
            loseBtn.setOnAction(this::onInstantGameOverClicked);

            Button troopsBtn = new Button("More Player Troops");
            troopsBtn.setOnAction(this::onMoreTroopsClicked);

            Button wthrBtn = new Button("Make Weather");
            wthrBtn.setOnAction(this::onMakeWeatherClicked);

            cheatControls.getChildren().addAll(List.of(winBtn, loseBtn, troopsBtn, wthrBtn));

        } else {
            cheatBtn.setText("Enable Cheat Mode");
            cheatControls.getChildren().clear();
        }

    }

    /**
     * displays the season
     * 
     * @param url of the season image
     */
    @FXML
    public void showSeason(String url) {
        pane.setStyle("-fx-background-image:url(" + url
                + "); -fx-background-repeat: no-repeat; -fx-background-blend-mode: darken; -fx-background-size: cover; -fx-background-position: center;");
    }

    /*************************************************************************/
    // Getters and setters

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}