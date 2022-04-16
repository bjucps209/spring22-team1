import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.City;
import model.CityType;
import model.Constants;
import model.Coordinate;
import model.DestinationType;
import model.Difficulty;
import model.Entity;
import model.Game;
import model.Troop;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GameWindow {

    private Game game;
    private City selectedCity;
    private ArrayList<EntityImage> selectedTroops = new ArrayList<>();
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
    public void initialize(String lvlname) {
        game = new Game();
        game.initialize(Difficulty.Easy, lvlname);
        for (Entity entity : game.getEntityList()) {
            new EntityImage(this, pane, entity);
        }
        scoreLabel.setLayoutX(15);
        scoreLabel.setLayoutY(15);
        scoreLabel.textProperty().bind(SimpleStringProperty.stringExpression(game.scoreProperty()));
        pane.getChildren().add(scoreLabel);
        pane.setOnMousePressed(me -> {
            if (!checkInCity(me) && me.getButton() == MouseButton.PRIMARY) {
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
            } else if (me.getButton() == MouseButton.PRIMARY && checkInCity(me)) {
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
                pane.getChildren().remove(dragBox);
                for (Entity entity : game.getEntityList()) {
                    if (entity instanceof Troop) {
                        Troop troop = (Troop) entity;
                        Coordinate location = troop.getLocation();
                        if (location.getX() >= upperLeft.getX() && location.getY() >= upperLeft.getY()
                                && location.getX() <= lowerRight.getX() && location.getY() <= lowerRight.getY()) {
                            for (Node node : pane.getChildren()) {
                                if (node instanceof EntityImage) {
                                    EntityImage entityNode = (EntityImage) node;
                                    if (entityNode.getEntity() == troop) {
                                        entityNode.getStyleClass().add("selected");
                                        selectedTroops.add(entityNode);
                                        troop.setSelected(true);
                                    }
                                }
                            }
                        }
                    }
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
        if (selectedCity != null) {
            selectedCity.setSelected(false);
        }
        selectedCity = null;
        for (EntityImage node : selectedTroops) {
            Troop troop = (Troop) node.getEntity();
            troop.setSelected(false);
        }
        selectedTroops = new ArrayList<>();
    }

    public void onSelected(Circle node, MouseEvent e, City city) {
        // do a left click, check to see if it's a player city.
        if (e.getButton() == MouseButton.PRIMARY && node.getStroke() == Paint.valueOf("blue")) {
            deSelect();
            selectedCity = city;
            node.getStyleClass().add("selected");
        }
    }

    public boolean checkInCity(MouseEvent e) {
        boolean pointInCircle = false;
        for (Entity entity : game.getEntityList()) {
            if (entity instanceof City) {
                City cityEntity = (City) entity;
                if (Math.pow(e.getX() - cityEntity.getLocation().getX(), 2) + Math
                        .pow(e.getY() - cityEntity.getLocation().getY(), 2) <= Math.pow(Constants.cityRadius, 2)) {
                    pointInCircle = true;
                    break;
                }
            }
        }
        return pointInCircle;
    }

    public Coordinate checkInCity(MouseEvent e, boolean returnCity) {
        for (Entity entity : game.getEntityList()) {
            if (entity instanceof City) {
                City cityEntity = (City) entity;
                if (Math.pow(e.getX() - cityEntity.getLocation().getX(), 2) + Math
                        .pow(e.getY() - cityEntity.getLocation().getY(), 2) <= Math.pow(Constants.cityRadius, 2)) {
                    return cityEntity.getLocation();
                }
            }
        }
        return null;
    }

    public void deployTroops(MouseEvent e) {
        Coordinate destination = new Coordinate(e.getX(), e.getY());
        boolean pointInCircle = checkInCity(e);
        Coordinate cityCenter = checkInCity(e, true);
        if (e.getButton() == MouseButton.SECONDARY) {
            if (selectedCity != null) {
                if (pointInCircle) {
                    ArrayList<Troop> troops = selectedCity.sendTroops(50.0, destination, selectedCity.getType(),
                            DestinationType.City);
                    for (Troop troop : troops) {
                        EntityImage circle = new EntityImage(this, pane, troop);
                        circle.setUserData(troop);
                        troop.setDestination(cityCenter);
                        troop.setTroopDelete(this::onTroopDelete);
                    }
                    game.getEntityList().addAll(troops);
                } else {
                    ArrayList<Troop> troops = selectedCity.sendTroops(50.0, destination, selectedCity.getType(),
                            DestinationType.Coordinate);
                    moveTroopToField(troops, destination);
                    for (Troop troop : troops) {
                        EntityImage circle = new EntityImage(this, pane, troop);
                        circle.setUserData(troop);
                    }
                    game.getEntityList().addAll(troops);
                }
            } else if (selectedTroops.size() != 0) {
                if (pointInCircle) {
                    for (EntityImage entity : selectedTroops) {
                        Troop troop = (Troop) entity.getEntity();
                        troop.setDestination(cityCenter);
                        troop.setSpeed(troop.getTroopType() == CityType.Fast ? Constants.fastTroopSpeed
                                : Constants.standardTroopSpeed);
                        troop.setHeading(troop.figureHeading(cityCenter));
                        troop.setTroopDelete(this::onTroopDelete);
                        troop.setDestinationType(DestinationType.City);
                    }
                } else {
                    ArrayList<Troop> troops = new ArrayList<>();
                    for (EntityImage entity : selectedTroops) {
                        troops.add((Troop) entity.getEntity());
                        moveTroopToField(troops, destination);
                    }
                }
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
        game.getDeleteEntityList().add(troop);
    }

    public void moveTroopToField(ArrayList<Troop> troops, Coordinate destination) {
        int numTroops = troops.size();
        int ring = 0;
        int curTroop = 0;
        while (numTroops != 0) {
            for (int i = 0; i < Math.max(ring * 6, 1); i++) {
                if (numTroops == 0) {
                    break;
                } else {
                    numTroops--;
                    Troop troop = troops.get(curTroop);
                    double changeInHeading = (ring * 6) == 0 ? 0 : Math.toRadians((360.0 / (ring * 6.0)) * i);
                    troop.setDestination(
                            new Coordinate(
                                    Math.max(Math.min(destination.getX() + (ring * Constants.troopRingRadius) * Math.cos(changeInHeading),
                                            Constants.windowWidth), 0),
                                    Math.max(Math.min(destination.getY() + (ring * Constants.troopRingRadius) * Math.sin(changeInHeading),
                                            Constants.windowHeight), 0)));
                    troop.setHeading(troop.figureHeading(troop.getDestination()));
                    troop.setSpeed(troop.getTroopType() == CityType.Fast ? Constants.fastTroopSpeed
                            : Constants.standardTroopSpeed);
                    troop.setDestinationType(DestinationType.Coordinate);
                    curTroop++;
                }
            }
            ring++;
        }
    }

    @FXML
    public void onSaveClicked(ActionEvent e) throws IOException {
        game.save();
    }

    @FXML
    public void onLoadClicked(ActionEvent e) throws IOException {
        game.load("Levels/savedGame.dat");
    }
}