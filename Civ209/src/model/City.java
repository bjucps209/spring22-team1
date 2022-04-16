//-----------------------------------------------------------
//File:   City.java
//Desc:   This program creates a city and updates it
//        when the game loop calls
//-----------------------------------------------------------
package model;

import java.util.Random;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

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

    /**
     * updates the population and image. fires projectile on appropriate ticks.
     */
    @Override
    public void update() {
        turnCount++;
        if (getPopulation() < 30 && turnCount % 3 == 0) {
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

        // Mr. Moffitt -
        // Butchered code, but the timer could be handy.
        // if (selectedCity != null) {
        // ArrayList<Troop> troops = selectedCity.sendTroops(50.0, city.getLocation(),
        // selectedCity.getType());
        // Timeline troopTimer = new Timeline(new KeyFrame(Duration.millis(200), ex -> {
        // EntityImage circle = new EntityImage(this, mainPane,
        // troops.get((getNextTroopSendIndex())));
        // circle.layoutXProperty().bind(troops.get((getNextTroopSendIndex())).getLocation().xProperty());
        // circle.layoutYProperty().bind(troops.get((getNextTroopSendIndex())).getLocation().yProperty());
        // // circle.setFill(Paint.valueOf("transparent"));
        // //
        // circle.setStroke(Paint.valueOf((troops.get((getNextTroopSendIndex())).getNationality()
        // // == Nationality.Enemy)
        // // ? "red"
        // // : (troops.get((getNextTroopSendIndex())).getNationality() ==
        // // Nationality.Neutral) ? "grey" : "blue"));
        // // circle.setRadius(5);
        // circle.setUserData(troops.get((getNextTroopSendIndex())));
        // troops.get((getNextTroopSendIndex())).setTroopDelete(this::onTroopDelete);
        // }));
        // troopTimer.setCycleCount(troops.size());
        // troopSendIndex = 0;
        // troopTimer.play();

        // game.getEntityList().addAll(troops);

        // //TODO Mr. Moffitt I made some code for you... may not be helpful but if you
        // want it take it
        // private int getNextTroopSendIndex() {
        // int current = troopSendIndex;
        // troopSendIndex++;
        // return current;
        // }

        percentage = percentage / 100;
        int numtroops = (int) (getPopulation() * percentage);
        ArrayList<Troop> troops = new ArrayList<>();
        for (int i = 0; i < numtroops; i++) {
            double heading = figureHeading(destination);
            Troop troop = new Troop(new Coordinate(getLocation()), getTurnCount(),
                    (type == CityType.Fast) ? Constants.fastTroopSpeed : Constants.standardTroopSpeed, heading,
                    destination,
                    (type == CityType.Strong) ? Constants.strongTroopHealth : Constants.standardTroopHealth,
                    nationality, false, destinationType, type);
            troops.add(troop);
        }

        setPopulation(getPopulation() - numtroops);
        return troops;
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
    public void fireProjectile() {
        /**
         * check if any enemies in range. If so, fire projectiles
         */
    }

    public void recieveTroops(int amount, Nationality attackingType) {

        if (nationality == attackingType) {
            populationProperty.set(populationProperty.get() + amount);
        } else {
            if (populationProperty.get() - amount > -1) {
                populationProperty.set(populationProperty.get() - amount);
                System.out.println("Trying to decrement");
            } else {
                nationality = attackingType;
                obs.update();
                System.out.println("Trying to switch city type");
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
        wr.writeDouble(this.getLocation().getX());
        wr.writeDouble(this.getLocation().getY());
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
}