//-----------------------------------------------------------
//File:   Troop.java
//Desc:   This program creates troop type objects and handels movement
//-----------------------------------------------------------
package model;

import java.io.DataOutputStream;
import java.io.IOException;

public class Troop extends MobileEntity {
    private int health;
    private Nationality nationality;
    private boolean selected;
    private onTroopDeleteInterface troopDelete;
    private DestinationType destinationType;
    private CityType troopType;

    public Troop(Coordinate location, int turnCount, double speed, double heading, Coordinate destination, int health,
            Nationality nationality, boolean selected, DestinationType destinationType, CityType troopType) {
        super(location, turnCount, speed, heading, destination);
        this.health = health;
        this.nationality = nationality;
        this.selected = selected;
        this.destinationType = destinationType;
        this.troopType = troopType;
    }

    /**
     * checks for if reached destination or hit another troop and then updates the
     * position and image
     */
    @Override
    public void update() {
        /**
         * check collision
         * check if reached destination
         */
        if (destinationType == DestinationType.City) {
            double distToDest = Math.sqrt(Math
                    .pow((getDestination().getX()
                            - (getLocation().getX() + getSpeed() * Math.cos(getHeading() * Math.PI / 180))), 2.0)
                    + Math.pow(
                            (getDestination().getY()
                                    - (getLocation().getY() + getSpeed() * Math.sin(getHeading() * Math.PI / 180))),
                            2.0));
            if (Math.abs(distToDest) < Constants.cityRadius + Constants.troopRadius) {
                troopDelete.onTroopDelete(this);
            }
        } else {
            double dist = Math.sqrt(Math
                    .pow((getLocation().getX()
                            - (getLocation().getX() + getSpeed() * Math.cos(getHeading() * Math.PI / 180))), 2.0)
                    + Math.pow(
                            (getLocation().getY()
                                    - (getLocation().getY() + getSpeed() * Math.sin(getHeading() * Math.PI / 180))),
                            2.0));
            double distToDest = Math.sqrt(Math
                    .pow((getDestination().getX()
                            - (getLocation().getX() + getSpeed() * Math.cos(getHeading() * Math.PI / 180))), 2.0)
                    + Math.pow(
                            (getDestination().getY()
                                    - (getLocation().getY() + getSpeed() * Math.sin(getHeading() * Math.PI / 180))),
                            2.0));
            if (dist > distToDest) {
                setSpeed(0);
            }
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
    public void serialize(DataOutputStream wr) throws IOException {
        wr.writeUTF("Troop");
        wr.writeDouble(this.getLocation().getX());
        wr.writeDouble(this.getLocation().getY());
        wr.writeInt(this.getTurnCount());
        wr.writeDouble(this.getSpeed());
        wr.writeDouble(this.getHeading());
        wr.writeInt(health);
        wr.writeChar((nationality == Nationality.Player) ? 'P' : nationality == Nationality.Enemy ? 'E' : 'N');
        wr.writeBoolean(selected);
        wr.writeChar((destinationType == DestinationType.City ? 'i' : 'o'));
        wr.writeChar((troopType == CityType.Fast) ? 'F' : troopType == CityType.Strong ? 'S' : 's');
        wr.writeDouble(this.getDestination().getX());
        wr.writeDouble(this.getDestination().getY());

    }

    public double figureHeading(Coordinate destination) {
        if (destination.getX() - getLocation().getX() != 0) {
            if (destination.getX() - getLocation().getX() < 0) {
                return 180 + (Math.toDegrees(Math.atan(
                        (getLocation().getY() - destination.getY()) / (getLocation().getX() - destination.getX()))));
            } else {
                return (Math.toDegrees(Math.atan(
                        (getLocation().getY() - destination.getY()) / (getLocation().getX() - destination.getX()))));
            }
        } else {
            return (destination.getY() - getLocation().getY() > 0) ? 90 : 270;
        }
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

    public DestinationType getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(DestinationType destinationType) {
        this.destinationType = destinationType;
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

    public CityType getTroopType() {
        return troopType;
    }

    public void setTroopType(CityType troopType) {
        this.troopType = troopType;
    }
}
