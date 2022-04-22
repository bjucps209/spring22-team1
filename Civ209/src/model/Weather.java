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
    private WeatherType type;
    Random rand = new Random();

    public Weather(Coordinate location, int turnCount, double speed, double heading,
            Coordinate destination, WeatherType type) {
        super(location, turnCount, speed, heading, destination);
        this.type = type;
    }

    /**
     * checks for if completed requirements for type and checks to see if troop in
     * range
     */
    @Override
    public void update() {
        /**
         * check if troops in range of circle radius, if so, kill a random number
         * 
         */
        super.update();
        // if troops in range of circle,
        // kill random number of troops in list.
        
    }

    public static Entity load(DataInputStream rd, String entityType) throws IOException {
        Coordinate location = new Coordinate(rd.readDouble(), rd.readDouble());
        int turnCount = rd.readInt();
        double speed = rd.readDouble();
        double heading = rd.readDouble();
        WeatherType weatherType = entityType == "L" ? WeatherType.LightningStorm
                : entityType == "B" ? WeatherType.Blizzard
                        : entityType == "F" ? WeatherType.Flood : WeatherType.Drought;
        Coordinate destination = new Coordinate(rd.readDouble(), rd.readDouble());

        return new Weather(location, turnCount, speed, heading, destination, weatherType);
    }

    /**
     * packages the object and writes it in file according to serialization pattern
     */
    @Override
    public void serialize(DataOutputStream wr) throws IOException {
        wr.writeUTF(type == WeatherType.LightningStorm ? "L"
                : type == WeatherType.Blizzard ? "B" : type == WeatherType.Drought ? "D" : "F");
        wr.writeDouble(this.getLocation().getX());
        wr.writeDouble(this.getLocation().getY());
        wr.writeInt(this.getTurnCount());
        wr.writeDouble(this.getSpeed());
        wr.writeDouble(this.getHeading());
        wr.writeDouble(this.getDestination().getX());
        wr.writeDouble(this.getDestination().getY());
    }

    public WeatherType getType() {
        return type;
    }

    public void setType(WeatherType type) {
        this.type = type;
    }

}
