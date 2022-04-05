package model;

import java.io.DataOutputStream;

public class Troop extends MobileEntity {
    private int health;
    private Nationality nationality;

    public Troop(Coordinate location, int turnCount, double speed, double heading,
            Coordinate destination, int health, Nationality nationality) {
        super(location, turnCount, speed, heading, destination);
        this.health = health;
        this.nationality = nationality;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        super.update();
    }

    public void collisionDetection() {
        /**
         * TODO: write psuedocode
         */
    }

    @Override
    public void serialize(DataOutputStream wr) {
        //TODO: Finish serialization
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }


}
