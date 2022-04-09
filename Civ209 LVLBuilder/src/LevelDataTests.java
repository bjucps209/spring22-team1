import static org.junit.Assert.assertEquals;

import org.junit.*;

import model.City;
import model.Level;
import model.Nationality;
import model.Season;

import java.util.*;

public class LevelDataTests {

    @Test
    public void testSaveandLoad() {
        LevelData d = new LevelData();
        Level l = new Level();
        l.setSeason(Season.WINTER);
        City city = l.create(Nationality.ENEMY);
        city.setId(0);
        city.setX(32);
        city.setY(20);
        d.save();
        Level loadedlevel = new Level();
        LevelData loadeddata = new LevelData();
        List<City> cities = loadeddata.load(); 
        City loadedcity = cities.get(0); 
        assertEquals(0, loadedcity.getId());
        assertEquals(32, loadedcity.getX());
        assertEquals(20, loadedcity.getY());
        assertEquals(Nationality.ENEMY, loadedcity.getNationality());
        assertEquals(Season.WINTER, loadedlevel.getSeason()); 
        
    }
}