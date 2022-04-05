package model;

import javafx.animation.Timeline;

public class Game {
    private Timeline timer;
    private Entity[] entityList;
    private String fileName;
    private double gameSpeed;
    private int score;


    public void innitialize(Difficulty difficulty, String lvlName) {
        /**
         * TODO: write psuedocode
         */
    }

    public void selectTroops(double x, double y, Nationality nationality) {
        /**
         * TODO: write psuedocode
         */
    }
    
    public void load(String fileName, String lvlName) {
        /**
         * TODO: write psuedocode
         */
    }

    public void serialize() {
        /**
         * TODO: write psuedocode
         */
    }

    public void startTimer() {
        /**
         * TODO: write psuedocode
         */
    }

    public void stopTimer() {
        /**
         * TODO: write psuedocode
         */
    }

    public Timeline getTimer() {
        return timer;
    }

    public void setTimer(Timeline timer) {
        this.timer = timer;
    }

    public Entity[] getEntityList() {
        return entityList;
    }

    public void setEntityList(Entity[] entityList) {
        this.entityList = entityList;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public double getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(double gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
