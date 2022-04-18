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

public class GameWindow implements ComputerObserver {

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
        game.setUpComputer(this);
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
            if (!checkInCity(new Coordinate(me.getX(), me.getY())) && me.getButton() == MouseButton.PRIMARY) {
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
            } else if (me.getButton() == MouseButton.PRIMARY && checkInCity(new Coordinate(me.getX(), me.getY()))) {
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

    public boolean checkInCity(Coordinate e) {
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

    public Coordinate checkInCity(Coordinate e, boolean returnCity) {
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

    // TODO: Rhys, don't know if these two methods should be in game. They may be getting passed into the computer, and i'm not sure how that's happening.
    public void sendTroopsFromCity(City selectedCity, Coordinate destination) {
        ArrayList<Troop> troops = selectedCity.sendTroops(slider.getValue(), destination,
                selectedCity.getType(),
                DestinationType.City);
        for (Troop troop : troops) {
            EntityImage circle = new EntityImage(this, pane, troop);
            circle.setUserData(troop);
            troop.setDestination(destination);
            troop.setTroopDelete(this::onTroopDelete);
        }
    }

    public void sendTroopsFromGround(ArrayList<Troop> selectedTroops, Coordinate destination) {
        for (Troop troop : selectedTroops) {
            troop.setDestination(destination);
            troop.setSpeed(troop.getTroopType() == CityType.Fast ? Constants.fastTroopSpeed
                    : Constants.standardTroopSpeed);
            troop.setHeading(troop.figureHeading(destination));
            troop.setTroopDelete(this::onTroopDelete);
            troop.setDestinationType(DestinationType.City);
        }
    }

    public void deployTroops(MouseEvent e) {
        Coordinate destination = new Coordinate(e.getX(), e.getY());
        boolean pointInCircle = checkInCity(destination);
        Coordinate cityCenter = checkInCity(destination, true);
        if (e.getButton() == MouseButton.SECONDARY) {
            if (game.getSelectedCity() != null) {
                if (pointInCircle) {
                    ArrayList<Troop> troops = game.getSelectedCity().sendTroops(slider.getValue(), destination,
                            game.getSelectedCity().getType(),
                            DestinationType.City);
                    for (Troop troop : troops) {
                        EntityImage circle = new EntityImage(this, pane, troop);
                        circle.setUserData(troop);
                        troop.setDestination(cityCenter);
                        troop.setTroopDelete(this::onTroopDelete);
                    }
                    game.getEntityList().addAll(troops);
                } else {
                    ArrayList<Troop> troops = game.getSelectedCity().sendTroops(slider.getValue(), destination,
                            game.getSelectedCity().getType(),
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
                    for (Troop troop : troops) {
                        troop.setSpeed(troop.getTroopType() == CityType.Fast ? Constants.fastTroopSpeed
                                : Constants.standardTroopSpeed);
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

        Coordinate location = troop.getLocation();

        if (checkInCity(location)) {
            City city = game.getCityHit(location);
            if (city != null)
                city.recieveTroops(troop.getHealth(), troop.getNationality());
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
                                    Math.max(Math.min(
                                            destination.getX()
                                                    + (ring * Constants.troopRingRadius) * Math.cos(changeInHeading),
                                            Constants.windowWidth), 0),
                                    Math.max(Math.min(
                                            destination.getY()
                                                    + (ring * Constants.troopRingRadius) * Math.sin(changeInHeading),
                                            Constants.windowHeight), 0)));
                    troop.setHeading(troop.figureHeading(troop.getDestination()));
                    // troop.setSpeed(troop.getTroopType() == CityType.Fast ?
                    // Constants.fastTroopSpeed
                    // : Constants.standardTroopSpeed);
                    troop.setDestinationType(DestinationType.Coordinate);
                    curTroop++;
                }
            }
            ring++;
        }
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