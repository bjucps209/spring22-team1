//-----------------------------------------------------------
//File:   City.java
//Desc:   This program creates a city and updates it
//        when the game loop calls
//-----------------------------------------------------------
package model;

import java.io.DataOutputStream;

public class City extends Entity {
    private int population;
    private double incrementRate;
    private Nationality nationality;
    private boolean selected = false;
    private double fireRate;

    public City(Coordinate location, int turnCount, int population, double incrementRate, Nationality nationality,
            boolean selected, double fireRate) {
        super(location, turnCount);
        this.population = population;
        this.incrementRate = incrementRate;
        this.nationality = nationality;
        this.selected = selected;
        this.fireRate = fireRate;
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
     */
    public void sendTroops(double percentage, Coordinate destination) {
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
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
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
}