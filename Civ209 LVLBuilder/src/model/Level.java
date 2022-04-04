package model;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private List<City> cities = new ArrayList<City>(); 

    /**
     * Creates a subclass of City and adds it to the list of cities
      * @param nationality - the nationality of the city to create
     * @return the created city
     */
    public City create(Nationality nationality) {
        throw new RuntimeException("Method not implemented");
    }

    /**
     * Finds a City in the list of cities
     * @param id - id of the city to find
     * @return City with the specified id, or null if no such city is in the list
     */
    public City find(int id) {
        throw new RuntimeException("Method not implemented");
    }

    /**
     * deletes the city with the specified id from the list of cities
     * @param id - id of city to destroy
     * @return the destroyed City, or null if no city had the specified id
     */
    public City delete(int id) {
        throw new RuntimeException("Method not implemented");
    }

    public List<City> getCities() {
        return cities;
    }
}
