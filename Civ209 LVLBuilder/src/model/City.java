//-----------------------------------------------------------
//File:   City.java
//Desc:   This program creates a city and updates it
//        when the game loop calls
//-----------------------------------------------------------
package model;

import java.util.Random;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

public class City extends Entity {
    private IntegerProperty populationProperty = new SimpleIntegerProperty();
    private double incrementRate;
    private Nationality nationality;
    private boolean selected = false;
    private double fireRate;
    private CityType type;
    private int id;
    private int x;
    private int y;
    private int turnCount = 0;

    private static int nextId;

    public City(Coordinate location, int turnCount, IntegerProperty population, double incrementRate,
            Nationality nationality,
            boolean selected, double fireRate, CityType type) {
        super(location, turnCount);
        this.populationProperty = population;
        this.incrementRate = incrementRate;
        this.nationality = nationality;
        this.selected = selected;
        this.fireRate = fireRate;
        this.type = type;
        this.id = ++nextId;
        var rand = new Random();
        this.x = rand.nextInt(750);
        this.y = rand.nextInt(450);
    }

    public static Entity load(DataInputStream rd) throws IOException {
        Coordinate location = new Coordinate(rd.readDouble(), rd.readDouble());
        int turnCount = rd.readInt();
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
        return new City(location, turnCount, popProperty, incrementRate, nationality, selected,
                fireRate, cityType);
    }

    /**
     * packages the object and writes it in file according to serialization pattern
     * 
     * @throws IOException
     */
    @Override
    public void serialize(DataOutputStream wr) throws IOException {
        // Goes through and writes all of the information necessary for a constructor.
        wr.writeUTF("City");
        wr.writeDouble(this.getLocation().getX());
        wr.writeDouble(this.getLocation().getY());
        wr.writeInt(this.getTurnCount());
        wr.writeInt(this.getPopulation());
        wr.writeDouble(incrementRate);
        wr.writeChar((nationality == Nationality.Player) ? 'P' : nationality == Nationality.Enemy ? 'E' : 'N');
        wr.writeBoolean(selected);
        wr.writeDouble(fireRate);
        wr.writeChar((type == CityType.Fast) ? 'F' : type == CityType.Strong ? 'S' : 's');
    }

    public int getPopulation() {
        return populationProperty.get();
    }

    public void setPopulation(int population) {
        this.populationProperty.set(population);
    }

    public IntegerProperty populationProperty() {
        return populationProperty;
    }

    public double getIncrementRate() {
        return incrementRate;
    }

    public void setIncrementRate(double incrementRate) {
        this.incrementRate = incrementRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Object[] getInformation() {
        Object[] items = { id, x, y, nationality };
        return items;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public double getFireRate() {
        return fireRate;
    }

    public void setFireRate(double fireRate) {
        this.fireRate = fireRate;
    }

    public CityType getType() {
        return type;
    }

    public void setType(CityType type) {
        this.type = type;
    }
}