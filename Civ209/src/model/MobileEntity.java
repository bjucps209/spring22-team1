//-----------------------------------------------------------
//File:   MobileEntity.java
//Desc:   This program architects a more specilized type entity
//-----------------------------------------------------------

package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class MobileEntity extends Entity {
    private double speed;
    private double heading;
    private Coordinate destination;

    /**
     * Basic constructor
     * @param location - coordinate to put mobile entity at to begin
     * @param turnCount - how many times update has been called
     * @param speed - speed at which entity moves
     * @param heading - heading at which it's moving
     * @param destination - coordinate that entity is heading to.
     */
    public MobileEntity(Coordinate location, int turnCount, double speed, double heading,
            Coordinate destination) {
        super(location, turnCount);
        this.speed = speed;
        this.heading = heading;
        this.destination = destination;
    }

    /**
     * updates the position and image
     */
    @Override
    public void update() {
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(Constants.tickSpeed / 10), e -> move()));
        timer.setCycleCount(Constants.tickSpeed / 10);
        timer.play();
        super.update();
    }

    /**
     * Logic to move mobile entity.
     */
    public void move() {
        getLocation()
                .setX(getLocation().getX() + (speed / (Constants.tickSpeed / 10)) * Math.cos(heading * Math.PI / 180));
        getLocation()
                .setY(getLocation().getY() + (speed / (Constants.tickSpeed / 10)) * Math.sin(heading * Math.PI / 180));
    }

    /******************************************************/
    // Getters and setters
    
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public Coordinate getDestination() {
        return destination;
    }

    public void setDestination(Coordinate destination) {
        this.destination = destination;
    }

}
