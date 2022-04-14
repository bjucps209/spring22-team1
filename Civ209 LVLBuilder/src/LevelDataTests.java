import static org.junit.Assert.assertEquals;

import org.junit.*;

import model.City;
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
        City city = l.create(Nationality.Enemy);
        city.setId(1);
        city.setX(32);
        city.setY(20);
        LevelData d = new LevelData(l);
        try { d.save();
        }catch (IOException e) {}
        // Level loadedlevel = new Level();
        LevelData w = new LevelData();  
        try { w.load();
        }catch (IOException e) {}
        Level loadeddata = w.getLevel(); 
        City loadedcity = (City) loadeddata.getCities().get(0); 
        assertEquals(1, loadedcity.getId());
        assertEquals(32, loadedcity.getX(), 0);
        assertEquals(20, loadedcity.getY(), 0);
        assertEquals(Nationality.Enemy, loadedcity.getNationality());
        assertEquals(SeasonType.Winter, loadeddata.getSeason()); 
        
    }
}