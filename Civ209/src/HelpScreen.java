//-----------------------------------------------------------
//File:   HelpScreen.java
//Desc:   File contains logic for help screen.
//-----------------------------------------------------------

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class HelpScreen {
    @FXML
    Label lblHelpTitle;

    public void initialize() {
        lblHelpTitle.setFont(Font.font("Impact", 30));
    }
}