package HighScores;

import java.util.ArrayList;

public class HighScores {
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

        // do some calculations to see if the score is higher than the ones currently in
        // the list
        // loop through and check each score.
        // if the score is greater than the one -1 positions in front, move it to
        // (currentPos-1).
        // if it is not, keep it in its position.
        // return scoreList;

    }

    /**
     * The save method takes in the scoreList and saves it into a text file
     * 
     * @param scoreList
     */
    public void save(ArrayList<ScoreEntry> scoreList) {
        throw new RuntimeException("Method not implemented");

    }

    /**
     * Return the scoreList and load it in to display on the screen
     * 
     * @return
     */
    public ArrayList<ScoreEntry> load() {
        throw new RuntimeException("Method not implemented");
    }
}