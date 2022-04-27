import static org.junit.Assert.assertEquals;

import org.junit.*;

import model.City;
import model.CityType;
import model.Level;
import model.Nationality;
import model.SeasonType;

import java.io.IOException;
import java.util.*;

import javax.swing.text.html.parser.Entity;

public class LevelDataTests {

    @Test
    public void testSaveandLoad() {
        Level l = new Level();
        l.setSeason(SeasonType.Winter);
        City city1 = l.create(Nationality.Enemy);
        city1.setId(1);
        city1.getLocation().setX(32);
        city1.getLocation().setY(20);
        city1.setType(CityType.Strong);
        City city2 = l.create(Nationality.Player);
        city2.setId(2);
        city2.getLocation().setX(15);
        city2.getLocation().setY(90);
        City city3 = l.create(Nationality.Neutral);
        city3.setId(3);
        city3.getLocation().setX(740);
        city3.getLocation().setY(680);
        
        LevelData d = new LevelData(l);
        try { d.save();
        }catch (IOException e) {}
        LevelData w = new LevelData();  
        try { w.load();
        }catch (IOException e) {}
        Level loadeddata = w.getLevel(); 
        City loadedcity1 = (City) loadeddata.getCities().get(0); 
        assertEquals(1, loadedcity1.getId());
        assertEquals(32, loadedcity1.getLocation().getX(), 0);
        assertEquals(20, loadedcity1.getLocation().getY(), 0);
        assertEquals(CityType.Strong, loadedcity1.getType()); 
        assertEquals(Nationality.Enemy, loadedcity1.getNationality());
        assertEquals(SeasonType.Winter, loadeddata.getSeason()); 
        City loadedcity2 = (City) loadeddata.getCities().get(1); 
        assertEquals(2, loadedcity2.getId());
        assertEquals(15, loadedcity2.getLocation().getX(), 0);
        assertEquals(90, loadedcity2.getLocation().getY(), 0);
        assertEquals(Nationality.Player, loadedcity2.getNationality());
        City loadedcity3 = (City) loadeddata.getCities().get(2); 
        assertEquals(3, loadedcity3.getId());
        assertEquals(740, loadedcity3.getLocation().getX(), 0);
        assertEquals(680, loadedcity3.getLocation().getY(), 0);
        assertEquals(Nationality.Neutral, loadedcity3.getNationality());
        
    }
}