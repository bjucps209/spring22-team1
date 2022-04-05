package model;

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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
