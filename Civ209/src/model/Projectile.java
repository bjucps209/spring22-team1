//-----------------------------------------------------------
//File:   Projetile.java
//Desc:   This program creates projectile objects ment to kill troops
//-----------------------------------------------------------
package model;

import java.io.DataOutputStream;

public class Projectile extends MobileEntity {
    private int damage;

    public Projectile(Coordinate location, int turnCount, double speed, double heading,
            Coordinate destination, int damage) {
        super(location, turnCount, speed, heading, destination);
        this.damage = damage;
    }

    /**
     * checks for if hit target and then updates the position and image
     */
    @Override
    public void update() {
        /**
         * check collision detection
         */
        super.update();
    }

    /**
     * checks if hit enemy
     */
    public void collisionDetection() {
        /**
         * if hit enemy unit, kill unit
         */
    }

    /**
     * packages the object and writes it in file according to serialization pattern
     */
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
