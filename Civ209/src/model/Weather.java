package model;

public class Weather extends MobileEntity {
    private WeatherType type;

    @Override
    public void update() {
        // TODO Auto-generated method stub
        super.update();
    }

    public WeatherType getType() {
        return type;
    }

    public void setType(WeatherType type) {
        this.type = type;
    }
}
