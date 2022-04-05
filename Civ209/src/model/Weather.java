package model;

import java.io.DataOutputStream;

public class Weather extends MobileEntity {
    private WeatherType type;

    public Weather(Coordinate location, int turnCount, double speed, double heading,
            Coordinate destination, WeatherType type) {
        super(location, turnCount, speed, heading, destination);
        this.type = type;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        super.update();
    }

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
