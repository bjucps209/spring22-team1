import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.City;
import model.CityObserver;
import model.CityType;
import model.Level;
import model.Nationality;
import model.SeasonType;


public class MainWindow implements CityObserver {

    @FXML Pane pane; 
    @FXML Label lblId; 
    @FXML Label lblLoc; 
    @FXML Label lblType;
    @FXML Button btnAnimate; 
    City currentCity; 
    @FXML ImageView lastImage; 
    @FXML ImageView currentImage;  
    @FXML VBox vbox;
    int id = 0; 
    Level level = new Level(); 


    /**
    * creates a player city and displays the city image using its coordinates
    */
    @FXML
    void onPlayerCastleClicked(ActionEvent e) {
        City city = level.create(Nationality.Player);  
        //City city = (City) level.getCities().get(id);
        id++; 
        showCity(city, "playerbutton"); 
    }

    
    /**
    * creates a enemy city and displays the city image using its coordinates
    */
    @FXML
    void onEnemyCastleClicked(ActionEvent e) {
        City city = level.create(Nationality.Enemy);  
        //City city = (City) level.getCities().get(id);
        id++; 
        showCity(city, "enemybutton"); 
    }

     /**
    * creates a neutral city and displays the city image using its coordinates
    */
    @FXML
    void onNeutralCastleClicked(ActionEvent e) {
        City city = level.create(Nationality.Neutral);  
        //City city = (City) level.getCities().get(id);
        id++; 
        showCity(city, "neutralbutton"); 

    }
    
    /**
     * displays the image of a city using the city and styleclass
      * @param city, styleclass - the city to show and the styleclass
     */ 
    void showCity(City city, String styleclass) {
        ImageView image = new ImageView("images/castle.png"); 
        image.setLayoutX(city.getX()); 
        image.setLayoutY(city.getY()); 
        image.setId(Integer.toString(city.getId())); 
        image.getStyleClass().add(styleclass);
        image.setOnMouseClicked(this::onCityClicked);
        pane.getChildren().add(image);  
        this.currentCity = level.find(Integer.parseInt(image.getId())); 
        display();  
        makeDraggable(image);
    }

    /**
    * makes the selected image draggable 
    */
    @FXML
    void onCityClicked(MouseEvent e) {
        // if (currentImage != null) {
        //     lastImage = currentImage; 
        //     lastImage.getStyleClass().remove("current"); 
        // }
        currentImage = (ImageView) e.getSource();
        //currentImage.getStyleClass().add("current");
        this.currentCity = level.find(Integer.parseInt(currentImage.getId())); 
        // display();
        
        // makeDraggable(currentImage);
        
    }


   // From https://stackoverflow.com/questions/17312734/how-to-make-a-draggable-node-in-javafx-2-0/46696687,
    // with modifications by S. Schaub
    public void makeDraggable(Node node) {
        final Delta dragDelta = new Delta();

        node.setOnMouseEntered(me -> node.getScene().setCursor(Cursor.HAND));
        node.setOnMouseExited(me -> node.getScene().setCursor(Cursor.DEFAULT) );
        node.setOnMousePressed(me -> {
            dragDelta.x = me.getX();
            dragDelta.y = me.getY();
            node.getScene().setCursor(Cursor.MOVE);
        });
        node.setOnMouseDragged(me -> {
            double x = node.getLayoutX()+ me.getX()- dragDelta.x;
            double y = node.getLayoutY()+ me.getY()- dragDelta.y;
            if ( x > -1 && x < 960 && y > -1 && y < 475) {
                node.setLayoutX(node.getLayoutX() + me.getX()- dragDelta.x);
                node.setLayoutY(node.getLayoutY() + me.getY() - dragDelta.y);
                cityMoved((int) node.getLayoutX(), (int) node.getLayoutY()); 
                display(); 
            }
        });
        node.setOnMouseReleased(me -> { 
            node.getScene().setCursor(Cursor.HAND);

            currentCity.setX((int) node.getLayoutX()); 
            currentCity.setY((int) node.getLayoutY());
            cityMoved((int) node.getLayoutX(), (int) node.getLayoutY()); 
            display(); 

         } );
    }

    private class Delta {
        public double x;
        public double y;
    }

    /**
    * deletes a city and resets the labels
    */
    @FXML 
    void onDeleteClicked(ActionEvent e) {
        int id = currentCity.getId(); 
        ImageView image = (ImageView) pane.getChildren().get(id - 1);
        level.delete(id); 
        this.id -= 1;
        lblId.setText(""); 
        lblLoc.setText("");
        lblType.setText("");
        image.setImage(null);
    }

    @FXML
    void onSeasonClicked(ActionEvent e) {
        Button season = (Button) e.getSource(); 
        if (season.getText().equals("Summer")) {
            showSeason("/images/tentativesummer.png");
            level.setSeason(SeasonType.Summer);
        }
        if (season.getText().equals("Fall")) {
            showSeason("/images/tentativefall.png");
            level.setSeason(SeasonType.Fall);
        }
        if (season.getText().equals("Winter")) {
            showSeason("/images/tentativewinter.png");
            level.setSeason(SeasonType.Winter);
        }
        if (season.getText().equals("Spring")) {
            showSeason("/images/tentativespring2.png");
            level.setSeason(SeasonType.Spring);
        }
    }

    @FXML
    public void showSeason(String url) {
        pane.setStyle("-fx-background-image:url(" + url + "); -fx-background-repeat: no-repeat; -fx-background-blend-mode: darken; -fx-background-size: cover; -fx-background-position: center;");
    }


    public void cityMoved(int x, int y) {
        lblLoc.setText("(" +  x + ","+ y + ")"); 
    }


    /**
    * updates all of the labels 
    */
    public void display() {
        if (currentCity != null) {
            cityMoved(currentCity.getX(), currentCity.getY());
            lblType.setText("" + currentCity.getNationality());
            lblId.setText("" + currentCity.getId());
        }
    }
}

