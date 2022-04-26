//-----------------------------------------------------------
//File:   ScoreEntry.java
//Desc:   File holds basic unit for high scores.
//-----------------------------------------------------------

public class ScoreEntry {
    private String playerName; // the player's name
    private int playerScore; // the player's score

    public int getPlayerScore() {

        return playerScore;
    }

    public String getPlayerName() {

        return playerName;
    }

    public void setPlayerName(String playerName) {

        this.playerName = playerName;
    }

    public void setPlayerScore(int playerScore) {

        this.playerScore = playerScore;
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
