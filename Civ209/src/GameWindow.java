import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.City;
import model.CityType;
import model.ComputerObserver;
import model.Constants;
import model.Coordinate;
import model.DestinationType;
import model.Difficulty;
import model.Entity;
import model.Game;
import model.Nationality;
import model.Troop;

import java.io.IOException;
import java.util.ArrayList;

public class GameWindow {

    private Game game;
    private ArrayList<EntityImage> selectedTroops = new ArrayList<EntityImage>();
    private Coordinate upperLeft = new Coordinate();
    private Coordinate lowerRight = new Coordinate();
    private VBox dragBox = new VBox();
    private Delta dragDelta = new Delta();
    private boolean inCity = false;

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
    public void initialize(String lvlname) {
        game = new Game();
        game.initialize(Difficulty.Easy, lvlname);
        for (Entity entity : game.getEntityList()) {
            new EntityImage(this, pane, entity);
        }
        lblSize.textProperty().bind(
                Bindings.createStringBinding(() -> String.valueOf((int) slider.getValue()), slider.valueProperty()));
        scoreLabel.setLayoutX(15);
        scoreLabel.setLayoutY(15);
        scoreLabel.textProperty().bind(SimpleStringProperty.stringExpression(game.scoreProperty()));
        pane.getChildren().add(scoreLabel);
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
            } else if (me.getButton() == MouseButton.PRIMARY && game.checkInCity(new Coordinate(me.getX(), me.getY()))) {
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
                        .add((EntityImage) pane.getChildren().filtered(e -> e.getUserData() == t).toArray()[0]));
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
            if (node.getUserData() == troop) {
                pane.getChildren().remove(node);
                break;
            }
        }
        game.deleteTroop(troop);
    }

    @FXML
    public void onSaveClicked(ActionEvent e) throws IOException {
        game.save("Levels/savedGame.dat");
    }

    @FXML
    public void onLoadClicked(ActionEvent e) throws IOException {
        game.load("Levels/savedGame.dat");
    }
}