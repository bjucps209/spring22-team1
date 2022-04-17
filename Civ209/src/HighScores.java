
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class HighScores {

    @FXML
    Label lblHScoreTitle;
    @FXML
    VBox VBoxScores;
    @FXML
    Slider slDifficulty;

    @FXML
    public void initialize() throws IOException {
        VBoxScores.getChildren().clear();
        lblHScoreTitle.setFont(Font.font("Times New Roman", 30));
        load();
        sortScores(scoreList);
        makeLabels();
        save(scoreList);
    }

    // An Arraylist holding all of the ScoreEntries.
    ArrayList<ScoreEntry> scoreList = new ArrayList<ScoreEntry>();

    public ArrayList<ScoreEntry> getScoreList() {
        return scoreList;

    }

    public void setScoreList(ArrayList<ScoreEntry> scoreList) {
        this.scoreList = scoreList;
    }

    public void addScoreList(ScoreEntry score) {
        scoreList.add(score);
    }

    /**
     * Sorts the scores that are currently in the ArrayList by using a stream
     * 
     * @return an ArrayList of sorted scores
     */
    public ArrayList<ScoreEntry> sortScores(ArrayList<ScoreEntry> scoreList) {

        List<ScoreEntry> sortedList = this.scoreList.stream()
                .sorted((s1, s2) -> Integer.compare(s2.getPlayerScore(), s1.getPlayerScore()))
                .collect(Collectors.toList());
        ArrayList<ScoreEntry> arraylist = new ArrayList<ScoreEntry>(sortedList);
        
        return arraylist;
    }

    public void makeLabels() {
        for (ScoreEntry score : sortScores(scoreList)) {
            // set the label value to each line value
            Label lblScore = new Label();
            String pName = score.getPlayerName();
            int pScore = score.getPlayerScore();
            lblScore.setText(pName + "                     " + pScore);
            VBoxScores.getChildren().add(lblScore);
        }
    }

    public int getDifficulty() {
        return (int) slDifficulty.getValue();
    }

    /*
     * The save method takes in the scoreList and saves it into a text file
     *
     * @param scoreList
     */
    public void save(ArrayList<ScoreEntry> scoreList) {
        try {
            FileWriter myWriter = new FileWriter("src/sampleScores.txt");
            for (int i = 0; i < scoreList.size(); i++) {
                myWriter.write(scoreList.get(i).getPlayerName() + "," +
                        scoreList.get(i).getPlayerScore() + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    /**
     * Return the scoreList and load it in to display on the screen
     * 
     * @return
     */
    public void load() {
        // throw new RuntimeException("Method not implemented");

        try {
            FileReader reader = new FileReader("src/sampleScores.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] nameScore = line.split(",");
                addScoreList((new ScoreEntry(nameScore[0], Integer.parseInt(nameScore[1]))));

            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}