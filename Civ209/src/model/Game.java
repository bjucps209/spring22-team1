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

    public void initialize(Difficulty difficulty, String lvlName) {
        /**
         * TODO: write psuedocode
         */
    }

    public void selectTroops(double x, double y, Nationality nationality) {
        /**
         * TODO: write psuedocode
         */
    }

    public void load(String lvlName) throws IOException {
        try (DataInputStream rd = new DataInputStream(new FileInputStream(lvlName))) {
            if (rd.readUTF().equals("Civilization209")) {
                entityList.clear();
                int score = rd.readInt();
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
                        Nationality nationality = nation == 'P' ? Nationality.Player
                                : nation == 'E' ? Nationality.Enemy : Nationality.Nuetral;

                        entity = new Troop(location, turnCount, speed, heading, destination, health, nationality);
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

    public void save() throws IOException {
        try (DataOutputStream wr = new DataOutputStream(new FileOutputStream("items.dat"))) {
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
