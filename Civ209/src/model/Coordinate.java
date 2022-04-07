//-----------------------------------------------------------
//File:   Coordinate.java
//Desc:   This program creates a coordinate object that symbolizes 
//        a point in the fourth quadrant of the cartesian plane
//-----------------------------------------------------------
package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Coordinate {
    private double x;
    private double y;

    public Coordinate() {
        x = 0;
        y = 0;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "COORDINATE: X is " + x + ", and Y is " + y + ".";
    }

    /**
     * returns true if this and c are equal
     * @param c coordinate to compare to
     * @return true if coordinates are equal, false otherwise
     */
    public boolean isEqual(Coordinate c) {
        return (c.getX() == x && c.getY() == y);
    }

    /**
     * @return int return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = round(x, 3);

    }

    /**
     * @return int return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = round(y, 3);
    }

    /**
     * rounds up to number of places passed in
     * @param value the value to round
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
// made a change. cause i can