//-----------------------------------------------------------
//File:   MobileEntity.java
//Desc:   This program architects a more specilized type entity
//-----------------------------------------------------------
package model;

public class MobileEntity extends Entity {
    private double speed;
    private double heading;
    private Coordinate destination;


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
        /**
         * update position based on speed and heading
         */
        super.update();
    }

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
