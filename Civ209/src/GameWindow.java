import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.City;
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

    @FXML
    public void initialize() {
        game = new Game();
        game.initialize(Difficulty.Easy, "");
        for (Entity entity : game.getEntityList()) {
            new EntityImage(this, mainPane, entity);
        }
        // We'll have to set this to the required resolultion
        mainVbox.setPrefSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 100,
                Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 100);
        scoreLabel.setLayoutX(15);
        scoreLabel.setLayoutY(15);
        scoreLabel.textProperty().bind(SimpleStringProperty.stringExpression(game.scoreProperty()));
        mainPane.getChildren().add(scoreLabel);
        mainVbox.getChildren().add(mainPane);
        mainPane.setOnMouseClicked(e -> deployTroops(e));
    }

    public void onSelected(Circle node, MouseEvent e, City city) {
        // do a left click, check to see if it's a player city.
        if (e.getButton() == MouseButton.PRIMARY && node.getStroke() == Paint.valueOf("blue")) {
            for (Node oldNodes : mainPane.getChildren()) {
                if (oldNodes.getStyleClass().contains("selected")) {
                    oldNodes.getStyleClass().remove("selected");
                    break;
                }
            }
            if (selectedCity != null) {
                selectedCity.setSelected(false);
            }
            city.setSelected(true);
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
        }
    }

    public void deployTroops(MouseEvent e) {
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
        if (pointInCircle != true && selectedCity != null && e.getButton() == MouseButton.SECONDARY) {
            Coordinate destination = new Coordinate(e.getX(), e.getY());
            ArrayList<Troop> troops = selectedCity.sendTroops(50.0, destination, selectedCity.getType(), DestinationType.Coordiante);
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
                        troop.setDestination(new Coordinate(destination.getX() + (ring * 20) * Math.cos(changeInHeading), destination.getY() + (ring * 20) * Math.sin(changeInHeading)));
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
        }
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