import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
import java.util.ArrayList;

public class GameWindow {

    @FXML
    VBox mainVbox;
    @FXML
    Pane mainPane = new Pane();
    @FXML
    Label scoreLabel = new Label();

    private Game game;
    private City selectedCity;
    private ArrayList<EntityImage> selectedTroops = new ArrayList<>();
    Coordinate upperLeft = new Coordinate();
    Coordinate lowerRight = new Coordinate();

    /**
     * Coordinates used in dragging image.
     */
    private class Delta {
        public double x;
        public double y;
    }

    @FXML
    public void initialize() {
        game = new Game();
        game.initialize(Difficulty.Easy, "");
        for (Entity entity : game.getEntityList()) {
            new EntityImage(this, mainPane, entity);
        }
        // We'll have to set this to the required resolultion
        mainVbox.setPrefSize(1025, 525);
        scoreLabel.setLayoutX(15);
        scoreLabel.setLayoutY(15);
        scoreLabel.textProperty().bind(SimpleStringProperty.stringExpression(game.scoreProperty()));
        VBox dragBox = new VBox();
        Delta dragDelta = new Delta();
        mainPane.getChildren().add(scoreLabel);
        mainVbox.getChildren().add(mainPane);
        mainPane.setOnMousePressed(me -> {
            if (!checkInCity(me) && me.getButton() == MouseButton.PRIMARY) {
                deSelect();
                dragDelta.x = me.getX();
                dragDelta.y = me.getY();
                dragBox.setLayoutX(me.getX());
                dragBox.setLayoutY(me.getY());
                dragBox.setStyle("-fx-border-color: black");
                mainPane.getChildren().add(dragBox);
                upperLeft.setX(me.getX());
                upperLeft.setY(me.getY());
            } else {
                deployTroops(me);
            }
        });
        mainPane.setOnMouseDragged(me -> {
            if (me.getX() - dragDelta.x < 0) {
                dragBox.setPrefWidth(dragDelta.x - me.getX());
                dragBox.setLayoutX(me.getX());
                upperLeft.setX(me.getX());
            } else {
                dragBox.setPrefWidth(me.getX() - dragDelta.x);
                lowerRight.setX(me.getX());
            }
            if (me.getY() - dragDelta.y < 0) {
                dragBox.setPrefHeight(dragDelta.y - me.getY());
                dragBox.setLayoutY(me.getY());
                upperLeft.setY(me.getY());
            } else {
                dragBox.setPrefHeight(me.getY() - dragDelta.y);
                lowerRight.setY(me.getY());
            }
        });
        mainPane.setOnMouseReleased(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                mainPane.getChildren().remove(dragBox);
                dragBox.setPrefSize(0, 0);
                for (Entity entity : game.getEntityList()) {
                    if (entity instanceof Troop) {
                        Troop troop = (Troop) entity;
                        Coordinate location = troop.getLocation();
                        if (location.getX() >= upperLeft.getX() && location.getY() >= upperLeft.getY()
                                && location.getX() <= lowerRight.getX() && location.getY() <= lowerRight.getY()) {
                            for (Node node : mainPane.getChildren()) {
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
        });
        // Prevent mouse clicks on img from propagating to the pane and
        // resulting in creation of a new image
        mainPane.setOnMouseClicked(me -> me.consume());
    }

    public void deSelect() {
        for (Node oldNodes : mainPane.getChildren()) {
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
    }

    public void onSelected(Circle node, MouseEvent e, City city) {
        deSelect();
        // do a left click, check to see if it's a player city.
        if (e.getButton() == MouseButton.PRIMARY && node.getStroke() == Paint.valueOf("blue")) {
            selectedCity = city;
            node.getStyleClass().add("selected");
        } else if (e.getButton() == MouseButton.SECONDARY) { // if right click
            if (selectedCity != null && selectedCity != city) {
                ArrayList<Troop> troops = selectedCity.sendTroops(50.0, city.getLocation(), selectedCity.getType(),
                        DestinationType.City);
                for (Troop troop : troops) {
                    EntityImage circle = new EntityImage(this, mainPane, troop);
                    circle.setDestination(city);
                    circle.setUserData(troop);
                    troop.setTroopDelete(this::onTroopDelete);
                }
                game.getEntityList().addAll(troops);
            }
            deSelect();
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
        if (e.getButton() == MouseButton.SECONDARY) {
            if (pointInCircle != true && selectedCity != null) {
                ArrayList<Troop> troops = selectedCity.sendTroops(50.0, destination, selectedCity.getType(),
                        DestinationType.Coordiante); // might want to fix the spelling - Izzo
                int numTroops = troops.size() - 1;
                int ring = 1;
                while (numTroops != 0) {
                    for (int i = 0; i < ring * 6; i++) {
                        if (numTroops == 0) {
                            break;
                        } else {
                            numTroops--;
                            Troop troop = troops.get(numTroops);
                            double changeInHeading = Math.toRadians((360 / (ring * 6)) * i);
                            troop.setDestination(
                                    new Coordinate(destination.getX() + (ring * 20) * Math.cos(changeInHeading),
                                            destination.getY() + (ring * 20) * Math.sin(changeInHeading)));
                            troop.setHeading(selectedCity.figureHeading(troop.getDestination()));
                        }
                    }
                    ring++;
                }

                for (Troop troop : troops) {
                    EntityImage circle = new EntityImage(this, mainPane, troop);
                    circle.setUserData(troop);
                }
                game.getEntityList().addAll(troops);
            } else if (selectedTroops.size() != 0) {
                Coordinate cityCenter = checkInCity(e, true);

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
                    for (EntityImage entity: selectedTroops) {
                        troops.add((Troop) entity.getEntity());
                    }
                    int numTroops = troops.size() - 1;
                    int ring = 1;
                    while (numTroops != 0) {
                        for (int i = 0; i < ring * 6; i++) {
                            if (numTroops == 0) {
                                break;
                            } else {
                                numTroops--;
                                Troop troop = troops.get(numTroops);
                                double changeInHeading = Math.toRadians((360 / (ring * 6)) * i);
                                troop.setDestination(
                                        new Coordinate(destination.getX() + (ring * 20) * Math.cos(changeInHeading),
                                                destination.getY() + (ring * 20) * Math.sin(changeInHeading)));
                                troop.setHeading(troop.figureHeading(troop.getDestination()));
                                troop.setSpeed(troop.getTroopType() == CityType.Fast ? Constants.fastTroopSpeed
                                : Constants.standardTroopSpeed);
                                troop.setDestinationType(DestinationType.Coordiante);
                            }
                        }
                        ring++;
                    }
                }
                selectedTroops.clear();
            }
        }
        deSelect();
    }

    public void onTroopSelected(MouseEvent e, Troop troop) {

    }

    public void onTroopDelete(Troop troop) {
        for (Node node : mainPane.getChildren()) {
            if (node.getUserData() == troop) {
                mainPane.getChildren().remove(node);
                break;
            }
        }
        game.getDeleteEntityList().add(troop);
    }

}