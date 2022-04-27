//-----------------------------------------------------------
//File:   AboutScreen.java
//Desc:   Logic for about screen.
//-----------------------------------------------------------

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class AboutScreen {
    @FXML Label lblCoders;
    @FXML Label lblAboutTitle;

    /**
     * Initializes the about screen
     */
    public void initialize(){
        lblCoders.setFont(Font.font("Times New Roman", 25));
        lblAboutTitle.setFont(Font.font("Impact", 40));
    }
}