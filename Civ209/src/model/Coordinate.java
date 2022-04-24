//-----------------------------------------------------------
//File:   Coordinate.java
//Desc:   This program creates a coordinate object that symbolizes 
//        a point in the fourth quadrant of the cartesian plane
//-----------------------------------------------------------
package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Coordinate {
    private DoubleProperty xProperty = new SimpleDoubleProperty();
    private DoubleProperty yProperty = new SimpleDoubleProperty();

    public Coordinate() {
        setY(0);
        setX(0);
    }

    public Coordinate(int x, int y) {
        setY(y);
        setX(x);
    }

    public Coordinate(double x, double y) {
        setY(y);
        setX(x);
    }

    public Coordinate(Coordinate coord) {
        setX(coord.getX());
        setY(coord.getY());
    }

    public Coordinate figureNewCoordinate(double heading, double distance) {

        if (distance < 0) {
            distance -= Constants.cityRadius * 2;
        } else {
            distance += Constants.cityRadius * 2;
        }

        double x = (Math.cos(Math.toRadians(heading)) * distance);
        double y = Math.sin(Math.toRadians(heading)) * distance;

        if (x < 1)
            x = 1;
        else if (x > Constants.windowWidth - 1)
            x = Constants.windowWidth - 1;
        if (y < 1)
            y = 1;
        else if (y > Constants.windowHeight - 1)
            y = Constants.windowHeight - 1;

        return new Coordinate(this.getX() + x, this.getY() + y);

    }

    public boolean isNearThis(Coordinate comp) {
        double xDiff = getX() - comp.getX();
        double yDiff = getY() - comp.getY();
        return ((xDiff <= 50 && xDiff > Constants.cityRadius) || (xDiff >= -50 && xDiff <= (Constants.cityRadius * -1)))
                && ((yDiff <= 50 && yDiff > Constants.cityRadius)
                        || (yDiff >= -50 && yDiff <= (Constants.cityRadius * -1)));
    }

    public String toString() {
        return "COORDINATE: X is " + getX() + ", and Y is " + getY() + ".";
    }

    /**
     * returns true if this and c are equal
     * 
     * @param c coordinate to compare to
     * @return true if coordinates are equal, false otherwise
     */
    public boolean isEqual(Coordinate c) {
        return (c.getX() == getX() && c.getY() == getY());
    }

    /**
     * @return int return the x
     */
    public double getX() {
        return xProperty.get();
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        xProperty.set(round(x, 3));

    }

    /**
     * @return int return the y
     */
    public double getY() {
        return yProperty.get();
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        yProperty.set(round(y, 3));
    }

    public DoubleProperty xProperty() {
        return xProperty;
    }

    public DoubleProperty yProperty() {
        return yProperty;
    }

    /**
     * rounds up to number of places passed in
     * 
     * @param value  the value to round
     * @param places the number of digits to round to
     * @return the amended value
     */
    // taken from https://www.baeldung.com/java-round-decimal-number
    private static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}