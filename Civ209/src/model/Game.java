//-----------------------------------------------------------
//File:   Game.java
//Desc:   This program instantiates an image and handles game state
//-----------------------------------------------------------
package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

import java.io.*;
import java.util.*;

public class Game {
    private Timeline timer;
    private ArrayList<Entity> entityList = new ArrayList<>();
    private String fileName;
    private double gameSpeed;
    private IntegerProperty scoreProperty = new SimpleIntegerProperty();
    private Computer computer;
    private int numPlayerCitiesLeft;
    private SeasonType season;
    private ArrayList<Entity> deleteEntityList = new ArrayList<>();

    /**
     * instantiates game from lvl with a computer of level difficulty
     * 
     * @param difficulty difficulty of computer
     * @param lvlName    id of level played to load in from binary file
     */
    public void initialize(Difficulty difficulty, String lvlName) {
        switch (difficulty) {
            case Easy:
                this.computer = new EasyComputer();
                break;
            case Medium:
                this.computer = new MediumComputer();
                break;
            case Hard:
                this.computer = new HardComputer();
                break;
        }
        try {
            load(lvlName);
        } catch (IOException e) {
            try {
                // Removed Civ209
                // TODO check to make sure this works
                load("Civ209/Levels/DemoLevel.dat");
            } catch (IOException xe) {
                System.out.println("fatalError! " + xe);
                System.exit(1);
            }
        }
        startTimer();
    }

    /**
     * selects troops of type nationality inside box constructed by coord1, coord2,
     * so that they can be told to go to a destination
     * 
     * @param coord1      top left corner of box
     * @param coord2      bottom right corner of box
     * @param nationality nationality of troops to select
     */
    public void selectTroops(Coordinate coord1, Coordinate coord2, Nationality nationality) {
        /**
         * iterate through troops and select troops based on nationality and if in box
         * created by x, y coordinate
         */
    }

    /**
     * checks numCitiesLeft and score to see if game should be over
     */
    public void gameEnd() {
        /**
         * checks numCitiesLeft and score to see if game should be over
         */
    }

    /**
     * loads in a game under the id lvlName
     * 
     * @param lvlName id for what to load in
     * @throws IOException in case file not there
     */
    public void load(String lvlName) throws IOException {
        try (DataInputStream rd = new DataInputStream(new FileInputStream(lvlName))) {
            if (rd.readUTF().equals("Civilization209")) {

                entityList.clear();

                setScore(rd.readInt());
                char s = rd.readChar();

                this.season = s == 'W' ? SeasonType.Winter
                        : s == 'F' ? SeasonType.Fall : s == 'S' ? SeasonType.Summer : SeasonType.Spring;
                this.numPlayerCitiesLeft = rd.readInt();

                this.gameSpeed = rd.readDouble();
                int size = rd.readInt();

                for (int i = 0; i < size; i++) {

                    Entity entity;
                    String entityType = rd.readUTF();
                    Coordinate location = new Coordinate(rd.readDouble(), rd.readDouble());
                    int turnCount = rd.readInt();

                    if (entityType.equals("City")) {

                        int population = rd.readInt();
                        IntegerProperty popProperty = new SimpleIntegerProperty(population);
                        double incrementRate = rd.readDouble();
                        char nation = rd.readChar();
                        Nationality nationality = nation == 'P' ? Nationality.Player
                                : nation == 'E' ? Nationality.Enemy : Nationality.Neutral;
                        boolean selected = rd.readBoolean();
                        double fireRate = rd.readDouble();
                        char cityT = rd.readChar();
                        CityType cityType = cityT == 'S' ? CityType.Standard
                                : cityT == 'F' ? CityType.Fast : CityType.Strong;
                        entity = new City(location, turnCount, popProperty, incrementRate, nationality, selected,
                                fireRate, cityType);
                    } else if (entityType.equals("Troop")) {

                        Coordinate destination = new Coordinate(rd.readDouble(), rd.readDouble());
                        double speed = rd.readDouble();
                        double heading = rd.readDouble();
                        int health = rd.readInt();
                        char nation = rd.readChar();
                        Boolean selected = rd.readBoolean();
                        Nationality nationality = nation == 'P' ? Nationality.Player
                                : nation == 'E' ? Nationality.Enemy : Nationality.Neutral;
                        char dChar = rd.readChar();
                        DestinationType destinationType = dChar == 'C' ? DestinationType.City : DestinationType.Coordiante;
                        entity = new Troop(location, turnCount, speed, heading, destination, health, nationality,
                                selected, destinationType);

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

    public void update() {
        setScore(getScore() - 1);
        for (Entity entity : deleteEntityList) {
            entityList.remove(entity);
        }
        deleteEntityList.clear();
        for (Entity entity : entityList) {
            entity.update();
        }
    }

    /**
     * stops timer and saves all objects to saved game portion of game file
     * 
     * @throws IOException in case file not there
     */
    public void save() throws IOException {
        /**
         * stop timer
         */
        try (DataOutputStream wr = new DataOutputStream(new FileOutputStream("savedGame.dat"))) {
            wr.writeUTF("Civilization209");
            wr.writeInt(getScore());
            wr.writeInt(entityList.size());
            for (Entity entity : entityList) {
                entity.serialize(wr);
            }
        }
    }

    public void startTimer() {
        if (timer == null) {
            timer = new Timeline(new KeyFrame(Duration.millis(200), e -> update()));
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();
        } else {
            timer.play();
        }
    }

    public void stopTimer() {
        timer.stop();
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
        return scoreProperty.get();
    }

    public void setScore(int score) {
        this.scoreProperty.set(score);
    }

    public IntegerProperty scoreProperty() {
        return scoreProperty;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public int getNumPlayerCitiesLeft() {
        return numPlayerCitiesLeft;
    }

    public void setNumPlayerCitiesLeft(int numPlayerCitiesLeft) {
        this.numPlayerCitiesLeft = numPlayerCitiesLeft;
    }

    public SeasonType getSeason() {
        return season;
    }

    public void setSeason(SeasonType season) {
        this.season = season;
    }

    public ArrayList<Entity> getDeleteEntityList() {
        return deleteEntityList;
    }

    public void setDeleteEntityList(ArrayList<Entity> deleteEntityList) {
        this.deleteEntityList = deleteEntityList;
    }

}
