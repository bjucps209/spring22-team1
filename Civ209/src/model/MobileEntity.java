package model;

public class MobileEntity extends Entity {
    private double speed;
    private double heading;
    private Coordinate destination;

    @Override
    public void update() {
        // TODO Auto-generated method stub
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
