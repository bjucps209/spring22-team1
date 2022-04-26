//-----------------------------------------------------------
//File:   City.java
//Desc:   This program creates a city and updates it
//        when the game loop calls
//-----------------------------------------------------------

package model;

import java.util.Random;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

public class City extends Entity {
    private IntegerProperty populationProperty = new SimpleIntegerProperty();
    private double incrementRate;
    private Nationality nationality;

    private boolean selected = false;
    private double fireRate;
    private CityType type;
    private int id;
    private int x;
    private int y;
    private CityObserver obs;
    private int turnCount = 0;
    private Game game;
    private Coordinate location;

    private static int nextId; 

    public City(Coordinate location, int turnCount, IntegerProperty population, double incrementRate,
            Nationality nationality,
            boolean selected, double fireRate, CityType type) {
        super(location, turnCount);
        this.populationProperty = population;
        this.incrementRate = incrementRate;
        this.nationality = nationality;
        this.selected = selected;
        this.fireRate = fireRate;
        this.type = type;
        this.id = ++nextId;
        var rand = new Random();
        this.x = rand.nextInt(750);
        this.y = rand.nextInt(450);
    }

    public static Entity load(DataInputStream rd) throws IOException {
        Coordinate location = new Coordinate(rd.readDouble(), rd.readDouble());
        int turnCount = rd.readInt();
        int population = rd.readInt();
        IntegerProperty popProperty = new SimpleIntegerProperty(population);
        double incrementRate = rd.readDouble();
        char nation = rd.readChar();
        Nationality nationality = nation == 'P' ? Nationality.Player
                : nation == 'E' ? Nationality.Enemy : Nationality.Neutral;
        boolean selected = rd.readBoolean();
        double fireRate = rd.readDouble();
        char cityT = rd.readChar();
        CityType cityType = cityT == 'S' ? CityType.Standard
                : cityT == 'F' ? CityType.Fast : CityType.Strong;
        return new City(location, turnCount, popProperty, incrementRate, nationality, selected,
                fireRate, cityType);
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
                        nationality, false, destinationType, type);
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
                if (troop.getNationality() != nationality && location.isNearThis(troop.getLocation())) {
                    if (turnCount%10 == 0) {
                        Troop targettroop = troop; 
                        projectile = new Projectile(this.location, turnCount, 2, 0,
                            targettroop.getLocation(), 2);
                        projectile.setGame(game);
                        projectile.fireProjectile(this); 
                    }
                    else {
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

    /**
     * packages the object and writes it in file according to serialization pattern
     * 
     * @throws IOException
     */
    @Override
    public void serialize(DataOutputStream wr) throws IOException {
        // Goes through and writes all of the information necessary for a constructor.
        wr.writeUTF("City");
        wr.writeDouble(this.getX());
        wr.writeDouble(this.getY());
        wr.writeInt(this.getTurnCount());
        wr.writeInt(this.getPopulation());
        wr.writeDouble(incrementRate);
        wr.writeChar((nationality == Nationality.Player) ? 'P' : nationality == Nationality.Enemy ? 'E' : 'N');
        wr.writeBoolean(selected);
        wr.writeDouble(fireRate);
        wr.writeChar((type == CityType.Fast) ? 'F' : type == CityType.Strong ? 'S' : 's');
    }

    public int getPopulation() {
        return populationProperty.get();
    }

    public void setPopulation(int population) {
        this.populationProperty.set(population);
    }

    public IntegerProperty populationProperty() {
        return populationProperty;
    }

    public double getIncrementRate() {
        return incrementRate;
    }

    public void setIncrementRate(double incrementRate) {
        this.incrementRate = incrementRate;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Object[] getInformation() {
        Object[] items = { id, x, y, nationality };
        return items;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public double getFireRate() {
        return fireRate;
    }

    public void setFireRate(double fireRate) {
        this.fireRate = fireRate;
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