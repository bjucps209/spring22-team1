package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Level {
    private List<City> cities = new ArrayList<City>(); 
    private SeasonType season; 

    /**
     * Creates a subclass of City and adds it to the list of cities
      * @param nationality - the nationality of the city to create
     * @return the created city
     */
    public City create(Nationality nationality) {
        var rand = new Random();
        int x = rand.nextInt(750);
        int y = rand.nextInt(450);
        Coordinate location = new Coordinate(x, y);
        IntegerProperty intprop = new SimpleIntegerProperty(10); 
        intprop.setValue(0);
        City city = new City(location, 0, intprop , 0.0, nationality,
        false, 0.0, CityType.Standard);  
        cities.add(city);
        return city;
    }

    /**
     * Finds a City in the list of cities
     * @param id - id of the city to find
     * @return City with the specified id, or null if no such city is in the list
     */
    public City find(int id) {
        for (Entity entity : cities) {
            City city = (City) entity; 
            if (city.getId() == id) {
                return city;
            }
        }
        return null;
    }

    /**
     * deletes the city with the specified id from the list of cities
     * @param id - id of city to destroy
     * @return the destroyed City, or null if no city had the specified id
     */
    public City delete(City city) {
            cities.remove(city); 
                return city; 
    }

    public List<City> getCities() {
        return cities;
    }

    public SeasonType getSeason() {
        return season;
    }

    public void setSeason(SeasonType season) {
        this.season = season;
    }

    public void add(City entity) {
        cities.add(entity);
    }

    // public void move(City entity) {
    //     entity.updatePosition(); 
    // }
    
}
