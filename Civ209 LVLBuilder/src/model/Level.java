package model;

import java.util.ArrayList;
import java.util.List;

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
        Coordinate location = new Coordinate();
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
    public City delete(int id) {
        for (Entity entity : cities) {
            City city = (City) entity;
            if (city.getId() == id) {
                cities.remove(city.getId()); 
                return city;
            }
        }
        return null;
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
