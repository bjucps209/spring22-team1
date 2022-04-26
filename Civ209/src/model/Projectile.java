//-----------------------------------------------------------
//File:   Projetile.java
//Desc:   This program creates projectile objects meant to kill troops
//-----------------------------------------------------------
package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Projectile extends MobileEntity {
    private int damage;
    private Coordinate location;
    private Coordinate destination;
    private int turnCount;
    // private onTroopDeleteInterface troopDelete;
    private Game game;

    public Projectile(Coordinate location, int turnCount, double speed, double heading,
            Coordinate destination, int damage) {
        super(location, turnCount, speed, heading, destination);
        this.damage = damage;
        this.location = location;
        this.destination = destination;
        this.turnCount = turnCount;
    }

    /**
     * packages the object and writes it in file according to serialization pattern
     */
    @Override
    public void serialize(DataOutputStream wr) throws IOException {
        wr.writeUTF("Projectile");
        wr.writeDouble(this.getLocation().getX());
        wr.writeDouble(this.getLocation().getY());
        wr.writeInt(turnCount);
        wr.writeDouble(this.getSpeed());
        wr.writeDouble(this.getHeading());
        wr.writeDouble(this.getDestination().getX());
        wr.writeDouble(this.getDestination().getY());
        wr.writeInt(damage);
    }

    public static Entity load(DataInputStream rd) throws IOException {
        Coordinate location = new Coordinate(rd.readDouble(), rd.readDouble());
        int turnCount = rd.readInt();
        double speed = rd.readDouble();
        double heading = rd.readDouble();
        Coordinate destination = new Coordinate(rd.readDouble(), rd.readDouble());
        int damage = rd.readInt();
        return new Projectile(location, turnCount, speed, heading, destination, damage);
    }

    /**
     * checks for if hit target and then updates the position and image
     */
    @Override
    public void update() {
        // if (turncount%15 == 0) {
        super.update();
        // }
        // ++fireProjectile;
        // sends the projectile to the destination
        /**
         * check collision detection()
         */
        // if hit, delete enemy troop and projectile
        // update damage
    }

    public Projectile fireProjectile(City city) {
        setHeading(city.figureHeading(destination));
        ArrayList<Troop> troops = new ArrayList<>();
        if (damage > 0) {
            game.getEntityList().stream().forEach(t -> {
                if (t instanceof Troop) {
                    troops.add((Troop) t);
                }
            });
            for (Troop troop : troops) {
                if (troop.getLocation() == destination) {
                    game.getDeleteEntityList().addAll(List.of(troop, this));
                    --damage;
                }
            }
        }
        update();
        return this;
    }

    public boolean fireable() {
        if (turnCount % 10 == 0) {
            return true;
        }
        return false;
    }

    /**
     * checks if hit enemy
     */
    // public void collisionDetection() {
    // ArrayList<Troop> troops = new ArrayList<>();
    // game.getEntityList().stream().forEach(t -> {
    // if (t instanceof Troop) {
    // troops.add((Troop) t);
    // }
    // });
    // for (Troop troop: troops) {
    // double distToTroop = Math.sqrt(Math.pow(troop.getLocation().getY() -
    // getLocation().getY(), 2) + Math.pow(troop.getLocation().getX() -
    // getLocation().getX(), 2));
    // if (distToTroop < Constants.troopRadius * 2) {
    // game.getDeleteEntityList().addAll(List.of(troop, this));
    // }
    // }
    // if (this.getLocation().getX() == this.getDestination().getX() &&
    // this.getLocation().getY() == this.getDestination().getY()){
    // return true;
    // }
    // return false;
    // }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }

    public Coordinate getDestination() {
        return destination;
    }

    public void setDestination(Coordinate destination) {
        this.destination = destination;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
