package model;

public class Computer {
    private Difficulty difficulty;
    private int turnCount;

    public void executeAction(Game game) {
        /**
         * TODO: write psuedocode
         */
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }
}
