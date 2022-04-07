package model;

import java.util.ArrayList;
import java.io.File;

public class HighScores {
    ArrayList<ScoreEntry> scoreList = new ArrayList<ScoreEntry>();


    /**
     * 
     * Create classes to manage a list of high scores and player names.
     * I recommend one class to manage the overall list, and a separate class to
     * hold the information for each entry in the high score list (player’s name and
     * score).
     * Define instance variables needed to hold this information.
     * Also, define methods to 1) add a new high score entry to the list and put it
     * into its proper position;
     * 2) save the list of high score data to a file; and 3) load the list of high
     * scores from a file.
     * Do not implement the methods. Document the methods with well-written header
     * comments.
     * Write unit tests for the methods.
     */

    public ArrayList<ScoreEntry> getScoreList() {
        throw new RuntimeException("Not implemented");

        // return scoreList;
    }

    public void setScoreList(ArrayList<ScoreEntry> scoreList) {
        throw new RuntimeException("Not implemented");

        // this.scoreList = scoreList;
    }

    public void addScoreList(ScoreEntry score) {
        throw new RuntimeException("Not implemented");

        // scoreList.add(score);
    }

    /**
     * Sorts the scores that are currently in the ArrayList by comparing the scores
     * to each other and moving the entries around.
     * 
     * @return
     */
    public ArrayList<ScoreEntry> sortScores() {
        throw new RuntimeException("Not implemented");

        // do some calculations to see if the score is higher than the ones currently in the list
        // loop through and check each score.
        // if the score is greater than the one -1 positions in front, move it to (currentPos-1).
        // if it is not, keep it in its position.
        // return scoreList;

    }

    public void save(ArrayList<ScoreEntry> scoreList) {
        // save the current text file

    }

    public ArrayList<ScoreEntry> load() {
        throw new RuntimeException("Method not implemented");
        // return the scorelist and display it on the screen.
    }
}