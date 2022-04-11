package model;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private List<City> cities = new ArrayList<City>(); 
    private Season season; 

    /**
     * Creates a subclass of City and adds it to the list of cities
      * @param nationality - the nationality of the city to create
     * @return the created city
     */
    public City create(Nationality nationality) {
        City city = new City(nationality);  
        cities.add(city);
        return city;
    }

    /**
     * Finds a City in the list of cities
     * @param id - id of the city to find
     * @return City with the specified id, or null if no such city is in the list
     */
    public City find(int id) {
        for (City city : cities) {
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
        for (City city : cities) {
            if (city.getId() == id) {
                cities.remove(city);
                return city;
            }
        }
        return null;
    }

    public List<City> getCities() {
        return cities;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
    
}
