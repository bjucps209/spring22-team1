package model;

import java.io.DataOutput;
import java.io.DataOutputStream;

public class Projectile extends MobileEntity {
    private int damage;

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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


}
