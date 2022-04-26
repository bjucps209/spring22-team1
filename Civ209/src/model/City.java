//-----------------------------------------------------------
//File:   City.java
//Desc:   This program creates a city and updates it
//        when the game loop calls
//-----------------------------------------------------------

package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

public class City extends Entity {
    private IntegerProperty populationProperty = new SimpleIntegerProperty();
    private Nationality nationality;
    private boolean selected = false;
    private CityType type;
    private int id;
    private CityObserver obs;
    private int turnCount = 0;
    private Game game;
    private Coordinate location;

    private static int nextId;

    public City(Coordinate location, int turnCount, IntegerProperty population,
            Nationality nationality,
            boolean selected, CityType type, Game game) {
        super(location, turnCount);
        this.location = location; 
        this.populationProperty = population;
        this.nationality = nationality;
        this.selected = selected;
        this.type = type;
        this.id = ++nextId;
    }

    /**
     * packages the object and writes it in file according to serialization pattern
     * 
     * @throws IOException
     */
    @Override
    public void serialize(DataOutputStream wr) throws IOException {
        // Goes through and writes all of the information necessary for a constructor.
        wr.writeUTF("City");
        wr.writeDouble(this.getLocation().getX());
        wr.writeDouble(this.getLocation().getY());
        wr.writeInt(this.getTurnCount());
        wr.writeInt(this.getPopulation());
        wr.writeChar((nationality == Nationality.Player) ? 'P' : nationality == Nationality.Enemy ? 'E' : 'N');
        wr.writeBoolean(selected);
        wr.writeChar((type == CityType.Fast) ? 'F' : type == CityType.Strong ? 'S' : 's');
    }

    public static Entity load(DataInputStream rd, Game game) throws IOException {
        Coordinate location = new Coordinate(rd.readDouble(), rd.readDouble());
        int turnCount = rd.readInt();
        int population = rd.readInt();
        IntegerProperty popProperty = new SimpleIntegerProperty(population);
        char nation = rd.readChar();
        Nationality nationality = nation == 'P' ? Nationality.Player
                : nation == 'E' ? Nationality.Enemy : Nationality.Neutral;
        boolean selected = rd.readBoolean();
        char cityT = rd.readChar();
        CityType cityType = cityT == 'S' ? CityType.Strong
                : cityT == 'F' ? CityType.Fast : CityType.Standard;
        return new City(location, turnCount, popProperty, nationality, selected,
                cityType, game);
    }

    /**
     * updates the population and image. fires projectile on appropriate ticks.
     */
    @Override
    public void update() {
        turnCount++;
        if (getPopulation() < Constants.cityPopulationLimit && turnCount % Constants.cityPopulationUpdateSpeed == 0) {
            setPopulation(getPopulation() + 1);

        }
        super.update();
    }

    /**
     * generates percentage of population troops going to destination
     * 
     * @param percentage  percentage of city population to send out
     * @param destination destination of the generated troops
     * @param type        type of troop to be sending out
     */
    public ArrayList<Troop> sendTroops(double percentage, Coordinate destination, CityType type,
            DestinationType destinationType) {
        if (getPopulation() > 0) {
            percentage = percentage / 100;
            int numtroops = (int) (getPopulation() * percentage);
            if (numtroops < 1)
                numtroops = 1;
            ArrayList<Troop> troops = new ArrayList<>();
            for (int i = 0; i < numtroops; i++) {
                double heading = figureHeading(destination);
                Troop troop = new Troop(new Coordinate(getLocation()), getTurnCount(),
                        0, heading,
                        destination,
                        (type == CityType.Strong) ? Constants.strongTroopHealth : Constants.standardTroopHealth,
                        nationality, false, destinationType, type, game);
                troops.add(troop);
            }

            Thread t = new Thread(() -> {
                Timeline timer = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                    Troop troop = troops.get(0);
                    troops.remove(troop);
                    troop.setSpeed((type == CityType.Fast) ? Constants.fastTroopSpeed : Constants.standardTroopSpeed);
                }));
                timer.setCycleCount(troops.size());
                timer.play();
            });
            t.start();

            setPopulation(getPopulation() - numtroops);
            return troops;
        }
        return new ArrayList<Troop>();
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

    /**
     * fires a projectile from city at closest enemy if enemy in range and city
     * population not 0
     * @param game parent game 
     * @return projectile to render
     */
    public Projectile fireProjectile(Game game) {
        this.setGame(game);
        Projectile projectile = null;
        if (getPopulation() != 0) {
            ArrayList<Troop> troops = new ArrayList<>();
            game.getEntityList().stream().forEach(t -> {
                if (t instanceof Troop) {
                    troops.add((Troop) t);
                }
            });
            for (Troop troop : troops) {
                if (troop.getNationality() != nationality && getLocation().isNearThis(troop.getLocation())) {
                    if (turnCount % 15 == 0) {
                        Troop targettroop = troop;
                        projectile = new Projectile(this.location, turnCount, 2, 0,
                                targettroop.getLocation(), 2);
                        projectile.setGame(game);
                        projectile.fireProjectile(this);
                    } else {
                        return null;
                    }
                }
            }
            return projectile;
        }
        return null;
    }

    public void recieveTroops(int amount, Nationality attackingType) {

        if (nationality == attackingType) {
            populationProperty.set(populationProperty.get() + amount);
        } else {
            if (populationProperty.get() - amount > 0) {
                populationProperty.set(populationProperty.get() - amount);
            } else {
                nationality = attackingType;
                obs.update();
                populationProperty.set(0);
            }
        }
    }

    /*************************************************************************/
    // Getters and setters

    public int getPopulation() {
        return populationProperty.get();
    }

    public void setPopulation(int population) {
        this.populationProperty.set(population);
    }

    public IntegerProperty populationProperty() {
        return populationProperty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public CityType getType() {
        return type;
    }

    public void setType(CityType type) {
        this.type = type;
    }

    public void setObs(CityObserver obs) {
        this.obs = obs;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}