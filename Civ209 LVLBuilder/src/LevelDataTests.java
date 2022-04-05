import org.junit.Assert;
import org.junit.Test;

import org.junit.Test;
import org.junit.Assert.*;

import model.City;
import model.Level;
import model.Nationality;
import model.Season;

import java.util.*;

public class LevelDataTests {

    LevelData d = new LevelData();
    Level l = new Level();

    public LevelDataTests () {
        l.setSeason(Season.WINTER);
        City city = l.create(Nationality.ENEMY);
        city.setId(0);
        city.setX(32);
        city.setY(20);
    }

    @Test
    public void testSave() {
        //Assert.assertEquals(d.save(l.getCities()));
        
    }

    @Test 
    public void testLoad() {
        //Assert.assertEquals(, d.load());
    }
}
