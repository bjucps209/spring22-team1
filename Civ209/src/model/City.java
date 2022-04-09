//-----------------------------------------------------------
//File:   City.java
//Desc:   This program creates a city and updates it
//        when the game loop calls
//-----------------------------------------------------------
package model;

import java.io.DataOutputStream;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class City extends Entity {
    private IntegerProperty populationProperty = new SimpleIntegerProperty();
    private double incrementRate;
    private Nationality nationality;
    private boolean selected = false;
    private double fireRate;
    private CityType type;

    public City(Coordinate location, int turnCount, IntegerProperty population, double incrementRate, Nationality nationality,
            boolean selected, double fireRate, CityType type) {
        super(location, turnCount);
        this.populationProperty = population;
        this.incrementRate = incrementRate;
        this.nationality = nationality;
        this.selected = selected;
        this.fireRate = fireRate;
        this.type = type;
    }

    /**
     * updates the population and image. fires projectile on appropriate ticks.
     */
    @Override
    public void update() {
        /**
         * increment population
         * fire projectile if cooldown done
         */
        super.update();
    }

    /**
     * generates percentage of population troops going to destination
     * @param percentage percentage of city population to send out
     * @param destination destination of the generated troops
     * @param type type of troop to be sending out
     */
    public void sendTroops(double percentage, Coordinate destination, CityType type) {
        /**
         * generate troops based on population and percentage of troops to send
         */
    }

    /**
     * fires a projectile from city at closest enemy if enemy in range and city population not 0
     */
    public void fireProjectile() {
        /**
         * check if any enemies in range. If so, fire projectiles
         */
    }

    /**
     * packages the object and writes it in file according to serialization pattern
     */
    @Override
    public void serialize(DataOutputStream wr) {
    }

    public int getPopulation() {
        return populationProperty.get();
    }

    public void setPopulation(int population) {
        this.populationProperty.set(population);
    }

    public IntegerProperty populationProperty(){
        return populationProperty;
    }

    public double getIncrementRate() {
        return incrementRate;
    }

    public void setIncrementRate(double incrementRate) {
        this.incrementRate = incrementRate;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
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