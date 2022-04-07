package model;

public class ScoreEntry {
    private String playerName; // the player's name
    private int playerScore; // the player's score

    public int getPlayerScore() {
        throw new RuntimeException("Not implemented");

        // return playerScore;
    }

    public String getPlayerName() {
        throw new RuntimeException("Not implemented");

        // return playerName;
    }

    public void setPlayerName(String playerName) {
        throw new RuntimeException("Not implemented");

        // this.playerName = playerName;
    }

    public void setPlayerScore(int playerScore) {
        throw new RuntimeException("Not implemented");

        // this.playerScore = playerScore;
    }

    /**
     * Creates a score entry using the playerName and the playerScore that were
     * passed in
     * 
     * @param playerName
     * @param playerScore
     */
    public ScoreEntry(String playerName, int playerScore) {

        this.playerName = playerName;
        this.playerScore = playerScore;
    }
}
