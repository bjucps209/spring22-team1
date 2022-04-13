
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class HighScores {

    @FXML
    Label lblHScoreTitle;
    @FXML
    VBox VBoxScores;

    @FXML
    public void initialize() throws IOException {
        lblHScoreTitle.setFont(Font.font("Times New Roman", 30));
        load();
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
     * Sorts the scores that are currently in the ArrayList by comparing the scores
     * to each other and moving the entries around.
     * 
     * @return
     */
    public ArrayList<ScoreEntry> sortScores() {
        throw new RuntimeException("Not implemented");

        // loop through and check each score.
        // do some calculations to see if the score is higher than the ones currently in
        // the list
        // if the score is greater than the one -1 positions in front, move it to
        // (currentPos-1).
        // if it is not, keep it in its position.
        // return scoreList;

        // https://www.geeksforgeeks.org/how-to-sort-an-arraylist-of-objects-by-property-in-java/
        // public static Comparator<ScoreEntry> ScoreComparator = new
        // Comparator<ScoreEntry>() {
        // public int compare(ScoreEntry s1, ScoreEntry s2) {
        // int score1 = s1.getPlayerScore();
        // int score2 = s2.getPlayerScore();
        // return score1 - score2;
        // }
        // };
    }
    // Collections.sort(scoreList, ScoreEntry.getPlayerScore);

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
                // set the label value to each line value
                Label lblScore = new Label();
                lblScore.setText(line);
                VBoxScores.getChildren().add(lblScore);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}