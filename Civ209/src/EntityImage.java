import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import model.*;

public class EntityImage extends ImageView implements CityObserver {
    private Entity entity;
    private Entity destination = null;
    private Nationality nationality;
    private GameWindow parent;
    private Circle cityCircle;
    private Label cityPop;

    // private static final Image projectileImage = null;

    // private static final Image droughtImage = null;

    // private static final Image floodImage = null;

    // private static final Image stormImage = new Image(
    // "https://commons.wikimedia.org/wiki/File:Light_Rain_Cloud_by_Sara.png");

    // private static final Image lightningImage = new Image(
    // "https://commons.wikimedia.org/wiki/File:Icons8_flat_flash_on.svg");

    public EntityImage(GameWindow parent, Pane pane, Entity entity) {
        this.entity = entity;
        this.parent = parent;

        if (entity instanceof City) {
            City cityEntity = (City) entity;
            cityEntity.setObs(this);
            this.nationality = cityEntity.getNationality();
            this.entity = cityEntity;
            Coordinate cityLocation = cityEntity.getLocation();
            if (cityEntity.getType() == CityType.Strong) {
                this.setImage(Constants.strongCity);
                this.setFitHeight(40);
                this.setFitWidth(40);
            } else if (cityEntity.getType() == CityType.Fast) {
                this.setImage(Constants.fastCity);
                this.setFitHeight(40);
                this.setFitWidth(40);
            } else {
                this.setImage(Constants.cityImage);
                this.setFitHeight(40);
                this.setFitWidth(40);
            }
            this.setLayoutX(cityLocation.getX() - this.getFitWidth() / 2);
            this.setLayoutY(cityLocation.getY() - this.getFitHeight() / 2);
            cityCircle = new Circle(cityLocation.getX(), cityLocation.getY(), Constants.cityRadius,
                    Paint.valueOf("transparent"));
            cityCircle.setStroke(Paint.valueOf((cityEntity.getNationality() == Nationality.Enemy) ? "red"
                    : (cityEntity.getNationality() == Nationality.Player) ? "blue" : "grey"));
            cityCircle.setOnMouseClicked(e -> parent.onSelected(cityCircle, e, cityEntity));
            cityPop = new Label();
            cityPop.textProperty().bind(cityEntity.populationProperty().asString());
            cityPop.setLayoutX(cityLocation.getX() - 5 - Constants.cityImage.getWidth() / 1.5);
            cityPop.setLayoutY(cityLocation.getY() - 5 - Constants.cityImage.getHeight() / 1.5);
            pane.getChildren().addAll(List.of(this, cityPop, cityCircle));

        } else if (entity instanceof Troop) {
            Troop troopEntity = (Troop) entity;
            this.entity = troopEntity;
            Coordinate troopLocation = troopEntity.getLocation();
            Image troopImage;
            if (troopEntity.getNationality() == Nationality.Enemy) {
                if (troopEntity.getTroopType() == CityType.Fast) {
                    troopImage = Constants.enemyFast;
                } else if (troopEntity.getTroopType() == CityType.Strong) {
                    troopImage = Constants.enemyStrong;
                } else {
                    troopImage = Constants.enemyImage;
                }
            } else {
                if (troopEntity.getTroopType() == CityType.Fast) {
                    troopImage = Constants.playerFast;
                } else if (troopEntity.getTroopType() == CityType.Strong) {
                    troopImage = Constants.playerStrong;
                } else {
                    troopImage = Constants.playerImage;
                }
            }
            this.setImage(troopImage);
            this.setFitWidth(Constants.troopRadius * 2);
            this.setFitHeight(Constants.troopRadius * 2);
            this.layoutXProperty().bind(troopLocation.xProperty().subtract(this.getFitWidth() / 2));
            this.layoutYProperty().bind(troopLocation.yProperty().subtract(this.getFitHeight() / 2));
            pane.getChildren().add(this);

        } else if (entity instanceof Weather) {
            Weather weatherEntity = (Weather) entity;
            this.entity = weatherEntity;
            Coordinate weatherLocation = weatherEntity.getLocation();
            WeatherType weatherType = weatherEntity.getType();
            Image weatherImage = (weatherType == WeatherType.Blizzard) ? Constants.blizzardImage
                    : (weatherType == WeatherType.Flood) ? Constants.floodImage : Constants.lightningImage;
            this.setImage(weatherImage);
            this.setFitWidth(50);
            this.setFitHeight(50);
            this.xProperty().bind(weatherLocation.xProperty().subtract(this.getFitWidth()/2));
            this.yProperty().bind(weatherLocation.yProperty().subtract(this.getFitHeight()/2));
            Circle c = new Circle(weatherLocation.getX(), weatherLocation.getY(), Constants.weatherRadius,
                    Paint.valueOf("transparent"));
            c.setStroke(Paint.valueOf("grey"));
            c.centerXProperty().bind(this.xProperty().add(this.getFitWidth()/2));
            c.centerYProperty().bind(this.yProperty().add(this.getFitHeight()/2));
            pane.getChildren().addAll(List.of(this, c));
        }

        else if (entity instanceof Projectile) {
            Projectile projectileEntity = (Projectile) entity;
            Line line = new Line(projectileEntity.getLocation().getX(), projectileEntity.getLocation().getY(),
                    projectileEntity.getDestination().getX(), projectileEntity.getDestination().getY());
            pane.getChildren().addAll(List.of(this, line));
        }

    }

    /**
     * Method updates the location of the Entity based off of the model.
     */
    public void update() {
        if (entity instanceof City) {
            City city = (City) entity;
            // System.out.println("Trying to show change in city");
            // System.out.println("Current nationality: " + nationality + " cityObjectNat: "
            // + city.getNationality());
            if (city.getNationality() != nationality) {
                // System.out.println("Nationality test works");
                this.nationality = city.getNationality();
                cityCircle.setStroke(Paint.valueOf((city.getNationality() == Nationality.Enemy) ? "red"
                        : (city.getNationality() == Nationality.Player) ? "blue" : "grey"));
                cityCircle.setOnMouseClicked(e -> parent.onSelected(cityCircle, e, city));
            }
        }
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
