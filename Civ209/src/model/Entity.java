package model;

import java.io.DataOutputStream;

public class Entity {
    private Coordinate location;
    private EntityObserver observer;
    private int turnCount;


    public void update() {
        /**
         * call observer update image position
         */
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }

    public EntityObserver getObserver() {
        return observer;
    }

    public void setObserver(EntityObserver observer) {
        this.observer = observer;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public void serialize(DataOutputStream wr) {
        
    }

    public Entity(Coordinate location, int turnCount) {
        this.location = location;
        this.turnCount = turnCount;
    }

}
