//-----------------------------------------------------------
//File:   Troop.java
//Desc:   This program creates troop type objects and handles movement
//-----------------------------------------------------------

package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Troop extends MobileEntity {
    private int health;
    private Nationality nationality;
    private boolean selected;
    private TroopDelete troopDelete;
    private DestinationType destinationType;
    private CityType troopType;
    private Game game;
    private boolean dead = false;

    public Troop(Coordinate location, int turnCount, double speed, double heading, Coordinate destination, int health,
            Nationality nationality, boolean selected, DestinationType destinationType, CityType troopType, Game game) {
        super(location, turnCount, speed, heading, destination);
        this.health = health;
        this.nationality = nationality;
        this.selected = selected;
        this.destinationType = destinationType;
        this.troopType = troopType;
        this.game = game;
    }

    /**
     * packages the object and writes it in file according to serialization pattern
     */
    @Override
    public void serialize(DataOutputStream wr) throws IOException {
        wr.writeUTF("Troop");
        wr.writeDouble(this.getLocation().getX());
        wr.writeDouble(this.getLocation().getY());
        wr.writeInt(this.getTurnCount());
        wr.writeDouble(this.getSpeed());
        wr.writeDouble(this.getHeading());
        wr.writeInt(health);
        wr.writeChar((nationality == Nationality.Player) ? 'P' : nationality == Nationality.Enemy ? 'E' : 'N');
        wr.writeBoolean(selected);
        wr.writeChar((destinationType == DestinationType.City ? 'i' : 'o'));
        wr.writeChar((troopType == CityType.Fast) ? 'F' : troopType == CityType.Strong ? 'S' : 's');
        wr.writeDouble(this.getDestination().getX());
        wr.writeDouble(this.getDestination().getY());
    }

    /**
     * Method loads the game
     * 
     * @param rd
     * @param game
     * @return
     * @throws IOException
     */
    public static Entity load(DataInputStream rd, Game game) throws IOException {

        Coordinate location = new Coordinate(rd.readDouble(), rd.readDouble());
        int turnCount = rd.readInt();
        double speed = rd.readDouble();
        double heading = rd.readDouble();
        int health = rd.readInt();
        char nation = rd.readChar();
        Boolean selected = rd.readBoolean();
        Nationality nationality = nation == 'P' ? Nationality.Player
                : nation == 'E' ? Nationality.Enemy : Nationality.Neutral;
        char dChar = rd.readChar();
        DestinationType destinationType = dChar == 'i' ? DestinationType.City
                : DestinationType.Coordinate;
        char tChar = rd.readChar();
        CityType troopType = tChar == 's' ? CityType.Standard
                : tChar == 'F' ? CityType.Fast : CityType.Strong;
        Coordinate destination = new Coordinate(rd.readDouble(), rd.readDouble());

        return new Troop(location, turnCount, speed, heading, destination, health, nationality, selected,
                destinationType, troopType, game);
    }

    /**
     * checks for if reached destination or hit another troop and then updates the
     * position and image
     */
    @Override
    public void update() {
        /**
         * check collision
         * check if reached destination
         */
        collisionDetection();
        if (destinationType == DestinationType.City) {
            double distToDest = Math.sqrt(Math
                    .pow((getDestination().getX()
                            - (getLocation().getX() + getSpeed() * Math.cos(getHeading() * Math.PI / 180))), 2.0)
                    + Math.pow(
                            (getDestination().getY()
                                    - (getLocation().getY() + getSpeed() * Math.sin(getHeading() * Math.PI / 180))),
                            2.0));

            if (Math.abs(distToDest) < Constants.cityRadius - 12 + Constants.troopRadius) {
                troopDelete.onTroopDelete(this);
                game.deleteTroop(this);
            }

        } else {
            double newDistToDest = Math.sqrt(Math
                    .pow((getDestination().getX()
                            - (getLocation().getX() + getSpeed() * Math.cos(getHeading() * Math.PI / 180))), 2.0)
                    + Math.pow(
                            (getDestination().getY()
                                    - (getLocation().getY() + getSpeed() * Math.sin(getHeading() * Math.PI / 180))),
                            2.0));
            double distToDest = Math.sqrt(Math.pow((getDestination().getX() - (getLocation().getX())), 2.0)
                    + Math.pow((getDestination().getY() - (getLocation().getY())), 2.0));
            if (newDistToDest > distToDest) {
                setSpeed(0);
            }
        }
        super.update();
    }

    /**
     * checks to see if hit another troop
     */
    public void collisionDetection() {
        if (game != null && isDead() == false) {
            ArrayList<Troop> troops = new ArrayList<>();
            game.getEntityList().stream().forEach(t -> {
                if (t instanceof Troop
                        && ((Troop) t).getNationality() == ((getNationality() == Nationality.Player) ? Nationality.Enemy
                                : Nationality.Player)) {
                    troops.add((Troop) t);
                }
            });
            for (Troop troop : troops) {
                double distToTroop = Math.sqrt(Math.pow(troop.getLocation().getY() - getLocation().getY(), 2)
                        + Math.pow(troop.getLocation().getX() - getLocation().getX(), 2));
                if (distToTroop < Constants.troopRadius * 2 && troop.isDead() == false) {
                    game.getDeleteEntityList().addAll(List.of(troop, this));
                    troop.setDead(true);
                    setDead(true);
                }
            }
        }
    }

    public double figureHeading(Coordinate destination) {
        if (destination.getX() - getLocation().getX() != 0) {
            if (destination.getX() - getLocation().getX() < 0) {
                return 180 + (Math.toDegrees(Math.atan(
                        (getLocation().getY() - destination.getY()) / (getLocation().getX() - destination.getX()))));
            } else {
                return (Math.toDegrees(Math.atan(
                        (getLocation().getY() - destination.getY()) / (getLocation().getX() - destination.getX()))));
            }
        } else {
            return (destination.getY() - getLocation().getY() > 0) ? 90 : 270;
        }
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

    public DestinationType getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(DestinationType destinationType) {
        this.destinationType = destinationType;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public TroopDelete getTroopDelete() {
        return troopDelete;
    }

    public void setTroopDelete(TroopDelete troopDelete) {
        this.troopDelete = troopDelete;
    }

    public CityType getTroopType() {
        return troopType;
    }

    public void setTroopType(CityType troopType) {
        this.troopType = troopType;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String __str__() {
        return "" + getSpeed() + " " + getHeading() + " " + getDestination();
    }
}
