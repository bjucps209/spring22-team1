//-----------------------------------------------------------
//File:   Entity.java
//Desc:   This program architects a standered entity
//-----------------------------------------------------------
package model;

import java.io.DataOutputStream;

public class Entity {
    private Coordinate location;
    private int turnCount;

    /**
     * updates image of object. worked by all objects that inherit from entity
     */
    public void update() {
        /**
         * call observer update image position
         */
    }

    /**
     *  packages an object and writes it in file according to serialization pattern
     * @param wr data writer used to write object to binary file
     */
    public void serialize(DataOutputStream wr) {
        /**
         * saves entity in file according to serialization pattern
         */
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public Entity(Coordinate location, int turnCount) {
        this.location = location;
        this.turnCount = turnCount;
    }

}
