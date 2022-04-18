import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class AboutScreen {
    @FXML Label lblCoders;
    @FXML Label lblAboutTitle;

    public void initialize(){
        lblCoders.setFont(Font.font("Times New Roman", 20));
        lblAboutTitle.setFont(Font.font("Impact", 30));
    }
}
