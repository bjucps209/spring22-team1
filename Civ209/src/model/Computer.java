//-----------------------------------------------------------
//File:   Computer.java
//Desc:   This program creates standered computer object
//-----------------------------------------------------------

package model;

import java.util.ArrayList;

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
        ArrayList<Entity> entities = game.getEntityList();
        ArrayList<City> computerCities = new ArrayList<City>();
        ArrayList<City> otherCities = new ArrayList<City>();
        ArrayList<Troop> computerTroops = new ArrayList<Troop>();
        ArrayList<Troop> otherTroops = new ArrayList<Troop>();

        turnCount++;
        // Initialize array lists for calculations
        for (Entity entity : entities) {
            if (entity instanceof City) {
                City city = (City) entity;
                if (city.getNationality() == Nationality.Enemy) {
                    computerCities.add(city);
                } else {
                    otherCities.add(city);
                }
            } else if (entity instanceof Troop) {
                Troop troop = (Troop) entity;
                if (troop.getNationality() == Nationality.Enemy) {
                    computerTroops.add(troop);
                } else {
                    otherTroops.add(troop);
                }
            }
        }

        City cityToAttack;
        try {
            cityToAttack = calculateAttackCity(otherCities);
            if (cityToAttack == null)
                return;
        } catch (Exception e) {
            cityToAttack = null;
            return;
            // If there is no city to attack, end process.
        }

        if (computerCities.size() < 1) {
            return;
        }

        switch (difficulty) {
            case Easy:
                /**
                 * In easy mode the computer sends a half of it's troops from one city every 100
                 * turns, and every 301st turn attacks one city with 40% of all cities
                 */
                try {
                    if (turnCount % 100 == 0) {
                        ArrayList<Troop> troops = game.sendTroopsFromCity(computerCities.get(0),
                                cityToAttack.getLocation(),
                                50);
                        game.getEntityList().addAll(troops);
                        obs.renderTroops(troops);
                    } else if (turnCount % 301 == 0) {
                        for (City city : computerCities) {
                            ArrayList<Troop> troops = game.sendTroopsFromCity(city, cityToAttack.getLocation(), 40);
                            game.getEntityList().addAll(troops);
                            obs.renderTroops(troops);
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                }

                break;

            case Medium:
                /**
                 * In medium the computer sends out troops to the ground, randomly directs those
                 * troops to attack cities, and then makes better coordinated attacks every 100
                 * turns.
                 */
                try {
                    if (turnCount % 100 == 0) {
                        for (City city : computerCities) {
                            ArrayList<Troop> troops = game.sendTroopsFromCity(city, cityToAttack.getLocation(), 100);
                            game.getEntityList().addAll(troops);
                            obs.renderTroops(troops);
                        }
                    } else if (turnCount % 60 == 0) {
                        game.sendTroopsFromGround(computerTroops, calculateAttackCity(otherCities).getLocation(),
                                DestinationType.City);
                    } else if (turnCount % 15 == 0) {
                        for (City city : computerCities) {
                            ArrayList<Troop> troops = city.sendTroops(1,
                                    city.getLocation().figureNewCoordinate(randomNumberGenerator(0, 360),
                                            randomNumberGenerator(-20, 20)),
                                    city.getType(), DestinationType.Coordinate);
                            obs.renderTroops(troops);
                            game.getEntityList().addAll(troops);
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                }
                break;

            case Hard:
                /**
                 * In hard the computer has the most efficient attack strategy: once the enemy
                 * cities reach full troops, send all troops from that city to attack one player
                 * or neutral city, whichever is the best to attack. It also sends out decoy
                 * troops at this point.
                 */
                try {

                    if (computerCities.get(0).getPopulation() >= Constants.cityPopulationLimit) {

                        for (City city : computerCities) {
                            ArrayList<Troop> troops = game.sendTroopsFromCity(city, cityToAttack.getLocation(), 100);
                            game.getEntityList().addAll(troops);
                            obs.renderTroops(troops);

                        }
                    }

                } catch (IndexOutOfBoundsException e) {
                }

                break;

        }
    }

    /**
     * Generates a random number between min and max.
     * 
     * @param min
     * @param max
     * @return - random number
     */
    private double randomNumberGenerator(int min, int max) {
        double randomNumber = Math.random() * (max - min) + min;
        return randomNumber;
    }

    /**
     * Calculates the city that should be attacked. First finds a non-computer
     * controlled city, then searches through non-computer controlled cities for the
     * city with the lowest population, which would be optimum to attack.
     * 
     * @param cities - List of all cities in game.
     * @return - City to attack
     */
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

    /* \***********************************************************************\ */
    // Getters and setters

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
