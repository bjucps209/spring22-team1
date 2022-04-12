//-----------------------------------------------------------
//File:   City.java
//Desc:   This program creates a city and updates it
//        when the game loop calls
//-----------------------------------------------------------
package model;

import java.io.DataOutputStream;
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

    public City(Coordinate location, int turnCount, IntegerProperty population, double incrementRate, Nationality nationality,
            boolean selected, double fireRate, CityType type) {
        super(location, turnCount);
        this.populationProperty = population;
        this.incrementRate = incrementRate;
        this.nationality = nationality;
        this.selected = selected;
        this.fireRate = fireRate;
        this.type = type;
    }

    /**
     * updates the population and image. fires projectile on appropriate ticks.
     */
    @Override
    public void update() {
        if (getPopulation() < 30){
            setPopulation(getPopulation() + 1);
        }
        super.update();
    }

    /**
     * generates percentage of population troops going to destination
     * @param percentage percentage of city population to send out
     * @param destination destination of the generated troops
     * @param type type of troop to be sending out
     */
    public ArrayList<Troop> sendTroops(double percentage, Coordinate destination, CityType type, DestinationType destinationType) {

        
    // Mr. Moffitt - 
    // Butchered code, but the timer could be handy.
    //                 if (selectedCity != null) {
    //             ArrayList<Troop> troops = selectedCity.sendTroops(50.0, city.getLocation(), selectedCity.getType());
    //             Timeline troopTimer = new Timeline(new KeyFrame(Duration.millis(200), ex -> {
    //                 EntityImage circle = new EntityImage(this, mainPane, troops.get((getNextTroopSendIndex())));
    //                 circle.layoutXProperty().bind(troops.get((getNextTroopSendIndex())).getLocation().xProperty());
    //                 circle.layoutYProperty().bind(troops.get((getNextTroopSendIndex())).getLocation().yProperty());
    //                 // circle.setFill(Paint.valueOf("transparent"));
    //                 // circle.setStroke(Paint.valueOf((troops.get((getNextTroopSendIndex())).getNationality()
    //                 // == Nationality.Enemy)
    //                 // ? "red"
    //                 // : (troops.get((getNextTroopSendIndex())).getNationality() ==
    //                 // Nationality.Neutral) ? "grey" : "blue"));
    //                 // circle.setRadius(5);
    //                 circle.setUserData(troops.get((getNextTroopSendIndex())));
    //                 troops.get((getNextTroopSendIndex())).setTroopDelete(this::onTroopDelete);
    //             }));
    //             troopTimer.setCycleCount(troops.size());
    //             troopSendIndex = 0;
    //             troopTimer.play();

    //             game.getEntityList().addAll(troops);




    //                 //TODO Mr. Moffitt I made some code for you... may not be helpful but if you want it take it
    // private int getNextTroopSendIndex() {
    //     int current = troopSendIndex;
    //     troopSendIndex++;
    //     return current;
    // }

                
        percentage = percentage / 100;
        int numtroops = (int)(getPopulation() * percentage);
        ArrayList<Troop> troops = new ArrayList<>();
        for (int i = 0; i < numtroops; i++) {
            double heading = figureHeading(destination);
            Troop troop = new Troop(new Coordinate(getLocation()), getTurnCount(), (type == CityType.Fast) ? Constants.fastTroopSpeed : Constants.standardTroopSpeed, heading, destination, (type == CityType.Strong)? Constants.stronTroopHealth : Constants.standardTroopHealth, nationality, false, destinationType);
            // troop.getLocation().setX(troop.getLocation().getX() + Constants.cityRadius * Math.cos(heading * Math.PI / 180));
            // troop.getLocation().setY(troop.getLocation().getY() + Constants.cityRadius * Math.sin(heading * Math.PI / 180));
            troops.add(troop);
        }
        setPopulation(getPopulation() - numtroops);
        return troops;
    }
    
    public double figureHeading(Coordinate destination) {
        if (destination.getX() - getLocation().getX() != 0) {
            if (destination.getX() - getLocation().getX() < 0) {
                return 180 + (Math.toDegrees(Math.atan((getLocation().getY() - destination.getY()) / (getLocation().getX() - destination.getX()))));
            } else {
                return (Math.toDegrees(Math.atan((getLocation().getY() - destination.getY()) / (getLocation().getX() - destination.getX()))));
            }
        } else {
            return 0.0;
        }
    }

    /**
     * fires a projectile from city at closest enemy if enemy in range and city population not 0
     */
    public void fireProjectile() {
        /**
         * check if any enemies in range. If so, fire projectiles
         */
    }

    /**
     * packages the object and writes it in file according to serialization pattern
     */
    @Override
    public void serialize(DataOutputStream wr) {
    }

    public int getPopulation() {
        return populationProperty.get();
    }

    public void setPopulation(int population) {
        this.populationProperty.set(population);
    }

    public IntegerProperty populationProperty(){
        return populationProperty;
    }

    public double getIncrementRate() {
        return incrementRate;
    }

    public void setIncrementRate(double incrementRate) {
        this.incrementRate = incrementRate;
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
}