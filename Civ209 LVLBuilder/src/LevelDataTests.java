import static org.junit.Assert.assertEquals;

import org.junit.*;

import model.City;
import model.Level;
import model.Nationality;
import model.SeasonType;

import java.util.*;

import javax.swing.text.html.parser.Entity;

public class LevelDataTests {

    @Test
    public void testSaveandLoad() {
        Level l = new Level();
        l.setSeason(SeasonType.Winter);
        City city = l.create(Nationality.Enemy);
        city.setId(0);
        city.setX(32);
        city.setY(20);
        LevelData d = new LevelData(l);
        //d.save();
        Level loadedlevel = new Level();
        LevelData loadeddata = new LevelData();
        //List<Entity> cities = loadeddata.load(); 
        // City loadedcity = (City) cities.get(0); 
        // assertEquals(0, loadedcity.getId());
        // assertEquals(32, loadedcity.getX());
        // assertEquals(20, loadedcity.getY());
        // assertEquals(Nationality.Enemy, loadedcity.getNationality());
        // assertEquals(SeasonType.Winter, loadedlevel.getSeason()); 
        
    }
}