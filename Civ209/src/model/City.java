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

    @Override
    public void update() {
        /**
         * increment population
         * fire projectile if cooldown done
         */
        super.update();
    }

    public void sendTroops(double percentage, Coordinate destination) {
        /**
         * generate troops based on population and percentage of troops to send
         */
    }

    public void fireProjectile() {
        /**
         * check if any enemies in range. If so, fire projectiles
         */
    }

    
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