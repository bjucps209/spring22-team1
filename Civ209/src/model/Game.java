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
    private Computer computer = new Computer();
    private int numPlayerCitiesLeft;
    private SeasonType season;
    private ArrayList<Entity> deleteEntityList = new ArrayList<>();
    private Difficulty difficulty;
    private int turncount = 0;
    private ArrayList<Troop> selectedTroops = new ArrayList<>();
    private City selectedCity;

    /**
     * instantiates game from lvl with a computer of level difficulty
     * 
     * @param difficulty difficulty of computer
     * @param lvlName    id of level played to load in from binary file
     */
    public void initialize(Difficulty difficulty, String lvlName) {
        this.difficulty = difficulty;

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
    public ArrayList<Troop> selectTroops(Coordinate coord1, Coordinate coord2, Nationality nationality) {
        selectedTroops.clear();
        for (Entity entity : getEntityList()) {
            if (entity instanceof Troop) {
                Troop troop = (Troop) entity;
                Coordinate location = troop.getLocation();
                if (location.getX() >= coord1.getX() && location.getY() >= coord1.getY()
                        && location.getX() <= coord2.getX() && location.getY() <= coord2.getY()) {
                    troop.setSelected(true);
                    selectedTroops.add(troop);
                }
            }
        }
        return selectedTroops;
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
                char diff = rd.readChar();
                this.difficulty = diff == 'E' ? Difficulty.Easy : diff == 'M' ? Difficulty.Medium : Difficulty.Hard;
                this.numPlayerCitiesLeft = rd.readInt();

                this.gameSpeed = rd.readDouble();
                int size = rd.readInt();

                for (int i = 0; i < size; i++) {

                    Entity entity;
                    String entityType = rd.readUTF();

                    if (entityType.equals("City"))
                        entity = City.load(rd);
                    else if (entityType.equals("Troop"))
                        entity = Troop.load(rd);
                    else if (entityType.equals("Projectile"))
                        entity = Projectile.load(rd);
                    else
                        entity = Weather.load(rd, entityType);

                    entityList.add(entity);
                }
            }
        }
    }

    public void update() {
        // Slows score decrementation
        turncount++;
        if (turncount % 3 == 0)
            setScore(getScore() - 1);

        for (Entity entity : deleteEntityList) {
            entityList.remove(entity);
        }
        deleteEntityList.clear();
        for (Entity entity : entityList) {
            entity.update();
        }
    }

    public void deSelect() {
        if (selectedCity != null) {
            selectedCity.setSelected(false);
        }
        selectedCity = null;
        for (Troop troop : selectedTroops) {
            troop.setSelected(false);
        }
        selectedTroops.clear();
    }

    /**
     * stops timer and saves all objects to saved game portion of game file
     * 
     * @throws IOException in case file not there
     */
    public void save(String lvlName) throws IOException {
        /**
         * stop timer
         */
        if (lvlName.equals(""))
            lvlName = "savedGame.dat";
        try (DataOutputStream wr = new DataOutputStream(new FileOutputStream(lvlName))) {
            wr.writeUTF("Civilization209");
            wr.writeInt(getScore());
            wr.writeChar(this.season == SeasonType.Winter ? 'W'
                    : this.season == SeasonType.Fall ? 'F' : this.season == SeasonType.Summer ? 'S' : 's');
            // this.difficulty = diff == 'E' ? Difficulty.Easy : diff == 'M' ?
            // Difficulty.Medium : Difficulty.Hard;
            wr.writeChar(this.difficulty == Difficulty.Easy ? 'E' : this.difficulty == Difficulty.Medium ? 'M' : 'H');
            wr.writeInt(numPlayerCitiesLeft);
            wr.writeDouble(gameSpeed);

            wr.writeInt(entityList.size());
            for (Entity entity : entityList) {
                entity.serialize(wr);
            }
        }
    }

    public void setUpComputer(ComputerObserver window) {
        computer.setObs(window);
    }

    public City getCityHit(Coordinate coordinate) {
        City city = null;
        for (Entity entity : getEntityList()) {
            if (entity instanceof City) {
                City cityEntity = (City) entity;
                if (Math.pow(coordinate.getX() - cityEntity.getLocation().getX(), 2) + Math
                        .pow(coordinate.getY() - cityEntity.getLocation().getY(), 2) <= Math.pow(Constants.cityRadius,
                                2)) {
                    city = cityEntity;
                    break;
                }
            }
        }
        return city;
    }

    public void moveTroopToField(ArrayList<Troop> troops, Coordinate destination) {
        int numTroops = troops.size();
        int ring = 0;
        int curTroop = 0;
        while (numTroops != 0) {
            for (int i = 0; i < Math.max(ring * 6, 1); i++) {
                if (numTroops == 0) {
                    break;
                } else {
                    numTroops--;
                    Troop troop = troops.get(curTroop);
                    double changeInHeading = (ring * 6) == 0 ? 0 : Math.toRadians((360.0 / (ring * 6.0)) * i);
                    troop.setDestination(
                            new Coordinate(
                                    Math.max(Math.min(
                                            destination.getX()
                                                    + (ring * Constants.troopRingRadius) * Math.cos(changeInHeading),
                                            Constants.windowWidth), 0),
                                    Math.max(Math.min(
                                            destination.getY()
                                                    + (ring * Constants.troopRingRadius) * Math.sin(changeInHeading),
                                            Constants.windowHeight), 0)));
                    troop.setHeading(troop.figureHeading(troop.getDestination()));
                    // troop.setSpeed(troop.getTroopType() == CityType.Fast ?
                    // Constants.fastTroopSpeed
                    // : Constants.standardTroopSpeed);
                    troop.setDestinationType(DestinationType.Coordinate);
                    curTroop++;
                }
            }
            ring++;
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

    public ArrayList<Troop> getSelectedTroops() {
        return selectedTroops;
    }

    public void setSelectedTroops(ArrayList<Troop> selectedTroops) {
        this.selectedTroops = selectedTroops;
    }

    public City getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }

}
