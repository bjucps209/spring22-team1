package model;

import java.io.DataOutputStream;

public class City extends Entity {
    private int population;
    private double incrementRate;
    private Nationality nationality;
    private boolean selected = false;
    private double fireRate;

    @Override
    public void update() {
        // TODO Auto-generated method stub
        super.update();
    }

    public void sendTroops() {
        /**
         * TODO: write psuedocode
         */
    }

    public void fireProjectile() {
        /**
         * TODO: write psuedocode
         */
    }

    
    @Override
    public void serialize(DataOutputStream wr) {
        //TODO: Finish serialization
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