import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.*;

public class EntityImage extends ImageView {
    private Entity entity;
    private Entity destination = null;

    // private static final Image projectileImage = null;


    // private static final Image droughtImage = null;

    // private static final Image floodImage = null;

    // private static final Image stormImage = new Image(
    // "https://commons.wikimedia.org/wiki/File:Light_Rain_Cloud_by_Sara.png");

    // private static final Image lightningImage = new Image(
    // "https://commons.wikimedia.org/wiki/File:Icons8_flat_flash_on.svg");

    public EntityImage(GameWindow parent, Pane pane, Entity entity) {
        this.entity = entity;

        if (entity instanceof City) {
            City cityEntity = (City) entity;
            this.entity = cityEntity;
            this.setImage(Constants.cityImage);
            Coordinate cityLocation = cityEntity.getLocation();
            this.setLayoutX(cityLocation.getX() - Constants.cityImage.getWidth() / 2);
            this.setLayoutY(cityLocation.getY() - Constants.cityImage.getHeight() / 2);
            Circle cityCircle = new Circle(cityLocation.getX(), cityLocation.getY(), Constants.cityRadius, Paint.valueOf("transparent"));
            cityCircle.setStroke(Paint.valueOf((cityEntity.getNationality() == Nationality.Enemy) ? "red"
                    : (cityEntity.getNationality() == Nationality.Player) ? "blue" : "grey"));
            cityCircle.setOnMouseClicked(e -> parent.onSelected(cityCircle, e, cityEntity));
            Label cityPop = new Label();
            cityPop.textProperty().bind(cityEntity.populationProperty().asString());
            cityPop.setLayoutX(cityLocation.getX() - Constants.cityImage.getWidth() / 1.5);
            cityPop.setLayoutY(cityLocation.getY() - Constants.cityImage.getHeight() / 1.5);
            pane.getChildren().addAll(List.of(this, cityPop, cityCircle));

        } else if (entity instanceof Troop) {
            Troop troopEntity = (Troop) entity;
            this.entity = troopEntity;
            Coordinate troopLocation = troopEntity.getLocation();
            Image troopImage = troopEntity.getNationality() == Nationality.Enemy ? Constants.enemyImage : Constants.playerImage;
            this.setImage(troopImage);
            this.setFitWidth(Constants.troopRadius * 2);
            this.setFitHeight(Constants.troopRadius * 2);
            this.layoutXProperty().bind(troopLocation.xProperty().subtract(this.getFitWidth() / 2));
            this.layoutYProperty().bind(troopLocation.yProperty().subtract(this.getFitHeight() / 2));
            pane.getChildren().add(this);
        }
        // else if (entity instanceof Projectile) {
        // Projectile projectileEntity = (Projectile) entity;
        // Coordinate projectileLocation = projectileEntity.getLocation();
        // SimpleDoubleProperty projectileX = new SimpleDoubleProperty();
        // SimpleDoubleProperty projectileY = new SimpleDoubleProperty();
        // projectileX.bind(projectileLocation.xProperty());
        // projectileY.bind(projectileLocation.yProperty());
        // this.setImage(projectileImage);
        // this.setLayoutX(projectileX.get() - projectileImage.getWidth() / 2);
        // this.setLayoutY(projectileY.get() - projectileImage.getHeight() / 2);
        // pane.getChildren().add(this);
        // } else {
        // Weather weatherEntity = (Weather) entity;
        // Coordinate weatherLocation = weatherEntity.getLocation();
        // SimpleDoubleProperty weatherX = new SimpleDoubleProperty();
        // SimpleDoubleProperty weatherY = new SimpleDoubleProperty();
        // weatherX.bind(weatherLocation.xProperty());
        // weatherY.bind(weatherLocation.yProperty());
        // WeatherType type = weatherEntity.getType();
        // Image weatherImage = type == WeatherType.Blizzard ? blizzardImage
        // : type == WeatherType.Drought ? droughtImage
        // : type == WeatherType.Flood ? floodImage : lightningImage;
        // this.setImage(weatherImage);
        // this.setLayoutX(weatherX.get() - weatherImage.getWidth() / 2);
        // this.setLayoutY(weatherY.get() - weatherImage.getHeight() / 2);
        // pane.getChildren().add(this);
        // }
    }

    /**
     * Method updates the location of the Entity based off of the model.
     */
    public void update() {
        setLayoutX(entity.getLocation().getX());
        setLayoutY(entity.getLocation().getY());
    }

    public Entity getDestination() {
        return destination;
    }

    public void setDestination(Entity destination) {
        this.destination = destination;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
