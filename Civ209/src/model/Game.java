//-----------------------------------------------------------
//File:   Game.java
//Desc:   This program instantiates an image and handles game state
//-----------------------------------------------------------
package model;

import javafx.animation.Timeline;
import java.io.*;
import java.util.*;

public class Game {
    private Timeline timer;
    private ArrayList<Entity> entityList;
    private String fileName;
    private double gameSpeed;
    private int score;
    private Computer computer;

    /**
     * instantiates game from lvl with a computer of level difficulty
     * @param difficulty difficulty of computer
     * @param lvlName id of level played to load in from binary file
     */
    public void initialize(Difficulty difficulty, String lvlName) {
        /**
         * create Computer of type difficulty
         * 
         * load in values of lvlName
         * 
         * start Timer
         */
    }

    /**
     * selects troops of type nationality inside box constructed by coord1, coord2, so that they can be told to go to a destination
     * @param coord1 top left corner of box
     * @param coord2 bottom right corner of box
     * @param nationality nationality of troops to select
     */
    public void selectTroops(Coordinate coord1, Coordinate coord2, Nationality nationality) {
        /**
         * iterate through troops and select troops based on nationality and if in box created by x, y coordinate
         */
    }

    /**
     * loads in a game under the id lvlName
     * @param lvlName id for what to laod in
     * @throws IOException in case file not there
     */
    public void load(String lvlName) throws IOException {
        try (DataInputStream rd = new DataInputStream(new FileInputStream(lvlName))) {
            if (rd.readUTF().equals("Civilization209")) {
                entityList.clear();
                this.score = rd.readInt();
                int size = rd.readInt();

                for (int i = 0; i < size; i++) {
                    Entity entity;
                    String entityType = rd.readUTF();
                    Coordinate location = new Coordinate(rd.readDouble(), rd.readDouble());
                    int turnCount = rd.readInt();
                    if (entityType.equals("City")) {
                        int population = rd.readInt();
                        double incrementRate = rd.readDouble();
                        char nation = rd.readChar();
                        Nationality nationality = nation == 'P' ? Nationality.Player
                                : nation == 'E' ? Nationality.Enemy : Nationality.Nuetral;
                        boolean selected = rd.readBoolean();
                        double fireRate = rd.readDouble();
                        entity = new City(location, turnCount, population, incrementRate, nationality, selected,
                                fireRate);
                    } else if (entityType.equals("Troop")) {
                        Coordinate destination = new Coordinate(rd.readDouble(), rd.readDouble());
                        double speed = rd.readDouble();
                        double heading = rd.readDouble();
                        int health = rd.readInt();
                        char nation = rd.readChar();
                        Boolean selected = rd.readBoolean();
                        Nationality nationality = nation == 'P' ? Nationality.Player
                                : nation == 'E' ? Nationality.Enemy : Nationality.Nuetral;
                        entity = new Troop(location, turnCount, speed, heading, destination, health, nationality, selected);
                    } else if (entityType.equals("Projectile")) {
                        Coordinate destination = new Coordinate(rd.readDouble(), rd.readDouble());
                        double speed = rd.readDouble();
                        double heading = rd.readDouble();
                        int damage = rd.readInt();
                        entity = new Projectile(location, turnCount, speed, heading, destination, damage);
                    } else {
                        Coordinate destination = new Coordinate(rd.readDouble(), rd.readDouble());
                        double speed = rd.readDouble();
                        double heading = rd.readDouble();
                        char type = rd.readChar();
                        WeatherType weatherType = type == 'L' ? WeatherType.LightningStorm
                                : type == 'B' ? WeatherType.Blizzard
                                        : type == 'F' ? WeatherType.Flood : WeatherType.Drought;

                        entity = new Weather(location, turnCount, speed, heading, destination, weatherType);
                    }
                    entityList.add(entity);
                }
            }
        }
    }

    /**
     * stops timer and saves all objects to saved game portion of game file
     * @throws IOException in case file not there
     */
    public void save() throws IOException {
        /**
         * stop timer
         */
        try (DataOutputStream wr = new DataOutputStream(new FileOutputStream("savedGame.dat"))) {
            wr.writeUTF("Civilization209");
            wr.writeInt(score);
            wr.writeInt(entityList.size());
            for (Entity entity : entityList) {
                entity.serialize(wr);
            }
        }
    }

    public void startTimer() {
        /**
         * timer.start()
         */
    }

    public void stopTimer() {
        /**
         * timer.stop()
         */
    }

    public Timeline getTimer() {
        return timer;
    }

    public void setTimer(Timeline timer) {
        this.timer = timer;
    }

    public ArrayList<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(ArrayList<Entity> entityList) {
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

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

}
