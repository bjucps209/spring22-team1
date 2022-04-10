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

import org.junit.Test.None;

public class GameWindow {
    private static final Image cityImage = new Image(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7d/BSicon_Castle.svg/32px-BSicon_Castle.svg.png");
    @FXML
    VBox mainVbox;
    @FXML
    Pane mainPane = new Pane();
    @FXML
    Label scoreLabel = new Label();

    private Game game;

    @FXML
    public void initialize() {
        game = new Game();
        game.initialize(Difficulty.Easy, "");
        for (Entity entity : game.getEntityList()) {
            createEntity(entity);
        }
        mainVbox.setPrefSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        scoreLabel.setLayoutX(0);
        scoreLabel.setLayoutY(0);
        scoreLabel.textProperty().bind(SimpleStringProperty.stringExpression(game.scoreProperty()));
        mainPane.getChildren().add(scoreLabel);
        mainVbox.getChildren().add(mainPane);
    }

    @FXML
    public void createEntity(Entity entity) {
        if (entity instanceof City) {
            City cityEntity = (City) entity;
            Coordinate cityLocation = cityEntity.getLocation();
            ImageView image = new ImageView(cityImage);
            SimpleDoubleProperty cityX = new SimpleDoubleProperty();
            SimpleDoubleProperty cityY = new SimpleDoubleProperty();
            cityX.bind(cityLocation.xProperty());
            cityY.bind(cityLocation.yProperty());
            image.setLayoutX(cityX.get());
            image.setLayoutY(cityY.get());
            Circle cityCircle = new Circle(cityX.get() + 16, cityY.get() + 20, 25, Paint.valueOf("transparent"));
            cityCircle.setStroke(Paint.valueOf((cityEntity.getNationality() == Nationality.Enemy) ? "red"
                    : (cityEntity.getNationality() == Nationality.Player) ? "blue" : "grey"));
            cityCircle.setOnMouseClicked(e -> onSelected(cityCircle, e, cityEntity));
            Label cityPop = new Label();
            cityPop.textProperty().bind(cityEntity.populationProperty().asString());
            cityPop.setLayoutX(cityX.get());
            cityPop.setLayoutY(cityY.get());
            mainPane.getChildren().addAll(List.of(image, cityPop, cityCircle));
        }
    }

    public void onSelected(Circle node, MouseEvent e, City city) {
        if (e.getButton() == MouseButton.PRIMARY && node.getStroke() == Paint.valueOf("blue")) {
            for (Node oldNodes : mainPane.getChildren()) {
                if (oldNodes.getStyleClass().contains("selected")) {
                    oldNodes.getStyleClass().remove("selected");
                    break;
                }
            }
            city.setSelected(true);
            node.getStyleClass().add("selected");
        } else if (e.getButton() == MouseButton.SECONDARY) {
            City cityEntity = null;
            for (Entity entity : game.getEntityList()) {
                if (entity instanceof City) {
                    cityEntity = (City) entity;
                    if (cityEntity.isSelected()) {
                        break;
                    }
                }
            }
            if (cityEntity != null) {
                ArrayList<Troop> troops = cityEntity.sendTroops(50.0, city.getLocation(), cityEntity.getType());
                for (Troop troop : troops) {
                    Circle circle = new Circle();
                    circle.layoutXProperty().bind(troop.getLocation().xProperty());
                    circle.layoutYProperty().bind(troop.getLocation().yProperty());
                    circle.setFill(Paint.valueOf("transparent"));
                    circle.setStroke(Paint.valueOf((troop.getNationality() == Nationality.Enemy) ? "red"
                            : (troop.getNationality() == Nationality.Neutral) ? "grey" : "blue"));
                    circle.setRadius(5);
                    mainPane.getChildren().add(circle);
                }
                game.getEntityList().addAll(troops);
            }
        }
    }
}