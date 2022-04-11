//-----------------------------------------------------------
//File:   Troop.java
//Desc:   This program creates troop type objects and handels movement
//-----------------------------------------------------------
package model;

import java.io.DataOutputStream;
import java.util.function.Predicate;

public class Troop extends MobileEntity {
    private int health;
    private Nationality nationality;
    private boolean selected;
    private onTroopDeleteInterface troopDelete;

    public Troop(Coordinate location, int turnCount, double speed, double heading, Coordinate destination, int health,
            Nationality nationality, boolean selected) {
        super(location, turnCount, speed, heading, destination);
        this.health = health;
        this.nationality = nationality;
        this.selected = selected;
    }

    /**
     * checks for if reached destination or hit another troop and then updates the position and image
     */
    @Override
    public void update() {
        /**
         * check collision
         * check if reached destination
         */
        // double dist = Math.sqrt(Math.pow((getLocation().getX() - (getLocation().getX() + getSpeed() * Math.cos(getHeading() * Math.PI / 180))), 2.0) + Math.pow((getLocation().getY() - (getLocation().getY() + getSpeed() * Math.sin(getHeading() * Math.PI / 180))), 2.0));
        double distToDest = Math.sqrt(Math.pow((getDestination().getX() - (getLocation().getX() + getSpeed() * Math.cos(getHeading() * Math.PI / 180))), 2.0) + Math.pow((getDestination().getY() - (getLocation().getY() + getSpeed() * Math.sin(getHeading() * Math.PI / 180))), 2.0));
        if (Math.abs(distToDest) < 40) { // TODO: magic number for city radius + troop radius
            troopDelete.onTroopDelete(this);
        }
        super.update();
    }

    /**
     * checks to see if hit another troop
     */
    public void collisionDetection() {
        /**
         * check if hit another enemy troop
         */
    }

    /**
     * packages the object and writes it in file according to serialization pattern
     */
    @Override
    public void serialize(DataOutputStream wr) {
        //TODO: Finish serialization
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public onTroopDeleteInterface getTroopDelete() {
        return troopDelete;
    }

    public void setTroopDelete(onTroopDeleteInterface troopDelete) {
        this.troopDelete = troopDelete;
    }
}

