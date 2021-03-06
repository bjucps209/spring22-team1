//-----------------------------------------------------------
//File:   Weather.java
//Desc:   This program creates weather events
//-----------------------------------------------------------

package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class Weather extends MobileEntity {

    // Type of weather, either Flood, Lightning, or Blizzard
    private WeatherType type;

    // Random object for random decisions.
    Random rand = new Random();

    /**
     * Makes some weather using a randomly generated location, speed, heading, and
     * type
     * 
     * @param location
     * @param turnCount
     * @param speed
     * @param heading
     * @param destination
     * @param type
     */
    public Weather(Coordinate location, int turnCount, double speed, double heading,
            Coordinate destination, WeatherType type) {
        super(location, turnCount, speed, heading, destination);
        this.type = type;
    }

    /**
     * checks for completed requirements for weather type and checks to see if troop is in range
     */
    @Override
    public void update() {
        super.update();

    }

    /**
     * Method reads in object from DataInputStream, returns weather object that it
     * built.
     * 
     * @param rd
     * @param entityType
     * @return
     * @throws IOException
     */
    public static Entity load(DataInputStream rd, String entityType) throws IOException {
        Coordinate location = new Coordinate(rd.readDouble(), rd.readDouble());
        int turnCount = rd.readInt();
        double speed = rd.readDouble();
        double heading = rd.readDouble();
        WeatherType weatherType = entityType.equals("L") ? WeatherType.LightningStorm
                : entityType.equals("B") ? WeatherType.Blizzard
                        : WeatherType.Flood;

        return new Weather(location, turnCount, speed, heading, null, weatherType);
    }

    /**
     * Packages the object and writes it in file according to serialization pattern
     */
    @Override
    public void serialize(DataOutputStream wr) throws IOException {

        wr.writeUTF(type == WeatherType.LightningStorm ? "L"
                : type == WeatherType.Blizzard ? "B" : "F");
        wr.writeDouble(this.getLocation().getX());
        wr.writeDouble(this.getLocation().getY());
        wr.writeInt(this.getTurnCount());
        wr.writeDouble(this.getSpeed());
        wr.writeDouble(this.getHeading());
    }

    /*************************************/
    // Getters and setters

    public WeatherType getType() {
        return type;
    }

    public void setType(WeatherType type) {
        this.type = type;
    }

}
