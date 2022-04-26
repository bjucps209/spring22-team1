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

        /**
         * Depending on level of difficulty, creates action and executes said action
         */
        ArrayList<Entity> entities = game.getEntityList();
        ArrayList<City> computerCities = new ArrayList<City>();
        ArrayList<City> otherCities = new ArrayList<City>();
        ArrayList<Troop> computerTroops = new ArrayList<Troop>();
        ArrayList<Troop> otherTroops = new ArrayList<Troop>();

        turnCount++;

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
        } catch (Exception e) {
            cityToAttack = null;
            return;
        }

        if (computerCities.size() < 1) {
            return;
        }

        switch (difficulty) {
            case Easy:
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
                try {

                    if (computerCities.get(0).getPopulation() >= Constants.cityPopulationLimit) {
                        for (City city : computerCities) {
                            int tries = 3;
                            for (City enemy : otherCities) {
                                if (tries < 1)
                                    break;
                                ArrayList<Troop> troops = game.sendTroopsFromCity(city, enemy.getLocation(), 1);
                                game.getEntityList().addAll(troops);
                                obs.renderTroops(troops);

                            }
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