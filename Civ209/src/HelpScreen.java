import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class HelpScreen {
    @FXML Label lblHowTo;
    @FXML Label lblHelpTitle;

    public void initialize(){
        lblHowTo.setFont(Font.font("Times New Roman", 20));
        lblHelpTitle.setFont(Font.font("Impact", 30));
    }
}