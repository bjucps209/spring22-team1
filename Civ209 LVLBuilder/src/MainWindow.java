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
import model.Level;
import model.Nationality;


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
        currentCity = level.create(Nationality.PLAYER);  
        City city = level.getCities().get(id);
        id++; 
        showCity(city, "playerbutton"); 
    }

    
    /**
    * creates a enemy city and displays the city image using its coordinates
    */
    @FXML
    void onEnemyCastleClicked(ActionEvent e) {
        currentCity = level.create(Nationality.PLAYER);  
        City city = level.getCities().get(id);
        id++; 
        showCity(city, "enemybutton"); 
    }

     /**
    * creates a neutral city and displays the city image using its coordinates
    */
    @FXML
    void onNeutralCastleClicked(ActionEvent e) {
        currentCity = level.create(Nationality.PLAYER);  
        City city = level.getCities().get(id);
        id++; 
        showCity(city, "neutralbutton"); 

    }
    
    /**
     * displays the image of a city using the city and styleclass
      * @param critter, styleclass - the city to show and the styleclass
     */ 
    void showCity(City city, String styleclass) {
        ImageView image = new ImageView("images/castle.png"); 
        image.setLayoutX(city.getX()); 
        image.setLayoutY(city.getY()); 
        image.setId(Integer.toString(city.getId())); 
        image.getStyleClass().add(styleclass);
        image.setOnMouseClicked(this::onCityClicked);
        pane.getChildren().add(image);    
    }

    /**
    * puts a red highlight on the selected critter
    * makes the selected image draggable 
    */
    @FXML
    void onCityClicked(MouseEvent e) {
        if (currentImage != null) {
            lastImage = currentImage; 
            lastImage.getStyleClass().remove("current"); 
        }
        currentImage = (ImageView) e.getSource();
        currentImage.getStyleClass().add("current");
        currentCity = level.find(Integer.parseInt(currentImage.getId())); 
        display();
        
        makeDraggable(currentImage);
    }


   // From https://stackoverflow.com/questions/17312734/how-to-make-a-draggable-node-in-javafx-2-0/46696687,
    // with modifications by S. Schaub
    public void makeDraggable(Node node) {
        final Delta dragDelta = new Delta();

        node.setOnMouseEntered(me -> node.getScene().setCursor(Cursor.HAND) );
        node.setOnMouseExited(me -> node.getScene().setCursor(Cursor.DEFAULT) );
        node.setOnMousePressed(me -> {
            dragDelta.x = me.getX();
            dragDelta.y = me.getY();
            node.getScene().setCursor(Cursor.MOVE);
        });
        node.setOnMouseDragged(me -> {
            node.setLayoutX(node.getLayoutX() + me.getX()- dragDelta.x);
            node.setLayoutY(node.getLayoutY() + me.getY() - dragDelta.y);
        });
        node.setOnMouseReleased(me -> { 
            node.getScene().setCursor(Cursor.HAND);

            currentCity.setX((int) node.getLayoutX()); 
            currentCity.setY((int) node.getLayoutY());
            cityMoved((int) node.getLayoutX(), (int) node.getLayoutY());
         } );

        // Prevent mouse clicks on img from propagating to the pane and
        // resulting in creation of a new image
        node.setOnMouseClicked(me -> me.consume());
    }

    private class Delta {
        public double x;
        public double y;
    }

    /**
    * deletes a critter and resets the labels
    */
    @FXML 
    void onDeleteClicked(ActionEvent e) {
        int id = currentCity.getId(); 
        ImageView image = (ImageView) pane.getChildren().get(id - 1); 
        level.delete(id); 
        lblId.setText(""); 
        lblLoc.setText("");
        lblType.setText("");
        image.setImage(null);
    }

    // @FXML
    // void onSeasonClicked(ActionEvent e) {
    //     Button season = (Button) e.getSource(); 
    //     if (season.getText().equals("Summer")) {
    //         showSeason("summerbackground");
    //     }
    //     else if (season.getText().equals("Fall")) {
    //         showSeason("fallbackground");
    //     }
    //     else if (season.getText().equals("Winter")) {
    //         showSeason("winterbackground");
    //     }
    //     else {
    //         showSeason("springbackground");
    //     }
    // }

    @FXML
    public void showSeason(String styleclass) {
        vbox.getStyleClass().add(styleclass);
    }


    public void cityMoved(int x, int y) {
        lblLoc.setText("(" +  x + ","+ y + ")"); 
    }

    /**
    * updates all of the labels 
    */
    public void display() {
        if (currentCity != null) {
            Object[] info = currentCity.getInformation();
            lblId.setText(Integer.toString((int) info[0]));
            cityMoved((int) info[1], (int) info[2]);
            if (info[3] == Nationality.ENEMY) {
                lblType.setText("Enemy");
            }
            else if (info[3] == Nationality.PLAYER) {
                lblType.setText("Player");
            } else {
                lblType.setText("Neutral");
            }
        }
    }
}

