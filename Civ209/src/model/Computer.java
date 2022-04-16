//-----------------------------------------------------------
//File:   Computer.java
//Desc:   This program creates standered computer object
//-----------------------------------------------------------
package model;

import java.util.ArrayList;

public class Computer {
    private Difficulty difficulty;
    private int turnCount;
    
    /**
     * creates and executes an action depending on level of difficulty
     * @param game the game state
     */
    public void executeAction(Game game) {
        /**
         * Depending on level of difficulty, creates action and executes said action
         */
        ArrayList<Entity> entities = game.getEntityList();
        ArrayList<City> enemyCities;
        
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
