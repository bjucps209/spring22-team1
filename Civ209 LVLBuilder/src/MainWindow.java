import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.City;
import model.CityType;
import model.Constants;
import model.Entity;
import model.Level;
import model.Nationality;
import model.SeasonType;

public class MainWindow {

    @FXML
    Pane pane;
    @FXML
    Label lblId;
    @FXML
    Label lblLoc;
    @FXML
    Label lblType;
    @FXML
    Button btnAnimate;
    @FXML
    Label seasons;
    @FXML
    Button loadbtn;
    City currentCity;
    @FXML
    ImageView currentImage;
    @FXML
    Circle currentCircle;
    @FXML
    VBox vbox;
    @FXML
    Button btnFastCastle;
    @FXML
    Button btnStrongCastle;

    int id = 0; // the id of the city
    Level level = new Level(); // instance of level
    LevelData leveldata = new LevelData(level); // instance of LevelData
    Boolean strongcitybuttonclicked = false; // if the strong city button is clicked
    Boolean fastcitybuttonclicked = false; // if the fast city button is clicked

    public void initialize() {
        Image strongcastle = new Image("/images/strongcastle.png");
        ImageView castle1 = new ImageView(strongcastle);
        castle1.setFitHeight(60);
        castle1.setFitWidth(60);
        castle1.setPreserveRatio(true);
        btnStrongCastle.setGraphic(castle1);
        Image fastcastle = new Image("/images/fastcastle.png");
        ImageView castle2 = new ImageView(fastcastle);
        castle2.setFitHeight(60);
        castle2.setFitWidth(60);
        castle2.setPreserveRatio(true);
        btnFastCastle.setGraphic(castle2);

    }

    /**
     * creates a player city and displays the city image using its coordinates
     */
    @FXML
    void onPlayerCastleClicked(ActionEvent e) {
        City city = level.create(Nationality.Player);
        // City city = (City) level.getCities().get(id);
        id++;
        showCity(city, "blue");
    }

    /**
     * creates a enemy city and displays the city image using its coordinates
     */
    @FXML
    void onEnemyCastleClicked(ActionEvent e) {
        City city = level.create(Nationality.Enemy);
        // City city = (City) level.getCities().get(id);
        id++;
        showCity(city, "red");
    }

    /**
     * creates a neutral city and displays the city image using its coordinates
     */
    @FXML
    void onNeutralCastleClicked(ActionEvent e) {
        City city = level.create(Nationality.Neutral);
        // City city = (City) level.getCities().get(id);
        id++;
        showCity(city, "grey");

    }

    /**
     * displays the image of a city using the city and styleclass
     * 
     * @param city, styleclass - the city to show and the styleclass
     */
    void showCity(City city, String color) {
        this.currentCity = city;
        ImageView image = new ImageView();
        if (strongcitybuttonclicked) {
            image = new ImageView("/images/strongcastle.png");
            currentCity.setType(CityType.Strong);
        } else if (fastcitybuttonclicked) {
            image = new ImageView("/images/fastcastle.png");
            currentCity.setType(CityType.Fast);
        } else {
            image = new ImageView("/images/castle.png");
            currentCity.setType(CityType.Standard); // setImage(Constants.cityImage);
        }
        image.setFitWidth(20);
        image.setFitHeight(20);
        Circle cityCircle = new Circle(10, 10, 35, Paint.valueOf("transparent")); // currentCity.getX() + 10,
                                                                                  // currentCity.getY() + 10
        cityCircle.setStroke(Paint.valueOf(color));
        image.layoutXProperty().bindBidirectional(cityCircle.layoutXProperty());
        image.layoutYProperty().bindBidirectional(cityCircle.layoutYProperty());
        image.setLayoutX(currentCity.getLocation().getX());
        image.setLayoutY(currentCity.getLocation().getY());
        image.setId(Integer.toString(currentCity.getId()));
        final ImageView finalimage = image;
        cityCircle.setId(Integer.toString(city.getId()));
        cityCircle.setOnMouseClicked((e) -> onCityClicked(e, finalimage));

        pane.getChildren().addAll(image, cityCircle);
        currentImage = image;
        currentCircle = cityCircle;
        btnFastCastle.setDisable(false);
        btnStrongCastle.setDisable(false);
        strongcitybuttonclicked = false;
        fastcitybuttonclicked = false;
        makeDraggable(cityCircle);
        display();

    }

    /**
     * makes the selected image draggable
     */
    @FXML
    void onCityClicked(MouseEvent e, ImageView image) {
        currentImage = image;
        Circle circle = (Circle) e.getSource(); // circle;
        currentCircle = circle;
        this.currentCity = level.find(Integer.parseInt(circle.getId()));
        display();

    }

    // From
    // https://stackoverflow.com/questions/17312734/how-to-make-a-draggable-node-in-javafx-2-0/46696687,
    // with modifications by S. Schaub
    public void makeDraggable(Node node) {
        final Delta dragDelta = new Delta();

        node.setOnMouseEntered(me -> node.getScene().setCursor(Cursor.HAND));
        node.setOnMouseExited(me -> node.getScene().setCursor(Cursor.DEFAULT));
        node.setOnMousePressed(me -> {
            currentCity = level.find(Integer.parseInt(node.getId()));
            dragDelta.x = me.getX();
            dragDelta.y = me.getY();
            node.getScene().setCursor(Cursor.MOVE);
        });
        node.setOnMouseDragged(me -> {
            double x = node.getLayoutX() + me.getX() - dragDelta.x;
            double y = node.getLayoutY() + me.getY() - dragDelta.y;
            if (x > 26 && x < 980 && y > 25 && y < 480) {
                currentCity = level.find(Integer.parseInt(node.getId()));
                node.setLayoutX(node.getLayoutX() + me.getX() - dragDelta.x);
                node.setLayoutY(node.getLayoutY() + me.getY() - dragDelta.y);
                cityMoved((int) node.getLayoutX(), (int) node.getLayoutY());
                display();
            }
        });
        node.setOnMouseReleased(me -> {
            node.getScene().setCursor(Cursor.HAND);

            currentCity = level.find(Integer.parseInt(node.getId()));
            currentCity.getLocation().setX((int) node.getLayoutX());
            currentCity.getLocation().setY((int) node.getLayoutY());
            cityMoved((int) node.getLayoutX(), (int) node.getLayoutY());
            display();

        });
    }

    private class Delta {
        public double x;
        public double y;
    }

    /**
     * Deletes a city and resets the labels
     */
    @FXML
    void onDeleteClicked(ActionEvent e) {
        level.delete(currentCity);
        this.id -= 1;
        lblId.setText("");
        lblLoc.setText("");
        lblType.setText("");
        pane.getChildren().remove(currentCircle);
        currentImage.setImage(null);
    }

    /**
     * Determine the background of the level through the button clicked
     * 
     * @param e
     */
    @FXML
    void onSeasonClicked(ActionEvent e) {
        Button season = (Button) e.getSource();
        if (season.getText().equals("Summer")) {
            showSeason("/images/summer.png");
            level.setSeason(SeasonType.Summer);
            seasons.setText("Summer");

        }
        if (season.getText().equals("Fall")) {
            showSeason("/images/fall.png");
            level.setSeason(SeasonType.Fall);
            seasons.setText("Fall");
        }
        if (season.getText().equals("Winter")) {
            showSeason("/images/winter.png");
            level.setSeason(SeasonType.Winter);
            seasons.setText("Winter");
        }
        if (season.getText().equals("Spring")) {
            showSeason("/images/spring.png");
            level.setSeason(SeasonType.Spring);
            seasons.setText("Spring");
        }
    }

    boolean load = false;

    /**
     * Saves the current level
     * 
     * @param e
     */
    @FXML
    void onSaveClicked(ActionEvent e) {
        try {
            leveldata.save();
            load = false;
            loadbtn.setDisable(false);
        } catch (IOException error) {
        }
    }

    /**
     * Loads the saved level in
     * 
     * @param e
     */
    @FXML
    void onLoadClicked(ActionEvent e) {
        pane.getChildren().clear();
        try {
            this.level = leveldata.load();
        } catch (IOException error) {
        }
        List<City> entityList = level.getCities();
        for (City entity : entityList) {
            City city = (City) entity;

            if (city.getType() == CityType.Strong) {
                strongcitybuttonclicked = true;
                fastcitybuttonclicked = false;
            }

            if (city.getType() == CityType.Fast) {
                fastcitybuttonclicked = true;
                strongcitybuttonclicked = false;
            }

            if (city.getNationality() == Nationality.Enemy) {
                showCity(city, "red");
            }

            if (city.getNationality() == Nationality.Player) {
                showCity(city, "blue");
            }

            if (city.getNationality() == Nationality.Neutral) {
                showCity(city, "grey");
            }
        }
        if (level.getSeason() != null) {
            SeasonType season = level.getSeason();
            if (season == SeasonType.Summer) {
                showSeason("/images/summer.png");
                seasons.setText("Summer");

            }
            if (season == SeasonType.Fall) {
                showSeason("/images/fall.png");
                seasons.setText("Fall");
            }
            if (season == SeasonType.Winter) {
                showSeason("/images/winter.png");
                seasons.setText("Winter");
            }
            if (season == SeasonType.Spring) {
                showSeason("/images/spring.png");
                seasons.setText("Spring");
            }
        }

        loadbtn.setDisable(true);
        load = true;
    }

    /**
     * Event handler that disables the castle type buttons when fast castle button
     * is clicked
     */
    @FXML
    public void onFastCastleClicked(ActionEvent e) {
        fastcitybuttonclicked = true;
        btnFastCastle.setDisable(true);
        btnStrongCastle.setDisable(true);
    }

    /**
     * Event handler that disables the castle type buttons when strong castle button
     * is clicked
     * 
     * @param e
     */
    @FXML
    public void onStrongCastleClicked(ActionEvent e) {
        strongcitybuttonclicked = true;
        btnFastCastle.setDisable(true);
        btnStrongCastle.setDisable(true);
    }

    /**
     * Shows the image background of the season in the pane.
     * 
     * @param url
     */
    @FXML
    public void showSeason(String url) {
        pane.setStyle("-fx-background-image:url(" + url
                + "); -fx-background-repeat: no-repeat; -fx-background-blend-mode: darken; -fx-background-size: cover; -fx-background-position: center;");
    }

    /**
     * Sets the current location of the city using x and y
     * 
     * @param x
     * @param y
     */
    public void cityMoved(int x, int y) {
        lblLoc.setText("(" + x + "," + y + ")");
        currentCity.getLocation().setX(x);
        currentCity.getLocation().setY(y);
    }

    /**
     * updates all of the labels
     */
    public void display() {
        if (currentCity != null) {
            cityMoved((int) currentCity.getLocation().getX(), (int) currentCity.getLocation().getY());
            lblType.setText("" + currentCity.getNationality());
            lblId.setText("" + currentCity.getId());
        }
    }

}
