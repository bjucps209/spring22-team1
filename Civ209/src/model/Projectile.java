package model;

import java.io.DataOutput;
import java.io.DataOutputStream;

public class Projectile extends MobileEntity {
    private int damage;

    public Projectile(Coordinate location, int turnCount, double speed, double heading,
            Coordinate destination, int damage) {
        super(location, turnCount, speed, heading, destination);
        this.damage = damage;
    }

    @Override
    public void update() {
        /**
         * check collision detection
         */
        super.update();
    }

    public void collisionDetection() {
        /**
         * if hit enemy unit, kill unit
         */
    }

    @Override
    public void serialize(DataOutputStream wr) {
        //TODO: Finish serialization
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


}
