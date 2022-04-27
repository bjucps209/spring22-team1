//-----------------------------------------------------------
//File:   Projectile.java
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
    private Coordinate destination;
    private int turnCount;
    private Coordinate location;
    private Game game;

    /**
     * Base constructor for Projectiles.
     * 
     * @param location
     * @param turnCount
     * @param speed
     * @param heading
     * @param destination
     * @param damage
     */
    public Projectile(Coordinate location, int turnCount, double speed, double heading,
            Coordinate destination, int damage) {
        super(location, turnCount, speed, heading, destination);
        this.location = location;
        this.damage = damage;
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

    /**
     * Method to load in the projectile that was saved.
     * 
     * @param rd - DataInputStream to read from.
     * @return - Returns built Projectile
     * @throws IOException
     */
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
     * updates the projectiles
     */
    @Override
    public void update() {
        super.update();
    }

    /**
     * Sends a projectile to a troop if damage is more than 0
     * 
     * @param city that we want to send the projectile from
     * @returns the sent Projectile so that it can be rendered
     */
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

    /*********************************************************/
    // Getters and Seconds

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
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

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }

}
