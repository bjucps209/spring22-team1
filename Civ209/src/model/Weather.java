package model;

import java.io.DataOutputStream;

public class Weather extends MobileEntity {
    private WeatherType type;

    public Weather(Coordinate location, int turnCount, double speed, double heading,
            Coordinate destination, WeatherType type) {
        super(location, turnCount, speed, heading, destination);
        this.type = type;
    }

    /**
     * checks for if completed requirements for type and checks to see if troop in range
     */
    @Override
    public void update() {
        /**
         * check if troops in range, if so do thing
         */
        super.update();
    }

    /**
     * packages the object and writes it in file according to serialization pattern
     */
    @Override
    public void serialize(DataOutputStream wr) {
        //TODO: Finish serialization
    }

    public WeatherType getType() {
        return type;
    }

    public void setType(WeatherType type) {
        this.type = type;
    }

}
