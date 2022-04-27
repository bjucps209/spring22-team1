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

    // Health of the troop, counts against city population.
    private int health;

    // Nationality of troop, Player or Enemy
    private Nationality nationality;

    // true if selected, for serialization purposes.
    private boolean selected;

    // Event handler to remove troop from entity list when appropriate
    private TroopDelete troopDelete;

    // Either City or Coordinate, for movement calculation purposes.
    private DestinationType destinationType;

    // Type of troop, Standard, Strong, or Fast.
    private CityType troopType;

    // Holds reference to the game it is played on for deletion purposes and
    // optimization purposes.
    private Game game;

    // Keeps track of if troop has been killed or not.
    private boolean dead = false;

    /**
     * Basic constructor
     * 
     * @param location
     * @param turnCount
     * @param speed
     * @param heading
     * @param destination
     * @param health
     * @param nationality
     * @param selected
     * @param destinationType
     * @param troopType
     * @param game
     */
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
     * Method reads in the data for the troop and returns the troop object built.
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

    /**
     * Figures what degree the heading should be to get to the desired coordinate
     * from current location.
     * 
     * @param destination
     * @return - heading in degrees (0-359)
     */
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

    /*************************************************************************/
    // Getters and setters
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

    // String method for debugging purposes.
    public String __str__() {
        return "" + getSpeed() + " " + getHeading() + " " + getDestination();
    }
}
