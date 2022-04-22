import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameWindow implements ComputerObserver {

    private Game game;
    private ArrayList<EntityImage> selectedTroops = new ArrayList<EntityImage>();
    private Coordinate upperLeft = new Coordinate();
    private Coordinate lowerRight = new Coordinate();
    private VBox dragBox = new VBox();
    private Delta dragDelta = new Delta();
    private boolean inCity = false;
    Random rand = new Random();
    // AudioClip castleTaken = new AudioClip("https://www.fesliyanstudios.com/play-mp3/6202");

    /**
     * Coordinates used in dragging image.
     */
    private class Delta {
        public double x;
        public double y;
    }

    @FXML
    Pane pane;

    @FXML
    VBox vbox;

    @FXML
    HBox controls;

    @FXML
    Label scoreLabel = new Label();

    @FXML
    Label lblSize;

    @FXML
    Slider slider;

    @FXML
    ImageView play = new ImageView(Constants.pauseButton);

    @FXML
    public void initialize(String lvlname) {
        game = new Game();
        game.setEntityManager(this::removeEntity);
        game.getComputer().setObs(this);
        game.initialize(Difficulty.Easy, lvlname);
        if (game.getSeason() == SeasonType.Summer) {
            showSeason("/images/summer.png");
        }
        if (game.getSeason() == SeasonType.Fall) {
            showSeason("/images/fall.png");
        }
        if (game.getSeason() == SeasonType.Winter) {
            showSeason("/images/winter.png");
        }
        if (game.getSeason() == SeasonType.Spring) {
            showSeason("/images/spring.png"); 
        }

        for (Entity entity : game.getEntityList()) {
            new EntityImage(this, pane, entity);
            if (entity instanceof Troop) {
                ((Troop) entity).setTroopDelete(this::onTroopDelete);
            }
        }
        lblSize.textProperty().bind(
                Bindings.createStringBinding(() -> String.valueOf((int) slider.getValue()), slider.valueProperty()));
        scoreLabel.setLayoutX(15);
        scoreLabel.setLayoutY(15);
        scoreLabel.textProperty().bind(SimpleStringProperty.stringExpression(game.scoreProperty()));
        play.setOnMousePressed(e ->  {
            if (play.getUserData() == "play") {
                play.setImage(Constants.pauseButtonPressed);
            } else {
                play.setImage(Constants.playButtonPressed);
            }
        });
        play.setOnMouseReleased(e -> onPlayClicked());
        play.setUserData("play");
        play.setFitWidth(50);
        play.setPreserveRatio(true);
        play.setLayoutX((play.getFitWidth() / 2));
        play.setLayoutY(-50 + (play.getFitHeight() / 2));
        pane.getChildren().addAll(List.of(scoreLabel, play));
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
        });
        // Prevent mouse clicks on img from propagating to the pane and
        // resulting in creation of a new image
        pane.setOnMouseClicked(me -> me.consume());
    }

    public void onMakeWeather() {
        Weather weather = game.makeWeather();
        EntityImage entityImage = new EntityImage(this, pane, weather);
    }

    public void deSelect() {
        for (Node oldNodes : pane.getChildren()) {
            if (oldNodes.getStyleClass().contains("selected")) {
                oldNodes.getStyleClass().remove("selected");
            }
        }
        selectedTroops.clear();
        game.deSelect();
    }

    public void onSelected(Circle node, MouseEvent e, City city) {
        // do a left click, check to see if it's a player city.
        if (e.getButton() == MouseButton.PRIMARY && node.getStroke() == Paint.valueOf("blue")) {
            deSelect();
            game.setSelectedCity(city);
            node.getStyleClass().add("selected");
        }
    }

    public void sendTroopsFromCity(City selectedCity, Coordinate destination) {
        game.sendTroopsFromCity(selectedCity, destination, slider.getValue()).stream().forEach(t -> {
            EntityImage circle = new EntityImage(this, pane, t);
            circle.setUserData(t);
            t.setTroopDelete(this::onTroopDelete);
        });
    }

    public void sendTroopsFromGround(ArrayList<Troop> troops, Coordinate destination) {
        game.sendTroopsFromGround(troops, destination).stream().forEach(t -> t.setTroopDelete(this::onTroopDelete));
        ;
    }

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

    public void onTroopDelete(Troop troop) {
        for (Node node : pane.getChildren()) {
            if (node instanceof EntityImage && ((EntityImage) node).getEntity() == troop) {
                pane.getChildren().remove(node);
                break;
            }
        }
        game.deleteTroop(troop);
    }

    public void renderTroops(ArrayList<Troop> troops) {
        for (Troop troop : troops) {
            EntityImage circle = new EntityImage(this, pane, troop);
            circle.setUserData(troop);
            troop.setTroopDelete(this::onTroopDelete);
        }
    }

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

    public void makeWeather() {

        Weather newWeather = game.makeWeather();
        EntityImage weather = new EntityImage(this, pane, newWeather);
        pane.getChildren().add(weather);
    }

    @FXML
    public void onSaveClicked(ActionEvent e) throws IOException {
        game.save("Levels/savedGame.dat");
    }

    @FXML
    public void onLoadClicked(ActionEvent e) throws IOException {
        game.load("Levels/savedGame.dat");
    }

    public void removeEntity(Entity entity) {
        for (Node node : pane.getChildren()) {
            if (node instanceof EntityImage) {
                if (((EntityImage) node).getUserData() == entity) {
                    pane.getChildren().remove(node);
                    return;
                }
            }
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @FXML 
    public void showSeason(String url) {
        pane.setStyle("-fx-background-image:url(" + url + "); -fx-background-repeat: no-repeat; -fx-background-blend-mode: darken; -fx-background-size: cover; -fx-background-position: center;");
    } 
}
