//-----------------------------------------------------------
//File:   GameOverObserver.java
//Desc:   File provides interface for model to notify view
// that game is over.
//-----------------------------------------------------------

package model;

public interface GameOverObserver {
    public void recognizeGameOver(String msg, int score);
}
