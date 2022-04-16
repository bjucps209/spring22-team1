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