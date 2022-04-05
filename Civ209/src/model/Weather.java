package model;

public class Weather extends MobileEntity {
    private WeatherType type;

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
