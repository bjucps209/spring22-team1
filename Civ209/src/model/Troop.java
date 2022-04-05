package model;

public class Troop extends MobileEntity {
    private int health;
    private Nationality nationality;

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
