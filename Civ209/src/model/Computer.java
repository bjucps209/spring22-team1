//-----------------------------------------------------------
//File:   Computer.java
//Desc:   This program creates standered computer object
//-----------------------------------------------------------
package model;

import java.util.ArrayList;

import java.util.Random;

public class Computer {

    // NOT FUNCTIONAL

    // Waiting for reinforcement of Model / View

    private Difficulty difficulty;
    private int turnCount = 0;
    private ComputerObserver obs;

    /**
     * creates and executes an action depending on level of difficulty
     * 
     * @param game the game state
     */
    public void executeAction(Game game) {

        Random r = new Random();
        /**
         * Depending on level of difficulty, creates action and executes said action
         */
        ArrayList<Entity> entities = game.getEntityList();
        ArrayList<City> computerCities = new ArrayList<City>();
        ArrayList<City> otherCities = new ArrayList<City>();
        ArrayList<Troop> computerTroops = new ArrayList<Troop>();

        turnCount++;
        for (Entity entity : entities) {
            if (entity instanceof City) {
                City city = (City) entity;
                if (city.getNationality() == Nationality.Enemy) {
                    computerCities.add(city);
                } else {
                    otherCities.add(city);
                }
            } else {
                Troop troop = (Troop) entity;
                if (troop.getNationality() == Nationality.Enemy) {
                    computerTroops.add(troop);
                }
            }
        }

        if (turnCount % 60 == 0) {
            City cityToAttack = calculateAttackCity(otherCities);
            for (City city : computerCities) {
                ArrayList<Troop> troops = game.sendTroopsFromCity(city, cityToAttack.getLocation(), 100);
                game.getEntityList().addAll(troops);
                obs.renderTroops(troops);
            }
        } else if (turnCount % 15 == 0) {
            for (City city : computerCities) {
                ArrayList<Troop> troops = city.sendTroops(1,
                        new Coordinate(randomNumberGenerator(50, 100) + city.getLocation().getX(),
                        randomNumberGenerator(50, 100)),
                        city.getType(), DestinationType.Coordinate);
                obs.renderTroops(troops);
                game.getEntityList().addAll(troops);

            }
        }

    
 
        // THIS IS WHAT HAPPENS WHEN WE DON'T ENFORCE MODEL / VIEW MOFFITT
        //
        // else if (turnCount % 39 == 0) {
        // City cityToAttack = calculateAttackCity(otherCities);
        // for (Troop troop : computerTroops) {
        // troop.setDestination(cityToAttack.getLocation());
        // troop.setSpeed(1);
        // troop.setHeading(troop.figureHeading(troop.getDestination()));
        // troop.setTroopDelete();

        // }
        // }

    }

    private double randomNumberGenerator(int min, int max) {
        double randomNumber = Math.random() * (max - min) + min;
        return randomNumber;
    }

    private City calculateAttackCity(ArrayList<City> cities) {
        City returnCity = null;
        for (City city : cities) {
            if (returnCity == null && city.getNationality() != Nationality.Enemy) {
                returnCity = city;
            } else if (city.getPopulation() > returnCity.getPopulation()
                    && city.getNationality() != Nationality.Enemy) {
                returnCity = city;
            }

        }
        return returnCity;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public void setObs(ComputerObserver obs) {
        this.obs = obs;
    }
}
