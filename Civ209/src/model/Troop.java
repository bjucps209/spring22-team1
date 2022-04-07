package model;

import java.io.DataOutputStream;

public class Troop extends MobileEntity {
    private int health;
    private Nationality nationality;
    private boolean selected;

    public Troop(Coordinate location, int turnCount, double speed, double heading, Coordinate destination, int health,
            Nationality nationality, boolean selected) {
        super(location, turnCount, speed, heading, destination);
        this.health = health;
        this.nationality = nationality;
        this.selected = selected;
    }

    @Override
    public void update() {
        /**
         * check collision
         * check if reached destination
         */
        super.update();
    }

    public void collisionDetection() {
        /**
         * check if hit another enemy troop
         */
    }

    @Override
    public void serialize(DataOutputStream wr) {
        //TODO: Finish serialization
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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
