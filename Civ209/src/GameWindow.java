import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.converter.IntegerStringConverter;
import model.City;
import model.Coordinate;
import model.Difficulty;
import model.Entity;
import model.Game;
import model.Nationality;
import model.Troop;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

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
            createEntity(entity);
        }
        // We'll have to set this to the required resolultion
        mainVbox.setPrefSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 100,
                Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 100);
        scoreLabel.setLayoutX(15);
        scoreLabel.setLayoutY(15);
        scoreLabel.textProperty().bind(SimpleStringProperty.stringExpression(game.scoreProperty()));
        mainPane.getChildren().add(scoreLabel);
        mainVbox.getChildren().add(mainPane);
    }

    @FXML
    public void createEntity(Entity entity) {
        new EntityImage(this, mainPane, entity);
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
            if (selectedCity != null) {
                ArrayList<Troop> troops = selectedCity.sendTroops(50.0, city.getLocation(), selectedCity.getType());
                for (Troop troop : troops) {
                    EntityImage circle = new EntityImage(this, mainPane, troop);
                    circle.layoutXProperty().bind(troop.getLocation().xProperty());
                    circle.layoutYProperty().bind(troop.getLocation().yProperty());
                    circle.setUserData(troop);
                    troop.setTroopDelete(this::onTroopDelete);

                }

                game.getEntityList().addAll(troops);
            }
        }
    }

    public void troopCreate(Troop troop) {

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